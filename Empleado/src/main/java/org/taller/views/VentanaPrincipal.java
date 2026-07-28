package main.java.org.taller.views;

import main.java.org.taller.conexion.IConexionCliente;
import main.java.org.taller.validadores.Validador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que controla el formulario de la ventana principal
 */
public class VentanaPrincipal extends JDialog {
    private JPanel panelPrincipal;
    private JButton gestionarInventarioButton;
    private JButton gestionarPersonalButton;
    private JButton generarInformesButton;
    private IConexionCliente conexionCliente;

    /**
     * Constructor por parámetros de la clase
     * @param conexionCliente Objeto que se encarga de manejar la conexion con el servidor
     */
    public VentanaPrincipal(IConexionCliente conexionCliente) {
        this.conexionCliente = conexionCliente;
        setContentPane(panelPrincipal);
        setModal(true);

        gestionarInventarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaInventario ventanaInventario = new VentanaInventario(conexionCliente);
                ventanaInventario.setLocationRelativeTo(VentanaPrincipal.this);
                ventanaInventario.pack();
                ventanaInventario.setVisible(true);
                ventanaInventario.setSize(900, 600);
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
                    ventanaPersonal.setSize(900,600);
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
                Archivos archivos = new Archivos(conexionCliente);
                archivos.setSize(400,400);
                archivos.setLocationRelativeTo(null);
                archivos.setVisible(true);
            }
        });
    }

    /**
     * Getter del panel principal
     * @return JPanel panelPrincipal
     */
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
