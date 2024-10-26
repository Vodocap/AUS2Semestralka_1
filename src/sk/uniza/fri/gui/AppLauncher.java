package sk.uniza.fri.gui;

import javax.swing.*;

/**
 * 8. 10. 2024 - 14:38
 *
 * @author matus
 */
public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow main = new MainWindow();
                main.show();
            }
        });
    }

}
