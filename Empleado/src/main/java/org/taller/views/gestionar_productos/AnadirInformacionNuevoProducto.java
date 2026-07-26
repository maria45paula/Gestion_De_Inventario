package main.java.org.taller.views.gestionar_productos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class AnadirInformacionNuevoProducto extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel lblIngresarDatos;
    private JLabel lblNombre;
    private JTextField textNombre;
    private JLabel lblPrecio;
    private JTextField textPrecio;
    private JLabel lblDescripcion;
    private JTextField textDescripcion;
    private JLabel lblCantidadDisponible;
    private JTextField textCantidadDisponible;

    public AnadirInformacionNuevoProducto() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        //AGFREGAR, NOMBRE, CATEGORÍA, PRECIO, DESCRIPCION, CANTIDAD

        try {
            conexionCliente.enviarPeticion("AGREGAR;" + textNombre.getText() + ";" + textPrecio.getText() + ";" + textDescripcion.getText() + ";");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        AnadirInformacionNuevoProducto dialog = new AnadirInformacionNuevoProducto();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
