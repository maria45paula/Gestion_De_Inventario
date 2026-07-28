package main.java.org.taller;


import main.java.org.taller.conexion.ConexionCliente;
import main.java.org.taller.views.Ingreso;

import javax.swing.*;

public class ClientePrincipal {

    public static void main(String[] args) {
        ConexionSocket conexionSocket = new ConexionSocket();
        ConexionCliente conexion = conexionSocket.conectar();
        JFrame ingresar = new JFrame("login");
        Ingreso ingreso = new Ingreso(conexion);
        ingreso.loginConfig();
        ingresar.setContentPane(ingreso.login);
        ingresar.pack();
        ingresar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ingresar.setLocationRelativeTo(null);
        ingresar.setSize(600, 600);
        ingresar.setVisible(true);
    }
}