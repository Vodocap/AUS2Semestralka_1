package sk.uniza.fri.gui;

import sk.uniza.fri.aplikacia.Parcela;
import sk.uniza.fri.aplikacia.TrControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class MainWindow {
    private JFrame window;
    private JPanel leftPanel;
    private JPanel resultPanel;
    private TrControl trControl;
    private JList<Parcela> parcelaJList;
    private DefaultListModel<Parcela> parcelyListModel;

    public MainWindow() {
        this.trControl = new TrControl();
        this.window = new JFrame();
        this.leftPanel = new JPanel();
        this.resultPanel = new JPanel();
        this.window.setTitle("Aplikácia pre geodetov");
        this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.window.setLayout(new BorderLayout(10, 5));
        this.window.setSize(1000, 800);
        this.window.setLocationRelativeTo(null);
        this.leftPanel.setLayout(new BoxLayout(this.leftPanel, BoxLayout.Y_AXIS));
        this.leftPanel.setBackground(Color.BLACK);
        this.resultPanel.setLayout(new BorderLayout());
        this.window.add(this.resultPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        this.window.add(this.leftPanel, BorderLayout.WEST);

        this.parcelyListModel = new DefaultListModel<>();
        this.parcelaJList = new JList<>(this.parcelyListModel);
        this.parcelaJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane nehnutelnostiPane = new JScrollPane(this.parcelaJList);
        nehnutelnostiPane.setPreferredSize(new Dimension(400, 300));
        nehnutelnostiPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        nehnutelnostiPane.setAlignmentY(Component.CENTER_ALIGNMENT);

        Button najdiParcelButton = new Button("Najdi parcelu");
        Button najdiNehnutelnostButton = new Button("Najdi nehnutelnost");
        Button najdiVsetkyCelkyButton = new Button("Najdi vsetky uzemne celky");
        Button pridajParceluButton = new Button("Pridaj parcelu");
        Button pridajNehnutelnostButton = new Button("Pridaj nehnutelnost");
        Button upravParceluButton = new Button("Uprav parcelu");
        Button upravnehnutelnost = new Button("Uprav nehnutelnost");
        Button vyradParcelu = new Button("Vyrad parcelu");
        Button vyradNehnutelnost = new Button("Vyrad nehnutelnost");
        this.leftPanel.add(najdiParcelButton);
        this.leftPanel.add(najdiNehnutelnostButton);
        this.leftPanel.add(najdiVsetkyCelkyButton);
        this.leftPanel.add(pridajParceluButton);
        this.leftPanel.add(pridajNehnutelnostButton);
        this.leftPanel.add(upravnehnutelnost);
        this.leftPanel.add(upravParceluButton);
        this.leftPanel.add(vyradNehnutelnost);
        this.leftPanel.add(vyradParcelu);
        this.resultPanel.add(this.parcelaJList);
        this.resultPanel.add(nehnutelnostiPane, BorderLayout.CENTER);
        QueryWindow queryWindow = new QueryWindow();
        queryWindow.show();
        najdiParcelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double surX = queryWindow.getSurX();
                    double surY = queryWindow.getSurY();
                    ArrayList<Parcela> nehnutelnosti = MainWindow.this.trControl.najdiVsetkyParcely(surX, surY);
                    MainWindow.this.parcelyListModel.clear();
                    for (Parcela parcela : nehnutelnosti) {
                        System.out.println(parcela.toString());
                        MainWindow.this.parcelyListModel.addElement(parcela);
                    }
                    System.out.println(surX + " - " + surY);

                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Prosím zadajte platné hodnoty");
                }


            }
        });

        pridajParceluButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    queryWindow.dispose();
                    queryWindow.toggleAdditionalFields();
                    queryWindow.show();
                    double[] suradnice = new double[4];
                    suradnice[0] = queryWindow.getSurX();
                    suradnice[1] = queryWindow.getSurY();
                    suradnice[2] = queryWindow.getSurXSpodny();
                    suradnice[3] = queryWindow.getSurYSpodny();
                    MainWindow.this.trControl.pridajParcelu(queryWindow.getCislo(), queryWindow.getPopis(), suradnice );

                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Prosím zadajte platné hodnoty");
                }
            }
        });


        MainWindow.this.parcelaJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Parcela selectedNehnutelnost = MainWindow.this.parcelaJList.getSelectedValue();
                if (selectedNehnutelnost != null) {
                    JOptionPane.showMessageDialog(MainWindow.this.window, "Vybraná nehnuteľnosť:\n" + selectedNehnutelnost.toString());
                }
            }
        });



        najdiParcelButton.setBackground(Color.WHITE);
        najdiNehnutelnostButton.setBackground(Color.WHITE);
        najdiVsetkyCelkyButton.setBackground(Color.WHITE);
        pridajParceluButton.setBackground(Color.WHITE);
        pridajNehnutelnostButton.setBackground(Color.WHITE);
        upravnehnutelnost.setBackground(Color.WHITE);
        upravParceluButton.setBackground(Color.WHITE);
        vyradNehnutelnost.setBackground(Color.WHITE);
        vyradParcelu.setBackground(Color.WHITE);
    }

    public void show() {
        this.window.setVisible(true);
    }
}
