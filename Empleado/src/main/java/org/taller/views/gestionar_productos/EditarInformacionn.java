package main.java.org.taller.views.gestionar_productos;

import main.java.org.taller.conexion.ConexionCliente;
import main.java.org.taller.conexion.IConexionCliente;
import main.java.org.taller.validadores.IValidador;
import main.java.org.taller.views.Error_;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class EditarInformacionn extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel lblEscogerProductoEditar;
    private JTextField textID;
    private JLabel txtEscogerDatoEditar;
    private JRadioButton JRBNombre;
    private JRadioButton RBPrecio;
    private JRadioButton RBCantidadDisponible;
    private JRadioButton RBDescripcion;
    private JRadioButton RBCategoria;
    private JLabel lblNuevoDato;
    private JTextField txtNuevoDato;
    private IConexionCliente conexionCliente;
    private IValidador validador;

    public EditarInformacionn(IConexionCliente conexionCliente) {
        this.conexionCliente = conexionCliente;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(JRBNombre);
        grupo.add(RBPrecio);
        grupo.add(RBCantidadDisponible);
        grupo.add(RBDescripcion);
        grupo.add(RBCategoria);


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
        try {
            if (validador.validarString(textID.getText())) {

// En el momento que necesites saber cuál está marcado:
                String seleccion = "";
                if (JRBNombre.isSelected()) seleccion = JRBNombre.getText();
                if (RBPrecio.isSelected()) seleccion = RBPrecio.getText();
                if (RBCantidadDisponible.isSelected()) seleccion = RBCantidadDisponible.getText();
                if (RBDescripcion.isSelected()) seleccion = RBDescripcion.getText();
                if (RBCategoria.isSelected()) seleccion = RBCategoria.getText();


                conexionCliente.enviarPeticion("MODIFICAR;" + textID.getText() + ";" + seleccion + ";" + txtNuevoDato.getText());
            } else {

                Error_ error = new Error_();
                error.setVisible(true);

                JOptionPane.showMessageDialog(null, "Profe, ¿cómo se atreve a intentar dañar el código? :( \n Y los datos están malos, de paso");


            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        EditarInformacionn dialog = new EditarInformacionn(new ConexionCliente("a", 13, "b", "c"));
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
