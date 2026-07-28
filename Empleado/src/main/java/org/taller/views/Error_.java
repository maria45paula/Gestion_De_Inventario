package main.java.org.taller.views;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que maneja la ventana de error
 */
public class Error_ extends JDialog {
    private JPanel contentPane;
    private JLabel lblFoto;

    /**
     * Constructor de la clase, configura los elementos
     */
    public Error_() {
        setContentPane(contentPane);
        setModal(true);
        pack();
        setSize(300, 300);
        loadImage();
    }

    /**
     * Carga la imagen para el lblFoto
     */
    public void loadImage() {
        ImageIcon original = new ImageIcon(getClass().getResource("/images/Profe_porque.jpeg"));
        Image img = original.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        lblFoto.setIcon(new ImageIcon(img));
    }
}
