package view;

import controller.Controller;
import view.panels.MainPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    Controller controllerRef;
    public MainFrame( Controller controllerRef ) {
        this.controllerRef = controllerRef;
        setupFrame();
    }

    public void setupFrame() {
        JFrame frame = new JFrame("My Happy Plants");


        MainPanel mainPanel = new MainPanel(controllerRef);
        frame.add(mainPanel, BorderLayout.NORTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
