package main.java.org.taller.views.gestionar_productos;

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

/**
 * Clase que controla el formulario para eliminar un Producto
 */
public class EliminarInformacionn extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel lblIDProductoEliminar;
    private JTextField textIDProductoEliminar;
    private IConexionCliente conexionCliente;
    private IValidador validador;


    /**
     * Constructor por parámetros de la clase
     * Configura los elementos del formulario
     * @param conexionCliente Objeto que maneja la conexion con el servidor
     * @param validador Objeto que puede validar Strings
     */
    public EliminarInformacionn(IConexionCliente conexionCliente,IValidador validador) {
        this.conexionCliente = conexionCliente;
        this.validador=validador;
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

    /**
     * Método que se llama al presionar el botón ok
     * Valida si el ID ingresado  es correcto
     * Y si lo es envía un mensaje el servidor
     */
    private void onOK() {
        try {
            if (validador.validarString(textIDProductoEliminar.getText())) {



                conexionCliente.enviarPeticion("ELIMINAR;" + textIDProductoEliminar.getText());
            } else {

                Error_ error = new Error_();
                error.setVisible(true);

                JOptionPane.showMessageDialog(null, "Profe, ¿cómo se atreve a intentar dañar el código? :( \n Y los datos están malos, de paso");


            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Método que se llama al presionar el botón cancel,
     * Cierra la ventana
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
