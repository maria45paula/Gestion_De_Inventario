package main.java.org.taller.views;

import javax.swing.*;
import java.awt.*;

public class Error_ extends JDialog {
    private JPanel contentPane;
    private JLabel lblFoto;


    public Error_() {
        setContentPane(contentPane);
        setModal(true);
        pack();
        setSize(300, 300);
        loadImage();
    }

    public void loadImage() {
        ImageIcon original = new ImageIcon(getClass().getResource("/images/Profe_porque.jpeg"));
        Image img = original.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        lblFoto.setIcon(new ImageIcon(img));
    }
}
