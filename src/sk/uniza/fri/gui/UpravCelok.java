package sk.uniza.fri.gui;

import javax.swing.*;
import java.awt.*;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class UpravCelok {
    private JPanel jPanel;
    private JPanel jPanel1;
    private JTextField textFieldSurYH;
    private JTextField textFieldSurXH;
    private JButton najdiButton;
    private JButton zrusitButton;
    private JCheckBox parcelaCheckBox;
    private JCheckBox nehnutelnostCheckBox;
    private JList list1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton upravButton;

    private void createUIComponents() {
        this.jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        jPanel.setMinimumSize(new Dimension(500, 500));
        jPanel.setName("pridajCelokQueryPanel");
        jPanel.setOpaque(true);
        jPanel.setPreferredSize(new Dimension(500, 500));
        jPanel.setVisible(true);    }
}
