package view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import controller.Controller;
import java.awt.event.ActionListener;



public class MessageDialog extends JLayeredPane implements ActionListener {
    private JButton yesBtn, noBtn;
    private Controller controller;
    private JLayeredPane dialog;

    public MessageDialog(Controller controller){
        this.controller = controller;
         MessageDialogPanel();
    }
    public void MessageDialogPanel() {
        Dimension toolkit = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(500, 150);
        setLocation((toolkit.width/3)-150, toolkit.height/3);
        setBackground(Color.white);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.white);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 3,15,3);

        JLabel label = new JLabel ("Are you sure you want to remove?");
        label.setFont(new Font("Calibri", Font.PLAIN, 15));
        label.setVisible(true);
        label.setBackground(Color.white);
        gbc.gridx = 100;
        gbc.gridy = 0;
        gbc.gridwidth = 150;
        gbc.anchor = GridBagConstraints.LINE_START;
        contentPanel.add(label, gbc);

        yesBtn = new JButton("YES");
        yesBtn.setVisible(true);
        yesBtn.setPreferredSize(new Dimension(25,25));
        yesBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        yesBtn.setBackground(new Color(220, 229, 185));
        yesBtn.setForeground(Color.darkGray);
        yesBtn.addActionListener(this);
        yesBtn.setFocusPainted(false);
        yesBtn.setFocusable(false);
        yesBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridx = 200;
        gbc.gridy = 8;
        gbc.ipadx = 50;
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(yesBtn, gbc);

        noBtn = new JButton("NO");
        noBtn.setVisible(true);
        noBtn.setPreferredSize(new Dimension(25,25));
        noBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        noBtn.setBackground(Color.darkGray);
        noBtn.setForeground(Color.WHITE);
        noBtn.addActionListener(this);
        noBtn.setFocusPainted(false);
        noBtn.setFocusable(false);
        noBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridx = 215;
        gbc.gridy = 8;
        gbc.ipadx = 50;
        gbc.anchor = GridBagConstraints.EAST;
        contentPanel.add(noBtn, gbc);

        add(contentPanel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == yesBtn) {
            controller.buttonPushed("remove plant");
            dialog.setVisible(false);
        } else if (e.getSource() == noBtn) {
            controller.buttonPushed("plantList");
            dialog.setVisible(false);
        }
    }
}