package org.taller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VentanaGestionPersonal {

    private JButton agregarEmpleadoButton;
    private JButton eliminarEmpleadoButton;
    private JButton buscarInformacionDeUnButton;
    private JPanel panelPrincipal;

    private ArrayList<Empleado> empleados;

    public VentanaGestionPersonal() {
        empleados = new ArrayList<>();
        agregarEmpleadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = JOptionPane.showInputDialog("Ingrese el nombre de usuario:");
                if (usuario == null || usuario.trim().isEmpty()) {
                    return;
                }
                String contrasena = JOptionPane.showInputDialog("Ingrese la contraseña:");
                if (contrasena == null || contrasena.trim().isEmpty()) {
                    return;
                }
                String ipTexto = JOptionPane.showInputDialog("Ingrese el ID del empleado:");
                if (ipTexto == null) {
                    return;
                }
                try {
                    int id = Integer.parseInt(ipTexto);
                    empleados.add(new Empleado(usuario, contrasena, id));
                    JOptionPane.showMessageDialog(null,
                            "Empleado agregado correctamente.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "El ID debe ser un número.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        buscarInformacionDeUnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = JOptionPane.showInputDialog("Ingrese el usuario a buscar:");
                if (usuario == null) {
                    return;
                }
                boolean encontrado = false;
                for (Empleado empleado : empleados) {
                    if (empleado.getUsuario().equalsIgnoreCase(usuario)) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Usuario: " + empleado.getUsuario()
                                        + "\nContraseña: " + empleado.getContrasena()
                                        + "\nID: " + empleado.getIp(),
                                "Empleado encontrado",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    JOptionPane.showMessageDialog(
                            null,
                            "No existe un empleado con ese usuario.",
                            "No encontrado",
                            JOptionPane.WARNING_MESSAGE
                    );
                }

            }
        });

        eliminarEmpleadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String usuario = JOptionPane.showInputDialog("Ingrese el usuario del empleado a eliminar:");

                if (usuario == null) {
                    return;
                }

                Empleado empleadoEliminar = null;

                for (Empleado empleado : empleados) {

                    if (empleado.getUsuario().equalsIgnoreCase(usuario)) {
                        empleadoEliminar = empleado;
                        break;
                    }

                }

                if (empleadoEliminar != null) {

                    int opcion = JOptionPane.showConfirmDialog(
                            null,
                            "¿Está seguro de eliminar al empleado " + empleadoEliminar.getUsuario() + "?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (opcion == JOptionPane.YES_OPTION) {
                        empleados.remove(empleadoEliminar);
                        JOptionPane.showMessageDialog(
                                null,
                                "Empleado eliminado correctamente."
                        );

                    }

                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Empleado no encontrado.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                }

            }
        });

    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
