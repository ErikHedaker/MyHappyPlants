package view.menus;

import view.settingsFrame.SettingsFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MenuBar extends JMenuBar {

    JMenu fileMenu, editMenu, helpMenu, subMenu, profileMenu;
    JMenuItem loadItem, settingsItem, exitItem, aboutItem, manualItem;
    JRadioButtonMenuItem themeOne, themeTwo;

    public MenuBar(){
        fileMenu = new JMenu(" Fil ");
        editMenu = new JMenu(" Ändra ");
        helpMenu = new JMenu(" Hjälp ");

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

        fileMenu.setFont(new Font("Arial", Font.PLAIN, 16));
        editMenu.setFont(new Font("Arial", Font.PLAIN, 16));
        helpMenu.setFont(new Font("Arial", Font.PLAIN, 16));

        fileMenu.setBorder(BorderFactory.createMatteBorder(0,0,0,1, Color.BLACK));
        fileMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        editMenu.setBorder(BorderFactory.createMatteBorder(0,0,0,1, Color.BLACK));
        editMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        helpMenu.setBorder(BorderFactory.createMatteBorder(0,0,0,1, Color.BLACK));
        helpMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(fileMenu);
        add(editMenu);
        add(helpMenu);

        add(Box.createHorizontalGlue());

        profileMenu = new JMenu(" Not signed in ");
        profileMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profileMenu.setBorder(BorderFactory.createMatteBorder(0,1,0,1, Color.BLACK));
        profileMenu.setFont(new Font("Arial", Font.PLAIN, 16));
        add(profileMenu);

        setOpaque(true);
        setBackground(Color.WHITE);
    }

    public void setProfile(String name) {
        profileMenu.setText(name);
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
