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
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que controla el form para añadir un nuevo producto
 */
public class AnadirInformacionNuevoProduct extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textNombre;
    private JComboBox cmbCategorias;
    private JTextField textPrecio;
    private JTextField textDescripcion;
    private JTextField textCantidadDisponible;
    private IValidador validador;
    private IConexionCliente conexionCliente;

    /**
     * Constructor por parámetros de la clase
     * Llama a los métodos de configuración de los elementos
     *
     * @param conexionCliente Objeto que maneja la conexion con el servidor
     * @param validador Objeto que puede validar Strings
     */
    public AnadirInformacionNuevoProduct(IConexionCliente conexionCliente, IValidador validador) {
        this.conexionCliente = conexionCliente;
        this.validador = validador;
        configCmb();

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
     * Método que es llamado en el momento que se presiona el boton Ok
     * Recoge los inputs del ususario y los verifica, en caso de que sean correctos envía
     * un mensaje al servidor, y en caso de ser incorrectos muestra un mensaje al usuario
     */
    private void onOK() {
        //AGFREGAR, NOMBRE, CATEGORÍA, PRECIO, DESCRIPCION, CANTIDAD

        try {
            if (validador.validarString(textNombre.getText()) && validador.validarInt(textPrecio.getText()) && validador.validarString(textDescripcion.getText()) && validador.validarInt(textCantidadDisponible.getText())) {
                String categoria = cmbCategorias.getSelectedItem().toString();

                conexionCliente.enviarPeticion("AGREGAR;" + textNombre.getText() + ";" + categoria + ";" + textPrecio.getText() + ";" + textDescripcion.getText() + ";"+textCantidadDisponible.getText());
            } else {

                System.out.println("Hola");
                Error_ error = new Error_();
                error.setVisible(true);

                JOptionPane.showMessageDialog(null, "Profe, ¿cómo se atreve a intentar dañar el código? :( \n Y los datos están malos, de paso");


            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Método llamado al presionar el botón Cancel
     * Cierra la ventana
     */
    private void onCancel() {
        dispose();
    }

    /**
     * Método que configura el ComboBox de Categorías
     */
    private void configCmb() {
        List<String> categorias = new ArrayList<>();
        categorias.add("ASEO");
        categorias.add("IMPLEMENTOS DE COCINA");
        categorias.add("ALIMENTOS");
        categorias.add("VEGETALES");
        categorias.add("CARNES");
        categorias.add("DULCES");

        categorias.stream().forEach(e -> cmbCategorias.addItem(e));
    }



}
