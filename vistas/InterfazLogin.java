package vistas;

import javax.swing.*;
import java.awt.*;

public class InterfazLogin extends JFrame {

    public JTextField txtUsuario = new JTextField();
    public JPasswordField txtContrasena = new JPasswordField();
    public JButton btnIngresar = new JButton("Ingresar");

    public InterfazLogin() {
        setTitle("Login");
        setSize(300, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        panel.add(txtContrasena);

        panel.add(new JLabel());
        panel.add(btnIngresar);

        add(panel);
    }

    public static void main(String[] args) {
        new InterfazLogin().setVisible(true);
    }
}
