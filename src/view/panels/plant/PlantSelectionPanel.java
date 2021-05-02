package view.panels.plant;

import controller.Controller;

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

    public PlantSelectionPanel(Controller controller) {
        this.controller = controller;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(340,340));
        setBorder(BorderFactory.createEmptyBorder(150, 0, 150, 0));

        JPanel borderPanel = new JPanel(new BorderLayout());

        borderPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        borderPanel.setBackground(Color.white);

        JPanel selectionPanel = new JPanel(new BorderLayout());

        selectionPanel.setBackground(Color.white);
        selectionPanel.setPreferredSize(new Dimension(270,270));

        title = new JLabel("", JLabel.CENTER);
        title.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        title.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));

        selectionPanel.add(title, BorderLayout.NORTH);

        image = new JLabel("", JLabel.CENTER);

        selectionPanel.add(image, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new GridLayout(0,2,0,0));
        buttons.setBackground(Color.white);
        buttons.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));

        waterBtn = new JButton("water");
        ImageIcon waterImg = new ImageIcon("./images/water-icon.png");
        Image scaledWaterImg = waterImg.getImage().getScaledInstance(30, 30,
                Image.SCALE_SMOOTH);

        waterBtn.setIcon(new ImageIcon(scaledWaterImg));

        waterBtn.setFont(new Font("Arial", Font.BOLD, 18));
        waterBtn.addActionListener(this);
        waterBtn.setBackground(new Color(220, 229, 185));
        waterBtn.setForeground(Color.darkGray);
        waterBtn.setVisible(false);
        buttons.add(waterBtn);

        editBtn = new JButton("edit");

        ImageIcon editImg = new ImageIcon("./images/edit-icon.png");
        Image scaledEditImg = editImg.getImage().getScaledInstance(30, 30,
                Image.SCALE_SMOOTH);
        editBtn.setIcon(new ImageIcon(scaledEditImg));
        editBtn.addActionListener(this);
        editBtn.setFont(new Font("Arial", Font.BOLD, 18));
        editBtn.setBackground(new Color(220, 229, 185));
        editBtn.setForeground(Color.darkGray);

        editBtn.setVisible(false);
        buttons.add(editBtn);

        selectionPanel.add(buttons, BorderLayout.SOUTH);

        borderPanel.add(selectionPanel);


        add(borderPanel);
    }

    public void setPlantName(String plantName) {
        title.setText(plantName.toUpperCase());
        waterBtn.setVisible(true);
        editBtn.setVisible(true);
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
        }
    }
}
