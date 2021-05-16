package view.panels.plant;

import controller.Controller;
import controller.Utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class PlantSearchPanel extends JPanel {

    private JPanel plantPanel;
    private Controller controller;
    private JLabel title;
    private JTextPane description;
    private JButton addPlantBtn;
    private JLabel imageLabel;

    public  PlantSearchPanel(Controller controller) {
        this.controller = controller;

        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(0, 340, 0, 340));
        setLayout(new BorderLayout());
        plantPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 6, 3);

        Border border = BorderFactory.createLineBorder(Color.lightGray, 1, true);
        plantPanel.setBorder(border);
        Border margin1 = new EmptyBorder(50,50,50,50);
        plantPanel.setBorder(new CompoundBorder(plantPanel.getBorder(), margin1));
        plantPanel.setPreferredSize(new Dimension(600,400));
        plantPanel.setBackground(Color.white);

        //JPanel header = new JPanel(new BorderLayout());
        //header.setBackground(Color.white);
        title = new JLabel("", SwingConstants.CENTER);
        title.setFont(new Font("Calibri Light", Font.BOLD, 27));
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        //header.add(title, BorderLayout.CENTER);

        plantPanel.add(title, gbc);

        imageLabel = new JLabel();
        setImageLabel(new ImageIcon("./images/plant.jpg"));
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        plantPanel.add(imageLabel, gbc);

        description = new JTextPane();
        description.setEditable(false);
        StyledDocument doc = description.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        description.setFont(new Font("Calibri Light", Font.PLAIN, 17));
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.ipadx = 250;
        gbc.anchor = GridBagConstraints.CENTER;

        plantPanel.add(description, gbc);

        addPlantBtn = new JButton("ADD PLANT");
        addPlantBtn.setBackground(new Color(220, 229, 185));
        addPlantBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addPlantBtn.setFont(new Font("Arial", Font.BOLD, 10));
        addPlantBtn.addActionListener(new Action());
        gbc.gridx = 3;
        gbc.gridy = 3;

        gbc.ipadx = 50;
        gbc.ipady = 15;
        gbc.anchor = GridBagConstraints.CENTER;
        plantPanel.add(addPlantBtn, gbc);

        add(plantPanel);
    }

    class Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().equals(addPlantBtn)) {
                controller.setPlantCreationMode(true);
                controller.buttonPushed("show plant creation page");
            }
        }
    }

    public void showButton(boolean show) {
        addPlantBtn.setVisible(show);
    }

    public void setDescription(String txt) {
        description.setText(txt);
    }

    public void setImageLabel(ImageIcon imageIcon) {
        if (imageIcon == null) {
            imageIcon = new ImageIcon("images/plant.jpg");
        }

        Image scaledImage = imageIcon.getImage().getScaledInstance(105,100, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        if (addPlantBtn != null) {
            getRootPane().setDefaultButton(addPlantBtn);
        }
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
}
