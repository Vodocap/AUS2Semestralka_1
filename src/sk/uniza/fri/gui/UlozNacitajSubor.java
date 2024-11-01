package sk.uniza.fri.gui;

import javax.swing.*;
import java.awt.*;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class UlozNacitajSubor {
    private JPanel panel1;

    private void createUIComponents() {
        this.panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setMinimumSize(new Dimension(500, 500));
        panel1.setName("pridajCelokQueryPanel");
        panel1.setOpaque(true);
        panel1.setPreferredSize(new Dimension(500, 500));
        panel1.setVisible(true);    }
}
