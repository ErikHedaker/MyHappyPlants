package view;

import controller.Controller;
import view.panels.MainPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private Controller controllerRef;
    private MainPanel panel;
    public MainFrame( Controller controllerRef ) {
        this.controllerRef = controllerRef;
        setupFrame();
    }

    public void setupFrame() {
        JFrame frame = new JFrame("MyHappyPlants");

        panel = new MainPanel(controllerRef);

        frame.add(panel, BorderLayout.NORTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setCardLayout(String cardLayout) {
        panel.setCardLayout(cardLayout);
    }

    public PlantList getPlantList() {
        return panel.getPlantList();
    }

}
