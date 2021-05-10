package view.panels;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class MessageDialog {

    public MessageDialog() {
        JOptionPane.showMessageDialog(null,
                getPanel(),
                "JOptionPane Example : ", JOptionPane.PLAIN_MESSAGE);
    }

    public JPanel getPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton btn = new JButton("TEST");
        panel.add(btn);
        return panel;
    }

}