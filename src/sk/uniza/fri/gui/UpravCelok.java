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
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Locale;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class UpravCelok extends JFrame {
    private JPanel jPanel;
    private JPanel jPanel1;
    private JTextField textFieldSurYH;
    private JTextField textFieldSurXH;
    private JButton najdiButton;
    private JButton zrusitButton;
    private JCheckBox parcelaCheckBox;
    private JCheckBox nehnutelnostCheckBox;
    private JList<UzemnyCelok> list1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton upravButton;
    private MainWindow mainWindow;
    private TrControl trControl;

    public UpravCelok(MainWindow paMainWindow) {
        this.createUIComponents();
        this.mainWindow = paMainWindow;
        this.trControl = this.mainWindow.getTrControl();
        list1 = new JList<>();
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.setLayoutOrientation(JList.VERTICAL);
        list1.setVisibleRowCount(-1);
        $$$setupUI$$$();

        this.mainWindow = paMainWindow;
        this.trControl = paMainWindow.getTrControl();


        this.zrusitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpravCelok.this.dispose();
            }
        });

        this.upravButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    UpravCelokPopup upravCelokPopup = new UpravCelokPopup(UpravCelok.this.trControl, UpravCelok.this.list1.getSelectedValue());
                    upravCelokPopup.setContentPane(upravCelokPopup.$$$getRootComponent$$$());
                    upravCelokPopup.pack();
                    upravCelokPopup.setVisible(true);


                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Nie je vybratý žiadny územný celok");
                }
            }
        });


        this.najdiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double[] suradnice = new double[2];
                    suradnice[0] = Double.parseDouble(UpravCelok.this.textFieldSurXH.getText());
                    suradnice[1] = Double.parseDouble(UpravCelok.this.textFieldSurYH.getText());

                    ArrayList<Parcela> parcelas = new ArrayList<>();
                    ArrayList<Nehnutelnost> nehnutelnosts = new ArrayList<>();
                    ArrayList<UzemnyCelok> uzemnyCeloks1 = new ArrayList<>();
                    ArrayList<UzemnyCelok> uzemnyCeloks2 = new ArrayList<>();
                    ArrayList<Object> vysledky = new ArrayList<>();

                    if (UpravCelok.this.parcelaCheckBox.isSelected() && UpravCelok.this.nehnutelnostCheckBox.isSelected()) {
                        uzemnyCeloks1 = UpravCelok.this.trControl.najdiVsetkyObjekty(suradnice[0], suradnice[1]);
                        uzemnyCeloks2 = UpravCelok.this.trControl.najdiVsetkyObjekty(Double.parseDouble(UpravCelok.this.textField1.getText()), Double.parseDouble(UpravCelok.this.textField2.getText()));
                        vysledky.addAll(uzemnyCeloks2);
                        vysledky.addAll(uzemnyCeloks1);

                    }
                    if (UpravCelok.this.parcelaCheckBox.isSelected() && !UpravCelok.this.nehnutelnostCheckBox.isSelected()) {
                        parcelas = UpravCelok.this.trControl.najdiVsetkyParcely(suradnice[0], suradnice[1]);
                        vysledky.addAll(parcelas);

                    } else if (UpravCelok.this.nehnutelnostCheckBox.isSelected() && !UpravCelok.this.parcelaCheckBox.isSelected()) {
                        nehnutelnosts = UpravCelok.this.trControl.najdiVsetkyNehnutelnosti(suradnice[0], suradnice[1]);
                        vysledky.addAll(nehnutelnosts);
                    }

                    UpravCelok.this.list1.clearSelection();
                    Object[] objektyArray = vysledky.toArray();
                    UzemnyCelok[] vyslednyArray = new UzemnyCelok[objektyArray.length];
                    for (int i = 0; i < vyslednyArray.length; i++) {
                        vyslednyArray[i] = (UzemnyCelok) objektyArray[i];
                    }
                    UpravCelok.this.list1.setListData(vyslednyArray);


                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Prosím zadajte platné hodnoty");
                }
            }
        });


        this.parcelaCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpravCelok.this.skontrolujViditelnostTextFieldov();
            }
        });

        this.nehnutelnostCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpravCelok.this.skontrolujViditelnostTextFieldov();
            }
        });


        this.list1.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int index = UpravCelok.this.list1.locationToIndex(e.getPoint());
                String prekryvy = "Ziadne prekryvajuce celky";
                if (index > -1) {
                    prekryvy = UpravCelok.this.list1.getModel().getElementAt(index).toStringObjektov();
                    UpravCelok.this.list1.setToolTipText(prekryvy);

                }
            }
        });

    }

    private void skontrolujViditelnostTextFieldov() {
        if (UpravCelok.this.parcelaCheckBox.isSelected() && UpravCelok.this.nehnutelnostCheckBox.isSelected()) {
            UpravCelok.this.textField1.setVisible(true);
            UpravCelok.this.textField2.setVisible(true);
        } else {
            UpravCelok.this.textField1.setVisible(false);
            UpravCelok.this.textField2.setVisible(false);
        }
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
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(najdiButton, gbc);
        zrusitButton = new JButton();
        zrusitButton.setText("Zrušiť");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
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
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel1.add(parcelaCheckBox, gbc);
        nehnutelnostCheckBox = new JCheckBox();
        nehnutelnostCheckBox.setText("Nehnutelnosť");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel1.add(nehnutelnostCheckBox, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setPreferredSize(new Dimension(300, 300));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
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
        upravButton = new JButton();
        upravButton.setText("Uprav");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel1.add(upravButton, gbc);
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