package sk.uniza.fri.gui;

import javax.swing.*;
import java.awt.*;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class UpravCelokPopup {
    private JPanel panel1;
    private JPanel jPanel;
    private JTextField textFieldSurYD;
    private JTextField textFieldSurYH;
    private JTextField textFieldCislo;
    private JTextField textFieldPopis;
    private JTextField textFieldSmerXD;
    private JTextField textFieldSmertXH;
    private JTextField textFieldSurXD;
    private JTextField textFieldSurXH;
    private JButton pridatButton;
    private JButton zrusitButton;

    private void createUIComponents() {
        this.jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        jPanel.setMinimumSize(new Dimension(500, 500));
        jPanel.setName("pridajCelokQueryPanel");
        jPanel.setOpaque(true);
        jPanel.setPreferredSize(new Dimension(500, 500));
        jPanel.setVisible(true);    }
}
