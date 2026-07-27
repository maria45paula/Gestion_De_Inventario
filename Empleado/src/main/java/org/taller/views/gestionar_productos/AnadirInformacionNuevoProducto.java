package main.java.org.taller.views.gestionar_productos;

import main.java.org.taller.ConexionCliente;
import main.java.org.taller.validadores.IValidador;
import main.java.org.taller.validadores.Validador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnadirInformacionNuevoProducto extends JDialog {
    private JPanel principal;
    private JButton btnOk;
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
    private ConexionCliente conexionCliente;
    private JComboBox cmbCategorias;
    private IValidador validador;


    public AnadirInformacionNuevoProducto() {
        validador = new Validador();
        //this.conexionCliente = conexionCliente;
        //setContentPane(principal);
        dialogInit();
        
        setModal(true);
        getRootPane().setDefaultButton(btnOk);


        btnOk.addActionListener(e -> {
            try {
                if (validador.validarString(textNombre.getText()) && validador.validarInt(textPrecio.getText()) &&
                        validador.validarString(textDescripcion.getText()) && validador.validarInt(textCantidadDisponible.getText())) {
                    String categoria = cmbCategorias.getSelectedItem().toString();

                    conexionCliente.enviarPeticion("AGREGAR;" + textNombre.getText() + ";" + categoria + ";" + textPrecio.getText() + ";" + textDescripcion.getText() + ";");
                } else {
                    JOptionPane.showMessageDialog(null, "Profe, ¿cómo se atreve a intentar dañar el código? :( \n Y los datos están malos, de paso");
                    Error error = new Error();

                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
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
        principal.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        configCmb();
    }

    private void onOK() {
        //AGFREGAR, NOMBRE, CATEGORÍA, PRECIO, DESCRIPCION, CANTIDAD

        try {
            if (validador.validarString(textNombre.getText()) && validador.validarInt(textPrecio.getText()) &&
                    validador.validarString(textDescripcion.getText()) && validador.validarInt(textCantidadDisponible.getText())) {
                String categoria = cmbCategorias.getSelectedItem().toString();

                conexionCliente.enviarPeticion("AGREGAR;" + textNombre.getText() + ";" + categoria + ";" + textPrecio.getText() + ";" + textDescripcion.getText() + ";");
            } else {
                JOptionPane.showMessageDialog(null, "Profe, ¿cómo se atreve a intentar dañar el código? :( \n Y los datos están malos, de paso");
                Error error = new Error();

            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

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

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        AnadirInformacionNuevoProducto dialog = new AnadirInformacionNuevoProducto();

        dialog.pack();
        dialog.setVisible(true);
    }
}
