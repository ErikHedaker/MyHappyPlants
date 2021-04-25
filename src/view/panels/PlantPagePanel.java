package view.panels;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PlantPagePanel extends JPanel {

    private JPanel plantPanel;
    private Controller controller;
    private JLabel title;
    private JLabel image;

    public  PlantPagePanel(Controller controller) {
        this.controller = controller;

        setBackground(new Color(245, 245, 245));
        setPreferredSize(new Dimension(1100, 720));
        setBorder(BorderFactory.createEmptyBorder(50, 340, 50, 340));

        plantPanel = new JPanel(new BorderLayout());

        Border border = BorderFactory.createLineBorder(Color.lightGray, 1, true);
        plantPanel.setBorder(border);

        Border margin1 = new EmptyBorder(50,50,50,50);
        plantPanel.setBorder(new CompoundBorder(plantPanel.getBorder(), margin1));
        plantPanel.setPreferredSize(new Dimension(600,600));
        plantPanel.setBackground(Color.white);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.white);
        title = new JLabel("", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 27));
        header.add(title, BorderLayout.CENTER);

        plantPanel.add(header, BorderLayout.NORTH);

        JLabel description = new JLabel("Description: ");
        description.setFont(new Font("Calibri", Font.BOLD, 23));
        description.setBorder(BorderFactory.createEmptyBorder(0,0,200,0));
        plantPanel.add(description, BorderLayout.CENTER);

        add(plantPanel);
    }

    public void setImage(ImageIcon icon) {
        /*Image scaledSearchInstance = icon.getImage().getScaledInstance(47, 45, Image.SCALE_SMOOTH);
        this.image.setIcon(new ImageIcon(scaledSearchInstance));*/

    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
}
