package main.java.org.taller.views;

import main.java.org.taller.conexion.IConexionCliente;

import javax.swing.*;
import java.io.IOException;

/**
 * Clase que maneja el formulario de login
 */
public class Ingreso {
    public JPanel login;
    private JTextField txtContrasena;
    private JTextField txtUsuario;
    private JLabel lblContrasena;
    private JLabel lblUsuario;
    private IConexionCliente conexionCliente;
    private JButton btnLogin;

    /**
     * Constructor por parámetros de la clase
     * @param conexionCliente Objeto que maneja la conexion con el servidor
     */
    public Ingreso(IConexionCliente conexionCliente) {
        this.conexionCliente = conexionCliente;
        loginConfig();

    }

    /**
     * Método que configura el botón Login
     * Cuando se presiona envia un mensaje al servidor para verificar el usuario
     * Y si es correcto abre la ventana principal
     * Si no lo es muestra un mensaje al usuario
     */
    public void loginConfig() {
        btnLogin.addActionListener(e -> {
            try {
                String mensaje = conexionCliente.enviarPeticionYEsperarRespuesta("AUTENTICAR;" + txtUsuario.getText() + ";" + txtContrasena.getText());

                if (mensaje != null && mensaje.startsWith("OK")) {
                    VentanaPrincipal menu = new VentanaPrincipal(conexionCliente);
                    menu.setSize(700,400);
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
