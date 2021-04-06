package view.menus;

import view.MainFrame;

import javax.swing.*;
import java.awt.event.*;

public class PopupMenu extends JPopupMenu {
    JPopupMenu puMenu;
    JMenuItem settings, profil, något;
    MainFrame frame;

    public PopupMenu(){
        puMenu = this;
        settings = new JMenuItem("Inställningar");
        profil = new JMenuItem("Gå till profil");

        puMenu.add(profil);
        puMenu.add(settings);


        Listener listener = new Listener();
        settings.addActionListener(listener);
        profil.addActionListener(listener);


    }

    class Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == settings){
                System.out.println("HEJ");
            }
        }
    }

}
