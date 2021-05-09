package view.panels;

import javax.swing.*;
import java.awt.*;

public class MessageDialog {

    public MessageDialog() {
        JFrame p = new JFrame("TEST");
        p.setUndecorated(true);
        p.setDefaultLookAndFeelDecorated(true);
        JLabel lable = new JLabel("test");
        p.add(lable);

        JOptionPane.showConfirmDialog(p, "dasda", "test", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

}
