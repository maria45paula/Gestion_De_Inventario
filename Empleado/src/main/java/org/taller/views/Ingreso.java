package main.java.org.taller.views;

import main.java.org.taller.conexion.IConexionCliente;

import javax.swing.*;
import java.io.IOException;

public class Ingreso {
    public JPanel login;
    private JTextField txtContrasena;
    private JTextField txtUsuario;
    private JLabel lblContrasena;
    private JLabel lblUsuario;
    private IConexionCliente conexionCliente;
    private JButton btnLogin;

    public Ingreso(IConexionCliente conexionCliente) {
        this.conexionCliente = conexionCliente;
        loginConfig();

    }

    public void loginConfig() {
        btnLogin.addActionListener(e -> {
            try {
                String mensaje = conexionCliente.enviarPeticionYEsperarRespuesta("AUTENTICAR;" + txtUsuario.getText() + ";" + txtContrasena.getText());

                if (mensaje != null && mensaje.startsWith("OK")) {
                    VentanaPrincipal menu = new VentanaPrincipal(conexionCliente);
                    menu.setSize(300,400);
                    menu.setLocationRelativeTo(null);
                    menu.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(login, "Usuario o contrasena incorrectos, o el servidor no respondio.");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(login, "Error de conexion: " + ex.getMessage());
            }

        });
    }



}
