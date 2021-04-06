package view.menus;

import view.SettingsFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MenuBar extends JMenuBar {
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, helpMenu, subMenu;
    JMenuItem loadItem, settingsItem, exitItem, aboutItem, manualItem;
    JRadioButtonMenuItem themeOne, themeTwo;

    public MenuBar(){
        menuBar = this;
        fileMenu = new JMenu("Fil");
        editMenu = new JMenu("Ändra");
        helpMenu = new JMenu("Hjälp");

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

        //Edit Menu
        themeOne = new JRadioButtonMenuItem("Mörk");
        themeTwo = new JRadioButtonMenuItem("Ljus");
        //menu.addSeparator();
        subMenu = new JMenu("Tema");
        subMenu.add(themeOne);
        subMenu.add(themeTwo);
        editMenu.add(subMenu);


        Listener listener = new Listener();
        loadItem.addActionListener(listener);
        settingsItem.addActionListener(listener);
        helpMenu.addActionListener(listener);
        aboutItem.addActionListener(listener);
        manualItem.addActionListener(listener);


        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
    }

    class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == loadItem){
                System.out.println("laddat en fil");
            }
            if(e.getSource() == settingsItem){
                System.out.println("Inställningar");
                SettingsFrame sf = new SettingsFrame();
            }
            if(e.getSource() == exitItem){
                System.exit(0);
            }
            if(e.getSource() == aboutItem){
                JOptionPane.showMessageDialog(null,"Version 1.0 - Skapad av Grupp 24","Om",JOptionPane.INFORMATION_MESSAGE);
            }
            if(e.getSource() == manualItem){
                if(Desktop.isDesktopSupported()){
                    try{
                        File myFile = new File("files/Manual.pdf");
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex){

                    }
                }
            }

        }
    }
}
