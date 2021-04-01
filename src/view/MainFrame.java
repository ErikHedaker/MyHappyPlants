package view;

import view.panels.MainPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame {

    public MainFrame() {
        setupFrame();
    }

    public void setupFrame() {
        JFrame frame = new JFrame("My Happy Plants");


        MainPanel mainPanel = new MainPanel();
        frame.add(mainPanel, BorderLayout.NORTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
