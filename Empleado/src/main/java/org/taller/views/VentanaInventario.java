package main.java.org.taller.views;

import main.java.org.taller.conexion.IConexionCliente;
import main.java.org.taller.validadores.Validador;
import main.java.org.taller.views.gestionar_productos.AnadirInformacionNuevoProduct;
import main.java.org.taller.views.gestionar_productos.EditarInformacionn;
import main.java.org.taller.views.gestionar_productos.EliminarInformacionn;

import javax.swing.*;

public class VentanaInventario extends JDialog {
    private JPanel contentPane;
    private JButton eliminarProductoButton;
    private JButton editarProductoButton;
    private JButton agregarProductoButton;
    private IConexionCliente conexion;

    public VentanaInventario(IConexionCliente conexionCliente) {
        this.conexion = conexionCliente;
        setContentPane(contentPane);
        setModal(true);

        eliminarProductoButton.addActionListener(e -> {
            EliminarInformacionn ventana = new EliminarInformacionn(conexion,new Validador());
            ventana.setSize(800,600);
            ventana.setVisible(true);
        });

        editarProductoButton.addActionListener(e -> {
            EditarInformacionn ventana = new EditarInformacionn(conexion,new Validador());
            ventana.setSize(800,600);
            ventana.setVisible(true);
        });


        agregarProductoButton.addActionListener(e -> {
            AnadirInformacionNuevoProduct ventana = new AnadirInformacionNuevoProduct(conexion, new Validador());
            ventana.setSize(800,600);
            ventana.setVisible(true);
        });
    }
}

