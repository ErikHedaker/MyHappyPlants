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

    public  PlantSearchPanel(Controller controller) {
        this.controller = controller;

        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(50, 340, 50, 340));

        plantPanel = new JPanel(new BorderLayout());

        Border border = BorderFactory.createLineBorder(Color.lightGray, 1, true);
        plantPanel.setBorder(border);

        Border margin1 = new EmptyBorder(50,50,50,50);
        plantPanel.setBorder(new CompoundBorder(plantPanel.getBorder(), margin1));
        plantPanel.setPreferredSize(new Dimension(600,400));
        plantPanel.setBackground(Color.white);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.white);
        title = new JLabel("", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 27));
        header.add(title, BorderLayout.CENTER);

        plantPanel.add(header, BorderLayout.NORTH);

        description = new JTextPane();
        description.setEditable(false);
        StyledDocument doc = description.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        description.setPreferredSize(new Dimension(200,200));
        description.setFont(new Font("Calibri", Font.BOLD, 15));
        description.setBorder(BorderFactory.createEmptyBorder(0,0,200,0));
        plantPanel.add(description, BorderLayout.CENTER);

        addPlantBtn = new JButton("ADD PLANT");
        addPlantBtn.addActionListener(new Action());
        plantPanel.add(addPlantBtn, BorderLayout.SOUTH);

        add(plantPanel);
    }

    class Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().equals(addPlantBtn)) {
                controller.buttonPushed("add plant");
                controller.buttonPushed("show plant creation page");
            }
        }
    }

    public void showButton(boolean show) {
        addPlantBtn.setVisible(show);
    }

    public void setDescription(String txt) {
        description.setText(Utility.splitParagraph(txt, 10));
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
}
