package view.panels.plant;

import controller.Controller;
import view.panels.MessageDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlantSelectionPanel extends JPanel implements ActionListener {

    private JLabel title;
    private JLabel image;
    private JButton waterBtn;
    private JButton editBtn;
    private Controller controller;
    private JButton deleteBtn;

    public PlantSelectionPanel(Controller controller) {
        this.controller = controller;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(360, 360));
        setBorder(BorderFactory.createEmptyBorder(140, 0, 150, 0));

        JPanel borderPanel = new JPanel(new BorderLayout());

        borderPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 50, 50));
        borderPanel.setBackground(Color.white);

        JPanel selectionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,3,6,3);
        selectionPanel.setBackground(Color.white);
        selectionPanel.setPreferredSize(new Dimension(270, 305));

        title = new JLabel("", JLabel.CENTER);
        title.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        title.setVisible(false);
        gbc.gridx = 2;
        gbc.gridy = 1;
        selectionPanel.add(title, gbc);

        image = new JLabel("", JLabel.CENTER);
        image.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        gbc.gridx = 2;
        gbc.gridy = 2;
        selectionPanel.add(image, gbc);
        JPanel buttons = new JPanel(new GridLayout(0, 2, 0, 0));
        buttons.setBackground(Color.white);
        buttons.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        waterBtn = new JButton("water");
        ImageIcon waterImg = new ImageIcon("./images/water-icon.png");
        Image scaledWaterImg = waterImg.getImage().getScaledInstance(30, 30,
                Image.SCALE_SMOOTH);

        waterBtn.setIcon(new ImageIcon(scaledWaterImg));

        waterBtn.setFont(new Font("Arial", Font.BOLD, 18));
        waterBtn.addActionListener(this);
        waterBtn.setBackground(new Color(220, 229, 185));
        waterBtn.setForeground(Color.darkGray);
        waterBtn.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(93, 118, 77)));
        waterBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        waterBtn.setVisible(false);
        buttons.add(waterBtn);

        editBtn = new JButton("edit");

        ImageIcon editImg = new ImageIcon("./images/edit-icon.png");
        Image scaledEditImg = editImg.getImage().getScaledInstance(30, 30,
                Image.SCALE_SMOOTH);
        editBtn.setIcon(new ImageIcon(scaledEditImg));
        editBtn.addActionListener(this);
        editBtn.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, new Color(93, 118, 77)));
        editBtn.setFont(new Font("Arial", Font.BOLD, 18));
        editBtn.setBackground(new Color(220, 229, 185));
        editBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        editBtn.setForeground(Color.darkGray);

        editBtn.setVisible(false);
        buttons.add(editBtn);
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.ipadx = 200;
        selectionPanel.add(buttons, gbc);

        deleteBtn = new JButton("remove");
        deleteBtn.setBackground(new Color(212, 79, 63, 255));
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 16));
        deleteBtn.setForeground(Color.white);
        deleteBtn.addActionListener(this);
        deleteBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteBtn.setBorder(null);
        deleteBtn.setVisible(false);
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.ipadx = 20;
        gbc.weighty = 1;
        gbc.ipady = 10;
        selectionPanel.add(deleteBtn, gbc);

        borderPanel.add(selectionPanel);

        add(borderPanel);
    }

    public void setPlantName(String plantName) {
        title.setText(plantName.toUpperCase());
        waterBtn.setVisible(true);
        editBtn.setVisible(true);
        title.setVisible(true);
        deleteBtn.setVisible(true);
    }

    public void setImageIcon(ImageIcon imageIcon) {
        Image img = imageIcon.getImage().getScaledInstance(150, 145,
                Image.SCALE_SMOOTH);

        image.setIcon(new ImageIcon(img));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editBtn) {
            controller.buttonPushed("show plant creation page");
        } else if (e.getSource() == waterBtn) {
            controller.buttonPushed("water plant");
        } else  if (e.getSource() == deleteBtn) {
            controller.buttonPushed("remove plant");
            //new MessageDialog(controller);
        }
    }
}