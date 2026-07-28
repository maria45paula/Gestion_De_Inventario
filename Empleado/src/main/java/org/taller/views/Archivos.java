package main.java.org.taller.views;

import main.java.org.taller.conexion.IConexionCliente;
import main.java.org.taller.validadores.IValidador;
import main.java.org.taller.accionesistema.ExportarCSV;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Clase que controla el formulario de gestión de archivos
 */
public class Archivos extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JButton CSVACCIONESEMPLEADOSButton;
    private JButton CSVINVENTARIOButton;
    private IConexionCliente conexionCliente;
    private IValidador validador;

    /**
     * Constructor por parámetros
     * @param conexionCliente Objeto que maneja la conexion con el servidor
     */
    public Archivos(IConexionCliente conexionCliente) {
        this.conexionCliente = conexionCliente;
        this.validador = validador;
        setContentPane(contentPane);
        setModal(true);


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
     * Método llamado en el botón inventario
     * @throws IOException si hay un error en la escritura del archivo
     */
    private void onInventario() throws IOException {


        String respuesta =
                conexionCliente.enviarPeticionYEsperarRespuesta(
                        "EXPORTAR"
                );


        if(respuesta.startsWith("OK;")){
            String datos = respuesta.substring(3);
            ExportarCSV.crearInventarioCSV(datos);

            JOptionPane.showMessageDialog(this, "Inventario creado correctamente");
        }else{
            JOptionPane.showMessageDialog(this, respuesta);
        }
    }

    /**
     * Método que se llama en el botón Acciones
     * @throws IOException si hay un error al escribir el archivo
     */
        private void onLogs() throws IOException {

            String respuesta = conexionCliente.enviarPeticionYEsperarRespuesta("EXPORTARLOGS");

            if(respuesta.startsWith("OK;")){
                String datos = respuesta.substring(3);
                ExportarCSV.crearAuditoriaCSV(datos);
                JOptionPane.showMessageDialog(this, "Auditoria creada correctamente");
            }else{
                JOptionPane.showMessageDialog(this, respuesta);
            }
        }

    /**
     * Método llamado en el botón cancel
     * Cierra la ventana
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
