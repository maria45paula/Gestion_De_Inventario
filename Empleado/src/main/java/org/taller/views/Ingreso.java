package main.java.org.taller.views;

import main.java.org.taller.ConexionCliente;

import javax.swing.*;
import java.io.IOException;

public class Ingreso {
    private JLabel lblIngreso;
    private JLabel lblUsuario;
    private JTextField txtUsuario;
    private JLabel lblContrasena;
    private JPasswordField txtContrasena;
    private JButton btnLogin;
    private ConexionCliente conexionCliente;

    public Ingreso(ConexionCliente conexionCliente) {
        this.conexionCliente = conexionCliente;
        loginConfig();
    }

    private void loginConfig() {
        btnLogin.addActionListener(e -> {
            try {
                conexionCliente.enviarPeticion("AUTENTICAR;" + txtUsuario.getText() + ";" + txtContrasena.getText());
                String mensaje = conexionCliente.getUltimoMensaje();

                if (mensaje.equals("OK")) {
                    //Lógica para redireccionar
                } else {

                }


            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
    }
}
