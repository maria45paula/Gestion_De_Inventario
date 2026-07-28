package main.java.org.taller.views;

import main.java.org.taller.conexion.IConexionCliente;
import main.java.org.taller.validadores.IValidador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Clase que controla la ventana de manejo de archivos
 */
public class Archivos extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton CSVACCIONESEMPLEADOSButton;
    private JButton CSVINVENTARIOButton;
    private IConexionCliente conexionCliente;
    private IValidador validador;

    /**
     * Constructor por parámetros de la clase que configura los elementos del formulario
     *
     * @param conexionCliente Objeto que maneja la conexion con el servidor
     */
    public Archivos(IConexionCliente conexionCliente) {
        this.conexionCliente = conexionCliente;
        this.validador = validador;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        CSVINVENTARIOButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onInventario();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        CSVACCIONESEMPLEADOSButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onLogs();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
     * Método llamado por el botón CSVINVENTARIO
     * Envía un mensaje al servidor
     * @throws IOException Si falla el envío
     */
    private void onInventario() throws IOException {
        conexionCliente.enviarPeticion("EXPORTAR");

    }

    /**
     * Método llamado por el botón CSVACCIONESEMPLEADOS
     * Envía un mensaje al servidor
     * @throws IOException Si falla el envío
     */
    private void onLogs() throws IOException {
        conexionCliente.enviarPeticion("EXPORTARLOGS");

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
