package main.java.org.taller.views;

import main.java.org.taller.ConexionCliente;
import main.java.org.taller.views.gestionar_productos.AnadirInformacionNuevoProduct;
import main.java.org.taller.views.gestionar_productos.EliminarInformacion;
import main.java.org.taller.views.gestionar_productos.EditarInformacion.EditarInformacion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInventario extends JDialog {
    private JPanel contentPane;
    private JButton eliminarProductoButton;
    private JButton editarProductoButton;
    private JButton agregarProductoButton;

    public VentanaInventario(ConexionCliente conexionCliente) {
        setContentPane(contentPane);
        setModal(true);

        eliminarProductoButton.addActionListener(e -> {
            EliminarInformacion ventana = new EliminarInformacion();
            ventana.setVisible(true);
        });

        editarProductoButton.addActionListener(e -> {
            EditarInformacion ventana = new EditarInformacion();
            ventana.setVisible(true);
        });

        agregarProductoButton.addActionListener(e -> {
            AnadirInformacionNuevoProduct ventana = new AnadirInformacionNuevoProduct(conexionCliente);
            ventana.setVisible(true);
        });
}
