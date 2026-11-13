
import javax.swing.*;
import java.awt.*;

public class Interfazlogin extends JFrame {

    private JTextField txtCorreo;
    private JPasswordField txtContrasena;

    public Interfazlogin() {
        setTitle("Inicio de sesión");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblCorreo = new JLabel("Correo:");
        txtCorreo = new JTextField();

        JLabel lblContrasena = new JLabel("Contraseña:");
        txtContrasena = new JPasswordField();

        JButton btnIngresar = new JButton("Iniciar sesión");
        JButton btnRegistro = new JButton("Registrarse");

        btnIngresar.addActionListener(e -> iniciarSesion());

        btnRegistro.addActionListener(e -> {
            new Interfazregistro().setVisible(true);
            dispose();
        });

        panel.add(lblCorreo);
        panel.add(txtCorreo);
        panel.add(lblContrasena);
        panel.add(txtContrasena);
        panel.add(btnRegistro);
        panel.add(btnIngresar);

        add(panel);
    }

    private void iniciarSesion() {
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Datos ingresados correctamente",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interfazlogin().setVisible(true);
        });
    }
}

