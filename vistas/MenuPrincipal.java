import clases.Proyecto;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    private final Proyecto proyecto = InterfazTablero.crearProyectoDemo();
    private InterfazTablero tablero;
    private InterfazReportes reportes;

    public MenuPrincipal() {
        setTitle("Gestion de proyectos - Menu principal");
        setSize(400, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titulo = new JLabel("Proyecto: " + proyecto.getNombre(), SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));

        JButton btnLogin = new JButton("Iniciar sesion / Registro");
        JButton btnTablero = new JButton("Tableros y Tareas");
        JButton btnReportes = new JButton("Reportes");
        JButton btnSalir = new JButton("Salir");

        btnLogin.addActionListener(e -> new Interfazlogin().setVisible(true));
        btnTablero.addActionListener(e -> abrirTablero());
        btnReportes.addActionListener(e -> abrirReportes());
        btnSalir.addActionListener(e -> System.exit(0));

        panel.add(titulo);
        panel.add(btnLogin);
        panel.add(btnTablero);
        panel.add(btnReportes);
        panel.add(btnSalir);
        add(panel);
    }

    private void abrirTablero() {
        if (tablero == null || !tablero.isDisplayable()) {
            tablero = new InterfazTablero(proyecto);
        }
        tablero.setVisible(true);
        tablero.toFront();
    }

    private void abrirReportes() {
        if (reportes == null || !reportes.isDisplayable()) {
            reportes = new InterfazReportes(proyecto);
        }
        reportes.setVisible(true);
        reportes.toFront();
        reportes.actualizar();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}
