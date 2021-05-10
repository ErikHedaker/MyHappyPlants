package view.panels;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class MessageDialog {

    public MessageDialog() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) dim.getWidth();
        int screenHeight = (int) dim.getHeight();

        JDialog dialog = new JDialog();
        dialog.setSize(600, 400);
        dialog.setBackground(Color.white);
        dialog.setLocation(screenWidth / 2 - 150, screenHeight / 2 - 150);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,3,6,3);

        JLabel label = new JLabel("WANNA REMOVE?! WANNA FUCKING KILL YOUR PLANT?!");
        label.setVisible(true);
        label.setBackground(Color.white);
        gbc.gridx = 5;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(label, gbc);

        JButton yesBtn = new JButton("YES");
        yesBtn.setVisible(true);
        yesBtn.setBackground(Color.white);
        gbc.gridx = 5;
        gbc.gridy = 5;
        gbc.ipadx = 50;
        gbc.anchor = GridBagConstraints.WEST;
        dialog.add(yesBtn, gbc);

        JButton noBtn = new JButton("NO");
        noBtn.setVisible(true);
        noBtn.setBackground(Color.white);
        gbc.gridx = 6;
        gbc.gridy = 5;
        gbc.ipadx = 50;
        gbc.anchor = GridBagConstraints.EAST;
        dialog.add(noBtn, gbc);



        dialog.setVisible(true);

        /*JOptionPane.showMessageDialog(null,
                getPanel(),
                "JOptionPane Example : ", JOptionPane.PLAIN_MESSAGE);*/
    }

    /*public JPanel getPanel() {
        JPanel panel = new JPanel();
        JButton btn = new JButton("TEST");
        panel.add(btn);
        return panel;
    }*/

}