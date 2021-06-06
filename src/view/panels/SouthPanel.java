package view.panels;

import javax.swing.*;
import java.awt.*;

/**
 * This class is made for the purpose of visualizing the GUI on the lower part of the window.
 * @Author Viktor Johansson
 */

public class SouthPanel extends JPanel {

    public SouthPanel() {
        setLayout(new BorderLayout());
        JPanel panelSouth = new JPanel(new BorderLayout());

        panelSouth.setBackground(new Color(220, 229, 185));
        panelSouth.setPreferredSize(new Dimension(1500, 50));

        JLabel label = new JLabel("MyHappyPlants - Team 24 (Sys) \u00A9");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panelSouth.add(label);
        panelSouth.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        add(panelSouth);
    }
}
