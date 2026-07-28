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
                conexionCliente.enviarPeticion("AUTENTICAR;" + txtUsuario.getText() + ";" + txtContrasena.getText());
                String mensaje = conexionCliente.getUltimoMensaje();

                if (mensaje.equals("OK")) {
                    VentanaPrincipal menu = new VentanaPrincipal(conexionCliente);
                    menu.setVisible(true);
                } else {

                }


            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
    }

}
