package view.menus;

import controller.Controller;
import view.settingsFrame.SettingsFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MenuBar extends JMenuBar {

    private JMenu fileMenu, editMenu, helpMenu, subMenu, profileMenu, rankMenu, yourProfileMenu;
    private JMenuItem loadItem, settingsItem, exitItem, aboutItem, manualItem, profileItem, signOutItem;
    private JRadioButtonMenuItem themeOne, themeTwo;

    private Controller controller;


    public MenuBar(Controller controller){
        this.controller = controller;
        createMenuBar();
    }

    public void createMenuBar(){
        fileMenu = new JMenu(" File ");
        editMenu = new JMenu(" Edit ");
        helpMenu = new JMenu(" Help ");
        rankMenu = new JMenu(" Rank ");
        yourProfileMenu = new JMenu(" Profile ");

        //File Menu
        loadItem = new JMenuItem("Open file");
        settingsItem = new JMenuItem("Settings");
        exitItem = new JMenuItem("Exit");
        fileMenu.add(loadItem);
        fileMenu.add(settingsItem);
        fileMenu.add(exitItem);

        //Help Menu
        manualItem = new JMenuItem("Help ");
        aboutItem = new JMenuItem("About");
        helpMenu.add(manualItem);
        helpMenu.add(aboutItem);

        //Edit Menu
        themeOne = new JRadioButtonMenuItem("Dark");
        themeTwo = new JRadioButtonMenuItem("Light");
        //menu.addSeparator();
        subMenu = new JMenu("Theme");
        subMenu.add(themeOne);
        subMenu.add(themeTwo);
        editMenu.add(subMenu);

        //Profile Menu
        profileItem = new JMenuItem("Your Profile");
        yourProfileMenu.add(profileItem);



        Listener listener = new Listener();
        loadItem.addActionListener(listener);
        settingsItem.addActionListener(listener);
        helpMenu.addActionListener(listener);
        aboutItem.addActionListener(listener);
        manualItem.addActionListener(listener);
        profileItem.addActionListener(listener);
        rankMenu.addActionListener(listener);

        /*fileMenu.setFont(new Font("Arial", Font.PLAIN, 16));
        editMenu.setFont(new Font("Arial", Font.PLAIN, 16));
        helpMenu.setFont(new Font("Arial", Font.PLAIN, 16));*/

        //fileMenu.setBorder(BorderFactory.createMatteBorder(0,0,0,1, Color.BLACK));
        fileMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //editMenu.setBorder(BorderFactory.createMatteBorder(0,0,0,1, Color.BLACK));
        editMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //helpMenu.setBorder(BorderFactory.createMatteBorder(0,0,0,1, Color.BLACK));
        helpMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rankMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        yourProfileMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        /*add(fileMenu);
        add(editMenu);
        add(helpMenu);*/

        add(Box.createHorizontalGlue());

        profileMenu = new JMenu(" Not signed in     ");
        profileMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profileMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0,
                new Color(173,193,124)));
        profileMenu.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        Image img = new ImageIcon("./images/defpic.jpg").getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH);

        signOutItem = new JMenuItem("Sign Out");
        signOutItem.addActionListener(new Listener());

        profileMenu.setIcon(new ImageIcon(img));
        //profileMenu.add(editMenu);
        //profileMenu.add(fileMenu);
        profileMenu.add(profileItem);
        profileMenu.add(manualItem);
        profileMenu.add(signOutItem);

        add(profileMenu);


        setOpaque(true);
        setBackground(new Color(220,229,185));
        setBorder(BorderFactory.createLineBorder(new Color(220,229,185)));
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
            if(e.getSource() == signOutItem){
                profileMenu.setText(" Not signed in     ");
                controller.buttonPushed("signIn");
                controller.activeUserDefault();
            }
            if(e.getSource() == profileItem) {
                controller.buttonPushed("profile page");
            }

            }
    }
}
