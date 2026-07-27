package main.java.org.taller.views;

import javax.swing.*;
import java.awt.*;

public class Error {
    private JPanel panel1;
    private JLabel lblFoto;

    public Error() {
        loadImage();
        panel1.setVisible(true);

    }

    public JPanel getPanel() {
        return panel1;
    }

    public void setPanel(JPanel panel1) {
        this.panel1 = panel1;
    }

    public void loadImage() {
        ImageIcon original = new ImageIcon(getClass().getResource("/images/Profe_porque.jpeg"));
        Image img = original.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        lblFoto.setIcon(new ImageIcon(img));
    }
}
