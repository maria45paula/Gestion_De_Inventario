package org.taller.views.gestionar_productos;

import javax.swing.*;
import java.awt.event.*;

public class EditarInformacion extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel lblEscogerProductoEditar;
    private JTextField textNombreProductoEditar;
    private JLabel txtEscogerDatoEditar;
    private JRadioButton JRBNombre;
    private JRadioButton RBPrecio;
    private JRadioButton RBCantidadDisponible;
    private JRadioButton RBDescripcion;
    private JLabel lblNuevoDato;
    private JTextField txtNuevoDato;

    public EditarInformacion() {
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
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        EditarInformacion dialog = new EditarInformacion();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
