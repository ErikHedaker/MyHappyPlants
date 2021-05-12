package view.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import controller.Controller;


public class ConfirmationDialog implements ActionListener {

    private JButton yesBtn, noBtn;
    private Controller controller;
    private JDialog dialog;
    private JLabel message;
    private DialogType type;

    public ConfirmationDialog(Controller controller){
        this.controller = controller;
         MessageDialogPanel();
    }
    public void MessageDialogPanel() {
        dialog = new JDialog();
        dialog.setSize(450, 150);
        dialog.setBackground(Color.white);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(dim.width / 2 - 225, dim.height / 2 - 125);
        dialog.setLayout(new GridBagLayout());
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        dialog.setUndecorated(true);
        dialog.getRootPane().setOpaque(false);
        dialog.getContentPane().setBackground(Color.white);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                dialog.setVisible(false);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 3,5,3);

        message = new JLabel ("");
        message.setFont(new Font("Calibri", Font.PLAIN, 17));
        message.setVisible(true);
        message.setBackground(Color.white);
        gbc.gridy = 0;
        gbc.gridwidth = 150;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(message, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        dialog.add(new JLabel(), gbc);

        yesBtn = new JButton("YES");
        yesBtn.setVisible(true);
        yesBtn.setPreferredSize(new Dimension(20,30));
        yesBtn.setFont(new Font("Arial", Font.BOLD, 12));
        yesBtn.setBackground(new Color(220, 229, 185));
        yesBtn.setForeground(Color.darkGray);
        yesBtn.addActionListener(this);
        yesBtn.setFocusPainted(false);
        yesBtn.setFocusable(false);
        yesBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridx = 1;
        gbc.insets = new Insets(0,25,0,0);
        gbc.gridy = 4;
        gbc.ipadx = 50;
        gbc.anchor = GridBagConstraints.EAST;
        dialog.add(yesBtn, gbc);

        noBtn = new JButton("NO");
        noBtn.setVisible(true);
        noBtn.setPreferredSize(new Dimension(20,30));
        noBtn.setFont(new Font("Arial", Font.BOLD, 12));
        noBtn.setBackground(Color.darkGray);
        noBtn.setForeground(Color.WHITE);
        noBtn.addActionListener(this);
        noBtn.setFocusPainted(false);
        noBtn.setFocusable(false);
        noBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.weightx = 0;
        gbc.gridwidth = 20;
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.ipadx = 50;
        gbc.anchor = GridBagConstraints.WEST;
        dialog.add(noBtn, gbc);
    }

    public void showConfirmationDialog(DialogType dialogType) {
        dialog.setVisible(true);
        if (dialogType == DialogType.REMOVE_CONFIRMATION_DIALOG) {
            noBtn.setBackground(new Color(173, 193, 124));
            yesBtn.setBackground(Color.darkGray);
            yesBtn.setForeground(Color.WHITE);
        } else if (dialogType == DialogType.WATER_CONFIRMATION_DIALOG) {
            noBtn.setBackground(Color.darkGray);
            noBtn.setForeground(Color.WHITE);
            yesBtn.setBackground(new Color(173, 193, 124));
        }
        type = dialogType;
    }

    public void setConfirmationMessage(String messageTxt) {
        message.setText(messageTxt);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == yesBtn) {
            if (type == DialogType.REMOVE_CONFIRMATION_DIALOG) {
                controller.buttonPushed("remove plant");
            } else if (type == DialogType.WATER_CONFIRMATION_DIALOG) {
                controller.buttonPushed("water plant");
            }
        } else if (e.getSource() == noBtn) {
            controller.buttonPushed("plantList");
        }
        dialog.setVisible(false);
    }
}