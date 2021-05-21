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
        gbc.insets = new Insets(0,0,5,0);

        message = new JLabel ("");
        message.setFont(new Font("Calibri", Font.PLAIN, 17));
        message.setVisible(true);
        message.setBackground(Color.white);
        gbc.gridy = 0;
        gbc.gridwidth = 200;
        gbc.anchor = GridBagConstraints.PAGE_START;
        dialog.add(message, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        dialog.add(new JLabel(), gbc);

        gbc.fill = GridBagConstraints.WEST;

        JPanel buttons = new JPanel(new BorderLayout());
        buttons.setPreferredSize(new Dimension(150, 30));
        buttons.setBackground(Color.WHITE);
        yesBtn = new JButton("YES");
        yesBtn.setVisible(true);
        yesBtn.setFont(new Font("Arial", Font.BOLD, 12));
        yesBtn.setBackground(Color.darkGray);
        yesBtn.setForeground(Color.WHITE);
        yesBtn.addActionListener(this);
        yesBtn.setFocusPainted(false);
        yesBtn.setFocusable(false);
        yesBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buttons.add(yesBtn, BorderLayout.WEST);


        noBtn = new JButton("NO");
        noBtn.setVisible(true);

        noBtn.setFont(new Font("Arial", Font.BOLD, 12));
        noBtn.setBackground(new Color(173, 193, 124));
        noBtn.setForeground(Color.DARK_GRAY);
        noBtn.addActionListener(this);
        noBtn.setFocusPainted(false);
        noBtn.setFocusable(false);
        noBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        gbc.anchor = GridBagConstraints.WEST;
        buttons.add(noBtn, BorderLayout.EAST);
        gbc.insets = new Insets(15,125,5,0);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.ipadx = 50;
        gbc.weightx = 1;
        dialog.add(buttons, gbc);

    }

    public ConfirmationDialog showConfirmationDialog(DialogType dialogType) {
        dialog.setVisible(true);
        if (dialogType == DialogType.REMOVE_CONFIRMATION_DIALOG) {
            noBtn.setText("KEEP");
            yesBtn.setText("REMOVE");
        } else if (dialogType == DialogType.WATER_CONFIRMATION_DIALOG) {
            noBtn.setText("CANCEL");
            yesBtn.setText("CONFIRM");
        } else {
            noBtn.setText("STAY");
            yesBtn.setText("CONFIRM");
        }
        noBtn.getRootPane().setDefaultButton(noBtn);
        try {
            Robot robot = new Robot();
            robot.mouseMove(1025, 510);
        } catch (AWTException e) {
        }
        type = dialogType;
        return this;
    }

    public ConfirmationDialog setConfirmationMessage(String messageTxt) {
        message.setText(messageTxt);
        return this;
    }

    public void actionPerformed(ActionEvent e) {
        if (type == DialogType.REMOVE_CONFIRMATION_DIALOG) {
            if (e.getSource() == yesBtn) {
                controller.buttonPushed("remove plant");
            } else if (e.getSource() == noBtn) {
                controller.buttonPushed("plantList");
            }
        } else if (type == DialogType.WATER_CONFIRMATION_DIALOG) {
            if (e.getSource() == yesBtn) {
                controller.buttonPushed("water plant");
            } else if (e.getSource() == noBtn) {
                controller.buttonPushed("plantList");
            }
        } else if (type == DialogType.PROCEED_BACK_CONFIRMATION_DIALOG) {
            if (e.getSource() == yesBtn) {
                controller.buttonPushed("plantList");
            } else if (e.getSource() == noBtn) {
                controller.buttonPushed("show plant creation page");
            }
        }
        dialog.setVisible(false);
    }
}