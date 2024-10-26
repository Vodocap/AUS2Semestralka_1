package sk.uniza.fri.gui;

import javax.swing.*;
import java.awt.*;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class QueryWindow {

    private JFrame queryFrame;
    private JPanel queryPanel;
    private JTextField textFieldX;
    private JTextField textFieldY;
    private JTextField textFieldXSpodny;
    private JTextField textFieldYSpodny;
    private JTextField textFieldCislo;
    private JTextField textFieldPopis;
    private JTextField textFieldSmerX;
    private JTextField textFieldSmerY;

    public QueryWindow() {
        this.queryFrame = new JFrame();
        this.queryPanel = new JPanel();
        this.textFieldX = new JTextField(10);
        this.textFieldY = new JTextField(10);
        this.textFieldXSpodny = new JTextField(10);
        this.textFieldYSpodny = new JTextField(10);
        this.textFieldCislo = new JTextField(10);
        this.textFieldPopis = new JTextField(10);
        this.textFieldSmerX = new JTextField(10);
        this.textFieldSmerY = new JTextField(10);


        this.queryFrame.setTitle("Vložte príslušné dáta");
        this.queryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.queryFrame.setLayout(new BorderLayout());
        this.queryFrame.setSize(400, 400);
        this.queryFrame.setLocationRelativeTo(null);

        this.queryPanel.setLayout(new BoxLayout(this.queryPanel, BoxLayout.Y_AXIS));

        JLabel labelSurX = new JLabel("Zadaj zemepisnú šírku:");
        JLabel labelSurY = new JLabel("Zadaj zemepisnú dĺžku:");
        JLabel labelXSpodny = new JLabel("Zadaj zemepisnú šírku (spodný roh):");
        JLabel labelYSpodny = new JLabel("Zadaj zemepisnú dĺžku (spodný roh):");
        JLabel labelCislo = new JLabel("Zadaj číslo:");
        JLabel labelPopis = new JLabel("Zadaj Popis celku:");
        JLabel labelSmerX = new JLabel("Zadaj smer zemepisnej šírky:");
        JLabel labelSmerY = new JLabel("Zadaj smer zemepisnej dĺžky:");

        this.queryPanel.add(labelSurX);
        this.queryPanel.add(this.textFieldX);
        this.queryPanel.add(labelSurY);
        this.queryPanel.add(this.textFieldY);
        this.queryPanel.add(labelXSpodny);
        this.queryPanel.add(this.textFieldXSpodny);
        this.queryPanel.add(labelYSpodny);
        this.queryPanel.add(this.textFieldYSpodny);
        this.queryPanel.add(labelCislo);
        this.queryPanel.add(this.textFieldCislo);
        this.queryPanel.add(labelPopis);
        this.queryPanel.add(this.textFieldPopis);
        this.queryPanel.add(labelSmerX);
        this.queryPanel.add(this.textFieldSmerX);
        this.queryPanel.add(labelSmerY);
        this.queryPanel.add(this.textFieldSmerY);

        labelCislo.setVisible(false);
        this.textFieldCislo.setVisible(false);
        labelPopis.setVisible(false);
        this.textFieldPopis.setVisible(false);
        labelSmerX.setVisible(false);
        this.textFieldSmerX.setVisible(false);
        labelSmerY.setVisible(false);
        this.textFieldSmerY.setVisible(false);
        labelXSpodny.setVisible(false);
        this.textFieldXSpodny.setVisible(false);
        labelYSpodny.setVisible(false);
        this.textFieldYSpodny.setVisible(false);

        this.queryFrame.add(this.queryPanel, BorderLayout.CENTER);

    }

    public double getSurX() {
        return Double.parseDouble(this.textFieldX.getText());
    }

    public double getSurY() {
        return Double.parseDouble(this.textFieldY.getText());
    }

    public double getSurXSpodny() {
        return Double.parseDouble(this.textFieldXSpodny.getText());
    }

    public double getSurYSpodny() {
        return Double.parseDouble(this.textFieldYSpodny.getText());
    }


    public int getCislo() {
        return Integer.parseInt(this.textFieldCislo.getText());
    }

    public String getPopis() {
        return this.textFieldPopis.getText();
    }

    public char getSmerX() {
        return this.textFieldSmerX.getText().charAt(0);
    }

    public char getSmerY() {
        return this.textFieldSmerY.getText().charAt(0);
    }

    public void hideAdditionalFields() {
        this.textFieldCislo.setVisible(false);
        this.textFieldPopis.setVisible(false);
        this.textFieldSmerX.setVisible(false);
        this.textFieldSmerY.setVisible(false);
        this.textFieldXSpodny.setVisible(false);
        this.textFieldYSpodny.setVisible(false);
        this.queryPanel.revalidate();
        this.queryPanel.repaint();
        for (Component component : this.queryPanel.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                if (label.getText().contains("číslo") || label.getText().contains("Popis") ||
                        label.getText().contains("smer zemepisnej šírky") || label.getText().contains("smer zemepisnej dĺžky") ||
                        label.getText().contains("Zadaj zemepisnú šírku (spodný roh):") || label.getText().contains("Zadaj zemepisnú dĺžku (spodný roh):")) {
                    label.setVisible(false);
                }
            }
        }

        this.queryPanel.revalidate();
        this.queryPanel.repaint();

    }

    public void toggleAdditionalFields() {
        boolean isVisible = this.textFieldCislo.isVisible();
        this.textFieldCislo.setVisible(!isVisible);
        this.textFieldPopis.setVisible(!isVisible);
        this.textFieldSmerX.setVisible(!isVisible);
        this.textFieldSmerY.setVisible(!isVisible);
        this.textFieldXSpodny.setVisible(!isVisible);
        this.textFieldYSpodny.setVisible(!isVisible);

        for (Component component : queryPanel.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                if (label.getText().contains("číslo") || label.getText().contains("Popis") ||
                        label.getText().contains("smer zemepisnej šírky") || label.getText().contains("smer zemepisnej dĺžky") ||
                        label.getText().contains("Zadaj zemepisnú šírku (spodný roh):") || label.getText().contains("Zadaj zemepisnú dĺžku (spodný roh):")) {
                    label.setVisible(!isVisible);
                }
            }
        }

        this.queryPanel.revalidate();
        this.queryPanel.repaint();
    }
    public void show() {
        this.queryFrame.setVisible(true);
    }

    public void dispose() {
        this.queryFrame.setVisible(false);
    }

}
