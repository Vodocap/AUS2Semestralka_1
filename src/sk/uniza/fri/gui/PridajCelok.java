package sk.uniza.fri.gui;

import sk.uniza.fri.aplikacia.TrControl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class PridajCelok extends JFrame {
    private JTextField textFieldSurXD;
    private JTextField textFieldSurYD;
    private JTextField textFieldSurYH;
    private JTextField textFieldSurXH;
    private JCheckBox parcelaCheckBox;
    private JCheckBox nehnutelnostCheckBox;
    private JTextField textFieldCislo;
    private JTextField textFieldPopis;
    private JButton pridatButton;
    private JButton zrusitButton;
    private JPanel jPanel;
    private JComboBox comboSmerXSirka;
    private JComboBox comboSmerYSirka;
    private JComboBox comboSmerXDlzka;
    private JComboBox comboSmerYDlzka;
    private final MainWindow mainWindow;
    private final TrControl trControl;


    public PridajCelok(MainWindow paMainWindow) {
        this.createUIComponents();
        $$$setupUI$$$();
        setTitle("Zadajte dáta");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        this.mainWindow = paMainWindow;
        this.trControl = paMainWindow.getTrControl();
        this.pridatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double[] suradnice = new double[4];
                    char[] smery = new char[4];
                    suradnice[0] = Double.parseDouble(PridajCelok.this.textFieldSurXH.getText());
                    suradnice[1] = Double.parseDouble(PridajCelok.this.textFieldSurYH.getText());
                    suradnice[2] = Double.parseDouble(PridajCelok.this.textFieldSurXD.getText());
                    suradnice[3] = Double.parseDouble(PridajCelok.this.textFieldSurYD.getText());

                    smery[0] = ((String) PridajCelok.this.comboSmerXSirka.getSelectedItem()).charAt(0);
                    smery[1] = ((String) PridajCelok.this.comboSmerYSirka.getSelectedItem()).charAt(0);
                    smery[2] = ((String) PridajCelok.this.comboSmerXDlzka.getSelectedItem()).charAt(0);
                    smery[3] = ((String) PridajCelok.this.comboSmerYDlzka.getSelectedItem()).charAt(0);

                    if (PridajCelok.this.parcelaCheckBox.isSelected()) {
                        PridajCelok.this.trControl.pridajParcelu(
                                Integer.parseInt(PridajCelok.this.textFieldCislo.getText()),
                                PridajCelok.this.textFieldPopis.getText(),
                                suradnice, smery, true
                        );
                        JOptionPane.showMessageDialog(null, "Úspešne pridané");
                    } else if (PridajCelok.this.nehnutelnostCheckBox.isSelected()) {
                        PridajCelok.this.trControl.pridajNehnutelnost(
                                Integer.parseInt(PridajCelok.this.textFieldCislo.getText()),
                                PridajCelok.this.textFieldPopis.getText(),
                                suradnice, smery, true
                        );
                        JOptionPane.showMessageDialog(null, "Úspešne pridané");
                    }
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Prosím zadajte platné hodnoty");
                }
            }
        });

        this.parcelaCheckBox.addItemListener(e -> {
            if (PridajCelok.this.parcelaCheckBox.isSelected()) {
                PridajCelok.this.nehnutelnostCheckBox.setSelected(false);
            }
        });

        this.nehnutelnostCheckBox.addItemListener(e -> {
            if (PridajCelok.this.nehnutelnostCheckBox.isSelected()) {
                PridajCelok.this.parcelaCheckBox.setSelected(false);
            }
        });

        this.zrusitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PridajCelok.this.dispose();
            }
        });


    }

    public JPanel getjPanel() {
        return this.jPanel;
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        jPanel.setLayout(new GridBagLayout());
        jPanel.setEnabled(false);
        jPanel.setMinimumSize(new Dimension(500, 500));
        jPanel.setName("pridajCelokQueryPanel");
        jPanel.setOpaque(true);
        jPanel.setPreferredSize(new Dimension(500, 500));
        jPanel.setVisible(true);
        jPanel.setBorder(BorderFactory.createTitledBorder(null, "Pridaj územný celok", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("Cantarell Extra Bold", Font.BOLD, 20, jPanel.getFont()), new Color(-16777216)));
        textFieldSurYD = new JTextField();
        textFieldSurYD.setMinimumSize(new Dimension(50, 35));
        textFieldSurYD.setPreferredSize(new Dimension(50, 35));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(textFieldSurYD, gbc);
        textFieldSurYH = new JTextField();
        textFieldSurYH.setMinimumSize(new Dimension(50, 35));
        textFieldSurYH.setPreferredSize(new Dimension(50, 35));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(textFieldSurYH, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("X");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label1, gbc);
        textFieldCislo = new JTextField();
        textFieldCislo.setMinimumSize(new Dimension(50, 35));
        textFieldCislo.setPreferredSize(new Dimension(50, 35));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(textFieldCislo, gbc);
        textFieldPopis = new JTextField();
        textFieldPopis.setMinimumSize(new Dimension(70, 35));
        textFieldPopis.setPreferredSize(new Dimension(70, 35));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 8;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(textFieldPopis, gbc);
        nehnutelnostCheckBox = new JCheckBox();
        nehnutelnostCheckBox.setText("Nehnutelnosť");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(nehnutelnostCheckBox, gbc);
        parcelaCheckBox = new JCheckBox();
        parcelaCheckBox.setText("Parcela");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(parcelaCheckBox, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Lavy vrchny roh");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Pravy spodny roh");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label3, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("Lavy vrchny roh");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label4, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Pravy spodny roh");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label5, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("Číslo");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label6, gbc);
        final JLabel label7 = new JLabel();
        label7.setText("Popis");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label7, gbc);
        textFieldSurXD = new JTextField();
        textFieldSurXD.setMinimumSize(new Dimension(50, 35));
        textFieldSurXD.setPreferredSize(new Dimension(50, 35));
        textFieldSurXD.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(textFieldSurXD, gbc);
        textFieldSurXH = new JTextField();
        textFieldSurXH.setMinimumSize(new Dimension(50, 35));
        textFieldSurXH.setPreferredSize(new Dimension(50, 35));
        textFieldSurXH.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(textFieldSurXH, gbc);
        final JLabel label8 = new JLabel();
        label8.setText("Súradnice");
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label8, gbc);
        final JLabel label9 = new JLabel();
        label9.setText("Smery");
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label9, gbc);
        final JLabel label10 = new JLabel();
        label10.setText("Y");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        jPanel.add(label10, gbc);
        comboSmerXSirka = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("N");
        defaultComboBoxModel1.addElement("S");
        comboSmerXSirka.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(comboSmerXSirka, gbc);
        comboSmerYSirka = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("E");
        defaultComboBoxModel2.addElement("W");
        comboSmerYSirka.setModel(defaultComboBoxModel2);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(comboSmerYSirka, gbc);
        comboSmerXDlzka = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("N");
        defaultComboBoxModel3.addElement("S");
        comboSmerXDlzka.setModel(defaultComboBoxModel3);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(comboSmerXDlzka, gbc);
        comboSmerYDlzka = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("E");
        defaultComboBoxModel4.addElement("W");
        comboSmerYDlzka.setModel(defaultComboBoxModel4);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(comboSmerYDlzka, gbc);
        pridatButton = new JButton();
        pridatButton.setText("Pridať");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(pridatButton, gbc);
        zrusitButton = new JButton();
        zrusitButton.setText("Zrušiť");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(zrusitButton, gbc);
        final JLabel label11 = new JLabel();
        label11.setText("Zadajte informácie");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label11, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return jPanel;
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