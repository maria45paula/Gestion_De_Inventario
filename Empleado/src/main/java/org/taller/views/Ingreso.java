package main.java.org.taller.views;

import main.java.org.taller.ConexionCliente;

import javax.swing.*;

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
    }

    private void loginConfig() {
        btnLogin.addActionListener(e -> {


        });
    }
}
