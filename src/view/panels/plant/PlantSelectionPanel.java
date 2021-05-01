package view.panels.plant;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class PlantSelectionPanel extends JPanel {

    private String plantName = "Aloe";
    private ImageIcon imageIcon;

    public PlantSelectionPanel(Controller controller) {
        imageIcon = new ImageIcon(controller.getImageDefault());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400,200));
        setBorder(BorderFactory.createEmptyBorder(200, 0, 200, 0));

        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.setBackground(Color.white);
        selectionPanel.setPreferredSize(new Dimension(200,200));

        JLabel title = new JLabel(plantName, JLabel.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 25));

        selectionPanel.add(title, BorderLayout.NORTH);

        JLabel image = new JLabel();
        image.setIcon(imageIcon);

        JPanel buttons = new JPanel(new GridLayout(0,2,0,0));

        JButton waterBtn = new JButton("Water");
        buttons.add(waterBtn);

        JButton editBtn = new JButton("Edit");
        buttons.add(editBtn);

        selectionPanel.add(buttons, BorderLayout.SOUTH);
        add(selectionPanel);
    }


}
