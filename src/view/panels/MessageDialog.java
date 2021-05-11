package view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import controller.Controller;

import java.awt.event.ActionListener;

public class MessageDialog implements ActionListener {
    private JLabel image;
    private JButton yesBtn, noBtn;
    private Controller controller;
    private JDialog dialog;

    public MessageDialog() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) dim.getWidth();
        int screenHeight = (int) dim.getHeight();

        dialog = new JDialog();
        dialog.setSize(600, 400);
        dialog.setBackground(Color.white);
        dialog.setLocation(screenWidth / 2 - 300, screenHeight / 2 - 150);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,3,30,3);

        JLabel label = new JLabel ("REMOVE?", JLabel.CENTER);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        label.setVisible(true);
        label.setBackground(Color.white);
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.gridwidth = 12;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(label, gbc);

        /*image = new JLabel("", JLabel.CENTER);
        image.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));*/

        ImageIcon image = new ImageIcon("./images/plant.jpg");
        Image img = image.getImage();
        Image images = img.getScaledInstance(75,75,0);
        image = new ImageIcon(images);
        JLabel imgLabel = new JLabel(image);
        gbc.gridx = 5;
        gbc.gridy = 1;
        dialog.add(imgLabel, gbc);
        JPanel buttons = new JPanel(new GridLayout(0, 2, 0, 0));
        buttons.setBackground(Color.white);
        buttons.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        yesBtn = new JButton("YES");
        yesBtn.setVisible(true);
        yesBtn.setFont(new Font("Arial", Font.BOLD, 18));
        yesBtn.setBackground(new Color(220, 229, 185));
        yesBtn.setForeground(Color.darkGray);
        yesBtn.addActionListener(this);
        yesBtn.setFocusPainted(false);
        yesBtn.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(93, 118, 77)));
        yesBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.ipadx = 50;
        gbc.anchor = GridBagConstraints.WEST;
        dialog.add(yesBtn, gbc);

        noBtn = new JButton("NO");
        noBtn.setVisible(true);
        noBtn.setFont(new Font("Arial", Font.BOLD, 18));
        noBtn.setBackground(new Color(220, 229, 185));
        noBtn.setForeground(Color.darkGray);
        noBtn.addActionListener(this);
        noBtn.setFocusPainted(false);
        noBtn.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(93, 118, 77)));
        noBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridx = 6;
        gbc.gridy = 2;
        gbc.ipadx = 50;
        gbc.anchor = GridBagConstraints.EAST;
        dialog.add(noBtn, gbc);


        dialog.setVisible(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == yesBtn) {
            controller.buttonPushed("remove plant");
        } else if (e.getSource() == noBtn) {
            controller.buttonPushed("plantList");
        }
    }
}