package sk.uniza.fri.gui;

import sk.uniza.fri.aplikacia.TrControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class VygenerujData extends JFrame{
    private JPanel jPanel;
    private JTextField textField3;
    private JButton vygenerujButton;
    private JTextField textField4;
    private JButton zrusButton;
    private JTextField textField1;
    private MainWindow mainWindow;
    private TrControl trControl;

    public VygenerujData (MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.trControl = mainWindow.getTrControl();
        this.vygenerujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    trControl.generujData(Integer.parseInt(textField4.getText()), Integer.parseInt(textField1.getText()), Double.parseDouble(textField3.getText()));

                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Prosím zadajte platné hodnoty");
                }
            }
        });


    }
    private void createUIComponents() {
        this.jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        jPanel.setMinimumSize(new Dimension(500, 500));
        jPanel.setName("pridajCelokQueryPanel");
        jPanel.setOpaque(true);
        jPanel.setPreferredSize(new Dimension(500, 500));
        jPanel.setVisible(true);



    }
}
