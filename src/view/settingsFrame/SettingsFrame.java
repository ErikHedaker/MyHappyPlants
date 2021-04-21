package view.settingsFrame;

import view.panels.MainPanel;

import javax.swing.*;

public class SettingsFrame extends JFrame {
    private int width = 1200;
    private int height = 600;

    SettingsMainPanel panel;

    public SettingsFrame(){
        final int offsetX = width/5;
        final int offsetY = height/5;

        setSize(width, height);
        setTitle("Inställningar");
        setLocation(offsetX, offsetY);

        panel = new SettingsMainPanel(width, height);
        setContentPane(panel);
        setResizable(false);
        pack();

        setVisible(true);
    }
}
