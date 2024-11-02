package sk.uniza.fri.gui;

import sk.uniza.fri.aplikacia.Nehnutelnost;
import sk.uniza.fri.aplikacia.Parcela;
import sk.uniza.fri.aplikacia.TrControl;
import sk.uniza.fri.aplikacia.UzemnyCelok;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class VymazCelokForm extends JFrame {
    private JTextField textFieldSurYH;
    private JTextField textFieldSurXH;
    private JButton najdiButton;
    private JButton zrusitButton;
    private JCheckBox parcelaCheckBox;
    private JCheckBox nehnutelnostCheckBox;
    private JList<Object> list1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton vymazButton;
    private JPanel jPanel1;
    private JPanel panel1;
    private JTextField smerXSirka;
    private JTextField smerYSirka;
    private JTextField smerXDlzka;
    private JTextField smerYDlzka;
    private MainWindow mainWindow;
    private TrControl trControl;


    public VymazCelokForm(MainWindow paMainWindow) {
        this.createUIComponents();
        $$$setupUI$$$();
        this.list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.list1.setLayoutOrientation(JList.VERTICAL);
        this.list1.setVisibleRowCount(-1);
        this.mainWindow = paMainWindow;
        this.trControl = this.mainWindow.getTrControl();

        this.zrusitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VymazCelokForm.this.dispose();
            }

        });
        this.najdiButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                try {
                    double[] suradnice = new double[4];
                    suradnice[0] = Double.parseDouble(VymazCelokForm.this.textFieldSurXH.getText());
                    suradnice[1] = Double.parseDouble(VymazCelokForm.this.textFieldSurYH.getText());

                    ArrayList<Parcela> parcelas = new ArrayList<>();
                    ArrayList<Nehnutelnost> nehnutelnosts = new ArrayList<>();
                    ArrayList<UzemnyCelok> uzemnyCeloks1 = new ArrayList<>();
                    ArrayList<UzemnyCelok> uzemnyCeloks2 = new ArrayList<>();


                    ArrayList<Object> vysledky = new ArrayList<>();

                    if (VymazCelokForm.this.parcelaCheckBox.isSelected() && VymazCelokForm.this.nehnutelnostCheckBox.isSelected()) {
                        uzemnyCeloks1 = VymazCelokForm.this.trControl.najdiVsetkyObjekty(suradnice[0], suradnice[1],
                                VymazCelokForm.this.smerXSirka.getText().charAt(0), VymazCelokForm.this.smerYSirka.getText().charAt(0));
                        uzemnyCeloks2 = VymazCelokForm.this.trControl.najdiVsetkyObjekty(Double.parseDouble(VymazCelokForm.this.textField1.getText()), Double.parseDouble(VymazCelokForm.this.textField2.getText())
                                , VymazCelokForm.this.smerXDlzka.getText().charAt(0), VymazCelokForm.this.smerYDlzka.getText().charAt(0));
                        vysledky.addAll(uzemnyCeloks2);
                        vysledky.addAll(uzemnyCeloks1);

                    }
                    if (VymazCelokForm.this.parcelaCheckBox.isSelected() && !VymazCelokForm.this.nehnutelnostCheckBox.isSelected()) {
                        parcelas = VymazCelokForm.this.trControl.najdiVsetkyParcely(suradnice[0], suradnice[1], VymazCelokForm.this.smerXSirka.getText().charAt(0), VymazCelokForm.this.smerYSirka.getText().charAt(0));
                        vysledky.addAll(parcelas);

                    } else if (VymazCelokForm.this.nehnutelnostCheckBox.isSelected() && !VymazCelokForm.this.parcelaCheckBox.isSelected()) {
                        nehnutelnosts = VymazCelokForm.this.trControl.najdiVsetkyNehnutelnosti(suradnice[0], suradnice[1], VymazCelokForm.this.smerXSirka.getText().charAt(0), VymazCelokForm.this.smerYSirka.getText().charAt(0));
                        vysledky.addAll(nehnutelnosts);
                    }

                    VymazCelokForm.this.list1.clearSelection();
                    VymazCelokForm.this.list1.setListData(vysledky.toArray());


                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Prosím zadajte platné hodnoty");
                }
            }

        });

        this.vymazButton.addActionListener(new ActionListener() {
            @Override


            public void actionPerformed(ActionEvent e) {

                try {
                    VymazCelokForm.this.trControl.vymazUzemnyCelok((UzemnyCelok) VymazCelokForm.this.list1.getSelectedValue());
                    JOptionPane.showMessageDialog(null, "Vymazané" + VymazCelokForm.this.list1.getSelectedValue().toString());
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Nie sú žiadne prvky na vymazanie");
                }

            }

        });

        this.parcelaCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                skontrolujViditelnostTextFieldov();
            }
        });

        this.nehnutelnostCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                skontrolujViditelnostTextFieldov();
            }
        });
    }

    private void skontrolujViditelnostTextFieldov() {
        if (VymazCelokForm.this.parcelaCheckBox.isSelected() && VymazCelokForm.this.nehnutelnostCheckBox.isSelected()) {
            VymazCelokForm.this.textField1.setVisible(true);
            VymazCelokForm.this.textField2.setVisible(true);
            VymazCelokForm.this.smerXDlzka.setVisible(true);
            VymazCelokForm.this.smerYDlzka.setVisible(true);
        } else {
            VymazCelokForm.this.textField1.setVisible(false);
            VymazCelokForm.this.textField2.setVisible(false);
            VymazCelokForm.this.smerXDlzka.setVisible(false);
            VymazCelokForm.this.smerYDlzka.setVisible(false);
        }
    }

    private void createUIComponents() {
        this.panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setMinimumSize(new Dimension(500, 500));
        panel1.setName("VymazCelok");
        panel1.setOpaque(true);
        panel1.setPreferredSize(new Dimension(500, 500));
        panel1.setVisible(true);
    }

    public JPanel getjPanel() {
        return this.panel1;
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
        panel1.setEnabled(false);
        jPanel1 = new JPanel();
        jPanel1.setLayout(new GridBagLayout());
        jPanel1.setEnabled(false);
        jPanel1.setMinimumSize(new Dimension(500, 500));
        jPanel1.setName("pridajCelokQueryPanel");
        jPanel1.setOpaque(true);
        jPanel1.setPreferredSize(new Dimension(400, 400));
        jPanel1.setVisible(true);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel1.add(jPanel1, gbc);
        jPanel1.setBorder(BorderFactory.createTitledBorder(null, "Nájdi súradnice", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("Cantarell Extra Bold", Font.BOLD, 20, jPanel1.getFont()), new Color(-16777216)));
        textFieldSurYH = new JTextField();
        textFieldSurYH.setMinimumSize(new Dimension(50, 35));
        textFieldSurYH.setPreferredSize(new Dimension(50, 35));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(textFieldSurYH, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("X");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel1.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Zadajte informácie");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel1.add(label2, gbc);
        textFieldSurXH = new JTextField();
        textFieldSurXH.setMinimumSize(new Dimension(50, 35));
        textFieldSurXH.setPreferredSize(new Dimension(50, 35));
        textFieldSurXH.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(textFieldSurXH, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Y");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        jPanel1.add(label3, gbc);
        najdiButton = new JButton();
        najdiButton.setText("Nájdi");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(najdiButton, gbc);
        zrusitButton = new JButton();
        zrusitButton.setText("Zrušiť");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(zrusitButton, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("Súradnice");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel1.add(label4, gbc);
        parcelaCheckBox = new JCheckBox();
        parcelaCheckBox.setText("Parcela");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel1.add(parcelaCheckBox, gbc);
        nehnutelnostCheckBox = new JCheckBox();
        nehnutelnostCheckBox.setText("Nehnutelnosť");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel1.add(nehnutelnostCheckBox, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setPreferredSize(new Dimension(300, 300));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 9;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.BOTH;
        jPanel1.add(scrollPane1, gbc);
        list1 = new JList();
        scrollPane1.setViewportView(list1);
        textField1 = new JTextField();
        textField1.setMinimumSize(new Dimension(50, 35));
        textField1.setPreferredSize(new Dimension(50, 35));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(textField1, gbc);
        textField2 = new JTextField();
        textField2.setMinimumSize(new Dimension(50, 35));
        textField2.setPreferredSize(new Dimension(50, 35));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(textField2, gbc);
        vymazButton = new JButton();
        vymazButton.setText("vymaz");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(vymazButton, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Smery");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel1.add(label5, gbc);
        smerXSirka = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(smerXSirka, gbc);
        smerYSirka = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(smerYSirka, gbc);
        smerXDlzka = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(smerXDlzka, gbc);
        smerYDlzka = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(smerYDlzka, gbc);
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
