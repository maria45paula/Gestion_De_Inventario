package main.java.org.taller.views;

import main.java.org.taller.conexion.IConexionCliente;
import main.java.org.taller.validadores.Validador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JDialog {
    private JPanel panelPrincipal;
    private JButton gestionarInventarioButton;
    private JButton gestionarPersonalButton;
    private JButton generarInformesButton;
    private IConexionCliente conexionCliente;

    public VentanaPrincipal(IConexionCliente conexionCliente) {
        this.conexionCliente = conexionCliente;
        setContentPane(panelPrincipal);
        setModal(true);

        gestionarInventarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaInventario ventanaInventario = new VentanaInventario(conexionCliente);
                ventanaInventario.setVisible(true);
            }
        });


        gestionarPersonalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String contraseña = JOptionPane.showInputDialog(null, "Ingrese la contraseña de administrador:", "Acceso Restringido", JOptionPane.QUESTION_MESSAGE);
                if (contraseña == null) {
                    return;
                }
                if (contraseña.equals("admin1234")) {

                    JOptionPane.showMessageDialog(null, "Acceso concedido.", "Correcto", JOptionPane.INFORMATION_MESSAGE);

                    VentanaPersonal ventanaPersonal = new VentanaPersonal(conexionCliente, new Validador());
                    ventanaPersonal.pack();
                    ventanaPersonal.setLocationRelativeTo(VentanaPrincipal.this);
                    ventanaPersonal.setVisible(true);
                } else {

                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta.", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        generarInformesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ventana de Informes
            }
        });
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
