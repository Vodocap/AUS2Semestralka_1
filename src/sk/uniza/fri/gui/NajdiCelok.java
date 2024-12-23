package sk.uniza.fri.gui;

import sk.uniza.fri.aplikacia.Nehnutelnost;
import sk.uniza.fri.aplikacia.Parcela;
import sk.uniza.fri.aplikacia.TrControl;
import sk.uniza.fri.aplikacia.UzemnyCelok;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
public class NajdiCelok extends JFrame {
    private JPanel jPanel;

    public JPanel getjPanel() {
        return this.jPanel;
    }

    private JPanel jPanel1;
    private JTextField textFieldSurYH;
    private JCheckBox nehnutelnostCheckBox;
    private JCheckBox parcelaCheckBox;
    private JTextField textFieldSurXH;
    private JButton najdiButton;
    private JButton zrusitButton;
    private JList<UzemnyCelok> list1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;
    private JTextPane textPane1;
    private JButton kontrolnyVypisButton;
    private JComboBox comboSmerXSirka;
    private JComboBox comboSmerYSirka;
    private JComboBox comboSmerXDlzka;
    private JComboBox comboSmerYDlzka;

    private MainWindow mainWindow;
    private TrControl trControl;

    public NajdiCelok(MainWindow paMainWindow) {
        this.createUIComponents();
        this.$$$setupUI$$$();

        this.mainWindow = paMainWindow;
        this.trControl = paMainWindow.getTrControl();

        this.zrusitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NajdiCelok.this.dispose();
            }
        });
        this.najdiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double[] suradnice = new double[2];
                    suradnice[0] = Double.parseDouble(NajdiCelok.this.textFieldSurXH.getText());
                    suradnice[1] = Double.parseDouble(NajdiCelok.this.textFieldSurYH.getText());

                    ArrayList<Parcela> parcelas = new ArrayList<>();
                    ArrayList<Nehnutelnost> nehnutelnosts = new ArrayList<>();
                    ArrayList<UzemnyCelok> uzemnyCeloks1 = new ArrayList<>();
                    ArrayList<UzemnyCelok> uzemnyCeloks2 = new ArrayList<>();
                    ArrayList<UzemnyCelok> vysledky = new ArrayList<>();

                    if (NajdiCelok.this.parcelaCheckBox.isSelected() && NajdiCelok.this.nehnutelnostCheckBox.isSelected()) {
                        uzemnyCeloks1 = NajdiCelok.this.trControl.najdiVsetkyObjekty(suradnice[0], suradnice[1],
                                ((String) NajdiCelok.this.comboSmerXSirka.getSelectedItem()).charAt(0), ((String) NajdiCelok.this.comboSmerYSirka.getSelectedItem()).charAt(0));
                        uzemnyCeloks2 = NajdiCelok.this.trControl.najdiVsetkyObjekty(Double.parseDouble(NajdiCelok.this.textField1.getText()), Double.parseDouble(NajdiCelok.this.textField2.getText())
                                , ((String) NajdiCelok.this.comboSmerXDlzka.getSelectedItem()).charAt(0), ((String) NajdiCelok.this.comboSmerYDlzka.getSelectedItem()).charAt(0));
                        vysledky.addAll(uzemnyCeloks2);
                        vysledky.addAll(uzemnyCeloks1);

                    }
                    if (NajdiCelok.this.parcelaCheckBox.isSelected() && !NajdiCelok.this.nehnutelnostCheckBox.isSelected()) {
                        parcelas = NajdiCelok.this.trControl.najdiVsetkyParcely(suradnice[0], suradnice[1]
                                , ((String) NajdiCelok.this.comboSmerXSirka.getSelectedItem()).charAt(0), ((String) NajdiCelok.this.comboSmerYSirka.getSelectedItem()).charAt(0));
                        vysledky.addAll(parcelas);

                    } else if (NajdiCelok.this.nehnutelnostCheckBox.isSelected() && !NajdiCelok.this.parcelaCheckBox.isSelected()) {
                        nehnutelnosts = NajdiCelok.this.trControl.najdiVsetkyNehnutelnosti(suradnice[0], suradnice[1],
                                ((String) NajdiCelok.this.comboSmerXSirka.getSelectedItem()).charAt(0), ((String) NajdiCelok.this.comboSmerYSirka.getSelectedItem()).charAt(0));
                        vysledky.addAll(nehnutelnosts);
                    }

                    NajdiCelok.this.list1.clearSelection();
                    Object[] objektyArray = vysledky.toArray();
                    UzemnyCelok[] vyslednyArray = new UzemnyCelok[objektyArray.length];
                    for (int i = 0; i < vyslednyArray.length; i++) {
                        vyslednyArray[i] = (UzemnyCelok) objektyArray[i];
                    }
                    NajdiCelok.this.list1.setListData(vyslednyArray);


                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Prosím zadajte platné hodnoty");
                }
            }
        });

        this.kontrolnyVypisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<Parcela> parcelas = new ArrayList<>();
                ArrayList<Nehnutelnost> nehnutelnosts = new ArrayList<>();
                ArrayList<UzemnyCelok> vysledky = new ArrayList<>();

                if (NajdiCelok.this.parcelaCheckBox.isSelected() && NajdiCelok.this.nehnutelnostCheckBox.isSelected()) {
                    parcelas = NajdiCelok.this.trControl.dajVsetkyParcely(true);
                    nehnutelnosts = NajdiCelok.this.trControl.dajVsetkyNehnutelnosti(true);
                    vysledky.addAll(parcelas);
                    vysledky.addAll(nehnutelnosts);

                }
                if (NajdiCelok.this.parcelaCheckBox.isSelected() && !NajdiCelok.this.nehnutelnostCheckBox.isSelected()) {
                    parcelas = NajdiCelok.this.trControl.dajVsetkyParcely(true);
                    vysledky.addAll(parcelas);

                } else if (NajdiCelok.this.nehnutelnostCheckBox.isSelected() && !NajdiCelok.this.parcelaCheckBox.isSelected()) {
                    nehnutelnosts = NajdiCelok.this.trControl.dajVsetkyNehnutelnosti(true);
                    vysledky.addAll(nehnutelnosts);
                }

                NajdiCelok.this.list1.clearSelection();
                Object[] objektyArray = vysledky.toArray();
                UzemnyCelok[] vyslednyArray = new UzemnyCelok[objektyArray.length];
                for (int i = 0; i < vyslednyArray.length; i++) {
                    vyslednyArray[i] = (UzemnyCelok) objektyArray[i];
                }
                NajdiCelok.this.list1.setListData(vyslednyArray);

            }
        });

        this.parcelaCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NajdiCelok.this.skontrolujViditelnostTextFieldov();
            }
        });

        this.nehnutelnostCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NajdiCelok.this.skontrolujViditelnostTextFieldov();
            }
        });


        this.list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                try {
                    if (e.getValueIsAdjusting() == false && NajdiCelok.this.list1.getSelectedValue() != null) {
                        NajdiCelok.this.textPane1.setText(NajdiCelok.this.list1.getSelectedValue().getStringObjektov());
                    } else if (NajdiCelok.this.list1.getSelectedValue() == null) {
                        NajdiCelok.this.textPane1.setText("");
                    }
                } finally {

                }
            }
        });

    }


    private void createUIComponents() {
        this.jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        jPanel.setMinimumSize(new Dimension(500, 500));
        jPanel.setName("NajdiCelok");
        jPanel.setOpaque(true);
        jPanel.setPreferredSize(new Dimension(500, 500));
        jPanel.setVisible(true);
    }

    private void skontrolujViditelnostTextFieldov() {
        if (NajdiCelok.this.parcelaCheckBox.isSelected() && NajdiCelok.this.nehnutelnostCheckBox.isSelected()) {
            NajdiCelok.this.textField1.setVisible(true);
            NajdiCelok.this.textField2.setVisible(true);
            NajdiCelok.this.comboSmerXDlzka.setVisible(true);
            NajdiCelok.this.comboSmerYDlzka.setVisible(true);


        } else {
            NajdiCelok.this.textField1.setVisible(false);
            NajdiCelok.this.textField2.setVisible(false);
            NajdiCelok.this.comboSmerXDlzka.setVisible(false);
            NajdiCelok.this.comboSmerYDlzka.setVisible(false);
        }
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
        jPanel1 = new JPanel();
        jPanel1.setLayout(new GridBagLayout());
        jPanel1.setEnabled(false);
        Font jPanel1Font = UIManager.getFont("Button.font");
        if (jPanel1Font != null) jPanel1.setFont(jPanel1Font);
        jPanel1.setMinimumSize(new Dimension(500, 500));
        jPanel1.setName("pridajCelokQueryPanel");
        jPanel1.setOpaque(true);
        jPanel1.setPreferredSize(new Dimension(400, 400));
        jPanel1.setVisible(true);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        jPanel.add(jPanel1, gbc);
        jPanel1.setBorder(BorderFactory.createTitledBorder(null, "Nájdi súradnice", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$("Cantarell Extra Bold", Font.BOLD, 20, jPanel1.getFont()), new Color(-16777216)));
        textFieldSurYH = new JTextField();
        textFieldSurYH.setMinimumSize(new Dimension(50, 35));
        textFieldSurYH.setPreferredSize(new Dimension(50, 35));
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(textFieldSurYH, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("X");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
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
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(textFieldSurXH, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Y");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
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
        gbc.gridx = 2;
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
        gbc.gridx = 2;
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
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(textField1, gbc);
        textField2 = new JTextField();
        textField2.setMinimumSize(new Dimension(50, 35));
        textField2.setPreferredSize(new Dimension(50, 35));
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(textField2, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Prekryvy");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel1.add(label5, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("Smery");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel1.add(label6, gbc);
        final JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setMinimumSize(new Dimension(100, 100));
        scrollPane2.setPreferredSize(new Dimension(100, 100));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.gridwidth = 10;
        gbc.fill = GridBagConstraints.BOTH;
        jPanel1.add(scrollPane2, gbc);
        textPane1 = new JTextPane();
        scrollPane2.setViewportView(textPane1);
        kontrolnyVypisButton = new JButton();
        kontrolnyVypisButton.setText("kontrolny vypis");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(kontrolnyVypisButton, gbc);
        comboSmerXSirka = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("N");
        defaultComboBoxModel1.addElement("S");
        comboSmerXSirka.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(comboSmerXSirka, gbc);
        comboSmerYSirka = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("E");
        defaultComboBoxModel2.addElement("W");
        comboSmerYSirka.setModel(defaultComboBoxModel2);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(comboSmerYSirka, gbc);
        comboSmerXDlzka = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("N");
        defaultComboBoxModel3.addElement("S");
        comboSmerXDlzka.setModel(defaultComboBoxModel3);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(comboSmerXDlzka, gbc);
        comboSmerYDlzka = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("E");
        defaultComboBoxModel4.addElement("W");
        comboSmerYDlzka.setModel(defaultComboBoxModel4);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(comboSmerYDlzka, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        jPanel.add(spacer2, gbc);
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

}
