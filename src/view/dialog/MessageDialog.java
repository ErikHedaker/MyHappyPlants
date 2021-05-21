package view.dialog;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MessageDialog {

    private JDialog dialog;

    public MessageDialog(String message) {
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

        JTextPane messageLabel = new JTextPane();
        messageLabel.setEditable(false);
        messageLabel.setText(message);
        StyledDocument doc = messageLabel.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        messageLabel.setFont(new Font("Calibri", Font.PLAIN, 17));
        messageLabel.setVisible(true);
        messageLabel.setBackground(Color.white);
        gbc.gridy = 0;
        gbc.gridwidth = 200;
        gbc.anchor = GridBagConstraints.PAGE_START;
        dialog.add(messageLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        dialog.add(new JLabel(), gbc);

        JButton confirm = new JButton("OK");
        confirm.setBackground(new Color(173, 193, 124));
        confirm.setForeground(Color.DARK_GRAY);
        confirm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.ipadx = 50;
        gbc.weightx = 1;
        dialog.add(confirm, gbc);

        gbc.fill = GridBagConstraints.WEST;
        dialog.setVisible(true);
    }
}
