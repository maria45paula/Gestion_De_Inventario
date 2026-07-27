package main.java.org.taller.views.gestionar_productos;

import main.java.org.taller.ConexionCliente;
import main.java.org.taller.validadores.IValidador;
import main.java.org.taller.validadores.Validador;
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
    private ConexionCliente conexionCliente;

    public AnadirInformacionNuevoProduct(ConexionCliente conexionCliente) {
        this.conexionCliente = conexionCliente;
        validador = new Validador();
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

    private void onOK() {
        //AGFREGAR, NOMBRE, CATEGORÍA, PRECIO, DESCRIPCION, CANTIDAD

        try {
            if (validador.validarString(textNombre.getText()) && validador.validarInt(textPrecio.getText()) &&
                    validador.validarString(textDescripcion.getText()) && validador.validarInt(textCantidadDisponible.getText())) {
                String categoria = cmbCategorias.getSelectedItem().toString();

                conexionCliente.enviarPeticion("AGREGAR;" + textNombre.getText() + ";" + categoria + ";" + textPrecio.getText() + ";" + textDescripcion.getText() + ";");
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

    private void onCancel() {
        dispose();
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

    public static void main(String[] args) {
        AnadirInformacionNuevoProduct dialog = new AnadirInformacionNuevoProduct(new ConexionCliente("a", 13, "b", "c"));
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
