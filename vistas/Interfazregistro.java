


import javax.swing.*;
import java.awt.*;

public class Interfazregistro extends JFrame {

    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;

    public Interfazregistro() {

        setTitle("Registro de usuario");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        JLabel lblCorreo = new JLabel("Correo:");
        txtCorreo = new JTextField();

        JLabel lblContrasena = new JLabel("Contraseña:");
        txtContrasena = new JPasswordField();

        JButton btnRegistrar = new JButton("Registrarse");

        btnRegistrar.addActionListener(e -> registrarUsuario());

        panel.add(lblNombre);
        panel.add(txtNombre);

        panel.add(lblCorreo);
        panel.add(txtCorreo);

        panel.add(lblContrasena);
        panel.add(txtContrasena);

        panel.add(new JLabel(""));
        panel.add(btnRegistrar);

        add(panel);
    }

    private void registrarUsuario() {

        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Todos los campos son obligatorios",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Usuario registrado correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interfazregistro().setVisible(true);
        });
    }
}
