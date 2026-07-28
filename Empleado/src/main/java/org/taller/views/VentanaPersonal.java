package main.java.org.taller.views;

import main.java.org.taller.conexion.IConexionCliente;
import main.java.org.taller.validadores.IValidador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Clase que controla el formulario de manejo de persional
 */
public class VentanaPersonal extends JDialog {
    private JPanel panelPrincipal;
    private JButton eliminarEmpleadoButton;
    private JButton agregarEmpleadoButton;
    private IConexionCliente conexionCliente;
    private IValidador validador;

    /**
     * Constructor por parámetros de la clase
     * @param conexionCliente Objeto que maneja la conexion con el servidor
     * @param validador Objeto que puede validar Strings
     */
    public VentanaPersonal(IConexionCliente conexionCliente, IValidador validador) {

        this.conexionCliente = conexionCliente;
        this.validador = validador;

        setContentPane(panelPrincipal);
        setModal(true);

        agregarEmpleadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEmpleado();
            }
        });

        eliminarEmpleadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEmpleado();
            }
        });
    }

    /**
     * Método que agrega un empelado, le pide los datos al usuario y los envía al servidor
     */
    private void agregarEmpleado() {
        String usuario = JOptionPane.showInputDialog("Ingrese el nombre de usuario:");
        if (usuario == null) {
            return;
        }
        String contrasena = JOptionPane.showInputDialog("Ingrese la contraseña:");
        if (contrasena == null) {
            return;
        }

        try {

            if (validador.validarString(usuario) && validador.validarString(contrasena)) {

                conexionCliente.enviarPeticion("AGREGAREMPLEADO;" + usuario + ";" + contrasena);
                JOptionPane.showMessageDialog(null, "Empleado agregado correctamente.");
            } else {
                Error_ error = new Error_();
                error.setVisible(true);
                JOptionPane.showMessageDialog(null, "Los datos ingresados no son válidos.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}




