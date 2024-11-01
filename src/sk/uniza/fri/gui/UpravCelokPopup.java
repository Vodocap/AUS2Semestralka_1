package sk.uniza.fri.gui;

import sk.uniza.fri.aplikacia.TrControl;
import sk.uniza.fri.aplikacia.UzemnyCelok;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class UpravCelokPopup extends JFrame {
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
    private UzemnyCelok upravovanyCelok;
    private TrControl trControl;

    public UpravCelokPopup(TrControl paTrControl, UzemnyCelok paUpravovanyCelok) {
        this.createUIComponents();
        $$$setupUI$$$();
        this.trControl = paTrControl;
        this.upravovanyCelok = paUpravovanyCelok;

    }

    private void createUIComponents() {
        this.jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        jPanel.setMinimumSize(new Dimension(500, 500));
        jPanel.setName("pridajCelokQueryPanel");
        jPanel.setOpaque(true);
        jPanel.setPreferredSize(new Dimension(500, 500));
        jPanel.setVisible(true);
        this.panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setMinimumSize(new Dimension(500, 500));
        panel1.setName("pridajCelokQueryPanel");
        panel1.setOpaque(true);
        panel1.setPreferredSize(new Dimension(500, 500));
        panel1.setVisible(true);
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
        panel1.setLayout(new GridBagLayout());
        jPanel.setLayout(new GridBagLayout());
        jPanel.setMinimumSize(new Dimension(500, 500));
        jPanel.setName("pridajCelokQueryPanel");
        jPanel.setOpaque(true);
        jPanel.setPreferredSize(new Dimension(500, 500));
        jPanel.setVisible(true);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel1.add(jPanel, gbc);
        jPanel.setBorder(BorderFactory.createTitledBorder(null, "Pridaj územný celok", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("Cantarell Extra Bold", Font.BOLD, 20, jPanel.getFont()), new Color(-16777216)));
        textFieldSurYD = new JTextField();
        textFieldSurYD.setMinimumSize(new Dimension(50, 35));
        textFieldSurYD.setPreferredSize(new Dimension(50, 35));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(textFieldSurYD, gbc);
        textFieldSurYH = new JTextField();
        textFieldSurYH.setMinimumSize(new Dimension(50, 35));
        textFieldSurYH.setPreferredSize(new Dimension(50, 35));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(textFieldSurYH, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("X");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label1, gbc);
        textFieldCislo = new JTextField();
        textFieldCislo.setMinimumSize(new Dimension(50, 35));
        textFieldCislo.setPreferredSize(new Dimension(50, 35));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(textFieldCislo, gbc);
        textFieldPopis = new JTextField();
        textFieldPopis.setMinimumSize(new Dimension(70, 35));
        textFieldPopis.setPreferredSize(new Dimension(70, 35));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 8;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(textFieldPopis, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Zadajte informácie");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Lavy vrchny roh");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label3, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("Pravy spodny roh");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label4, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Lavy vrchny roh");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label5, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("Pravy spodny roh");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label6, gbc);
        final JLabel label7 = new JLabel();
        label7.setText("Číslo");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label7, gbc);
        final JLabel label8 = new JLabel();
        label8.setText("Popis");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label8, gbc);
        textFieldSmerXD = new JTextField();
        textFieldSmerXD.setMinimumSize(new Dimension(50, 30));
        textFieldSmerXD.setPreferredSize(new Dimension(50, 35));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(textFieldSmerXD, gbc);
        textFieldSmertXH = new JTextField();
        textFieldSmertXH.setMinimumSize(new Dimension(50, 34));
        textFieldSmertXH.setPreferredSize(new Dimension(50, 35));
        textFieldSmertXH.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(textFieldSmertXH, gbc);
        textFieldSurXD = new JTextField();
        textFieldSurXD.setMinimumSize(new Dimension(50, 35));
        textFieldSurXD.setPreferredSize(new Dimension(50, 35));
        textFieldSurXD.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(textFieldSurXD, gbc);
        textFieldSurXH = new JTextField();
        textFieldSurXH.setMinimumSize(new Dimension(50, 35));
        textFieldSurXH.setPreferredSize(new Dimension(50, 35));
        textFieldSurXH.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(textFieldSurXH, gbc);
        final JLabel label9 = new JLabel();
        label9.setText("Súradnice");
        gbc = new GridBagConstraints();
        gbc.gridx = 10;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label9, gbc);
        final JLabel label10 = new JLabel();
        label10.setText("Smery");
        gbc = new GridBagConstraints();
        gbc.gridx = 10;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel.add(label10, gbc);
        final JLabel label11 = new JLabel();
        label11.setText("Y");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        jPanel.add(label11, gbc);
        pridatButton = new JButton();
        pridatButton.setText("Uprav");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(pridatButton, gbc);
        zrusitButton = new JButton();
        zrusitButton.setText("Zrušiť");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(zrusitButton, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer2, gbc);
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
        return panel1;
    }

}
