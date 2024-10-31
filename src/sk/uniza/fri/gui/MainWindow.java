package sk.uniza.fri.gui;

import sk.uniza.fri.aplikacia.Parcela;
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
public class MainWindow {
    private JFrame window;
    private JPanel leftPanel;

    private TrControl trControl;


    public MainWindow() {
        this.trControl = new TrControl();
        this.window = new JFrame();
        this.leftPanel = new JPanel();
        this.window.setTitle("Aplikácia pre geodetov");
        this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.window.setLayout(new BorderLayout(10, 5));
        this.window.setSize(1000, 800);
        this.window.setLocationRelativeTo(null);
        this.leftPanel.setLayout(new BoxLayout(this.leftPanel, BoxLayout.Y_AXIS));
        this.leftPanel.setBackground(Color.BLACK);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        this.window.add(this.leftPanel, BorderLayout.WEST);


        Button najdiCelokButtom = new Button("Najdi uzemne celky");
        Button pridajCelokButton = new Button("Pridaj Uzemny celok");
        Button upravCelok = new Button("Uprav parcelu");
        Button vygenerujCelky = new Button("Vygeneruj Uzemne celky");
        Button vyradCelok = new Button("Vyrad uzemny celok");
        Button nacitajZoSuboru = new Button("Nacitaj databazu zo suborov");
        Button ulozDoSuboru = new Button("Uloz databazu do suborov");
        this.leftPanel.add(najdiCelokButtom);
        this.leftPanel.add(pridajCelokButton);
        this.leftPanel.add(vygenerujCelky);
        this.leftPanel.add(upravCelok);
        this.leftPanel.add(vyradCelok);
        this.leftPanel.add(nacitajZoSuboru);
        this.leftPanel.add(ulozDoSuboru);

        najdiCelokButtom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    NajdiCelok najdiCelokQuery = new NajdiCelok(MainWindow.this);
                    najdiCelokQuery.setContentPane(najdiCelokQuery.getjPanel());
                    najdiCelokQuery.pack();
                    najdiCelokQuery.setVisible(true);


                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Prosím zadajte platné hodnoty");
                }


            }
        });


        pridajCelokButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PridajCelokQuery pridajCelokQuery = new PridajCelokQuery(MainWindow.this);
                pridajCelokQuery.setContentPane(pridajCelokQuery.getjPanel());
                pridajCelokQuery.pack();
                pridajCelokQuery.setVisible(true);

            }
        });

        vyradCelok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VymazCelokForm vyradCelok = new VymazCelokForm(MainWindow.this);
                vyradCelok.setContentPane(vyradCelok.getjPanel());
                vyradCelok.pack();
                vyradCelok.setVisible(true);
            }
        });



        najdiCelokButtom.setBackground(Color.WHITE);
        pridajCelokButton.setBackground(Color.WHITE);
        vygenerujCelky.setBackground(Color.WHITE);
        upravCelok.setBackground(Color.WHITE);
        vyradCelok.setBackground(Color.WHITE);
        ulozDoSuboru.setBackground(Color.WHITE);
        nacitajZoSuboru.setBackground(Color.WHITE);
    }

    public void show() {
        this.window.setVisible(true);
    }

    public TrControl getTrControl() {
        return this.trControl;
    }
}
