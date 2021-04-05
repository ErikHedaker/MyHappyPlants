package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, helpMenu;
    JMenuItem loadItem, settingsItem, exitItem, aboutItem, manualItem;

    public MenuBar(){
        // ---------MenuBar-----------

        menuBar = this;
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
}
