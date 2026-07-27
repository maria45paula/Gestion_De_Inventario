import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal {

    private JPanel panelPrincipal;
    private JButton gestionarProductosEInventarioButton;
    private JButton gestionarPersonalButton;
    private JButton generarInformesButton;

    public VentanaPrincipal() {

        // Botón: Gestionar Productos e Inventario
        gestionarProductosEInventarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //ventana productos
            }
        });

        gestionarPersonalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String contraseña = JOptionPane.showInputDialog(
                        null,
                        "Ingrese la contraseña de administrador:",
                        "Acceso Restringido",
                        JOptionPane.QUESTION_MESSAGE
                );
                if (contraseña == null) {
                    return;
                }

                if (contraseña.equals("admin1234")) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Acceso concedido.",
                            "Correcto",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    //Ventana de personal

                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Contraseña incorrecta.",
                            "Acceso denegado",
                            JOptionPane.ERROR_MESSAGE
                    );

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