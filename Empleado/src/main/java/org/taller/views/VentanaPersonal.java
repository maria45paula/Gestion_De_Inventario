package main.java.org.taller.views;

import main.java.org.taller.conexion.IConexionCliente;
import main.java.org.taller.validadores.IValidador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class VentanaPersonal extends JDialog {
    private JPanel panelPrincipal;
    private JButton eliminarEmpleadoButton;
    private JButton agregarEmpleadoButton;
    private IConexionCliente conexionCliente;
    private IValidador validador;

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

    private void agregarEmpleado() {
        String usuario = JOptionPane.showInputDialog("Ingrese el nombre de usuario:");
        if (usuario == null) {
            return;
        }
        String contrasena = JOptionPane.showInputDialog("Ingrese la contraseña:");
        if (contrasena == null) {
            return;
        }
        String idTexto = JOptionPane.showInputDialog("Ingrese el ID del empleado:");
        if (idTexto == null) {
            return;
        }

        try {

            if (validador.validarString(usuario)
                    && validador.validarString(contrasena)
                    && validador.validarInt(idTexto)) {

                conexionCliente.enviarPeticion("AGREGAR_EMPLEADO;" + usuario + ";" + contrasena + ";" + idTexto + ";");
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


    private void eliminarEmpleado() {
        String idTexto = JOptionPane.showInputDialog("Ingrese el ID del empleado a eliminar:");
        if (idTexto == null) {
            return;
        }
        try {

            if (validador.validarInt(idTexto)) {
                conexionCliente.enviarPeticion("ELIMINAR_EMPLEADO;" + idTexto + ";");
                JOptionPane.showMessageDialog(null, "Solicitud enviada correctamente.");
            } else {
                Error_ error = new Error_();
                error.setVisible(true);
                JOptionPane.showMessageDialog(null, "El ID ingresado no es válido.");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}




