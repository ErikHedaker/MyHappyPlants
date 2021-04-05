package view;

import controller.Controller;
import view.panels.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame implements ActionListener {
    private Controller controllerRef;
    private MainPanel panel;
    public MainFrame( Controller controllerRef ) {
        this.controllerRef = controllerRef;
        setupFrame();
    }
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, helpMenu;
    JMenuItem loadItem, settingsItem, exitItem, aboutItem, manualItem;

    public void setupFrame() {
        JFrame frame = new JFrame("MyHappyPlants");

        panel = new MainPanel(controllerRef);

        frame.add(panel, BorderLayout.NORTH);
        frame.pack();
        frame.setLocationRelativeTo(null);

        // ---------MenuBar-----------

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        helpMenu = new JMenu("Help");

        //File Menu
        loadItem = new JMenuItem("Öppna fil");
        settingsItem = new JMenuItem("Inställningar");
        exitItem = new JMenuItem("Stäng");
        fileMenu.add(loadItem);
        fileMenu.add(settingsItem);
        fileMenu.add(exitItem);

        //Help Menu
        manualItem = new JMenuItem("Manual");
        aboutItem = new JMenuItem("Om");

        helpMenu.add(manualItem);
        helpMenu.add(aboutItem);


        loadItem.addActionListener(this);
        settingsItem.addActionListener(this);
        helpMenu.addActionListener(this);
        aboutItem.addActionListener(this);
        manualItem.addActionListener(this);


        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setCardLayout(String cardLayout) {
        panel.setCardLayout(cardLayout);
    }

    public PlantList getPlantList() {
        return panel.getPlantList();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == loadItem){
            System.out.println("laddat en fil");
        }
        if(e.getSource() == settingsItem){
            System.out.println("sparat en fil");
        }
        if(e.getSource() == exitItem){
            System.exit(0);
        }
        if(e.getSource() == aboutItem){
            System.out.println("GRUPP 24 IN DA MUTHAFU**ING BUILDIIIIIING!!!!!!!!!");
        }
        if(e.getSource() == manualItem){
            System.out.println("Öppnar manual :)");
        }

    }
}
