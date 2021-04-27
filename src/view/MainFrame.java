package view;

import controller.Controller;
import view.menus.MenuBar;
import view.menus.PopupMenu;
import view.panels.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame{
    private Controller controllerRef;
    private JFrame frame;
    private MainPanel mainPanel;
    private view.menus.PopupMenu popupMenu;

    public MainFrame( Controller controllerRef ) {
        this.controllerRef = controllerRef;
        setupFrame();
    }


    public void setupFrame() {
        frame = new JFrame("MyHappyPlants");

        mainPanel = new MainPanel(controllerRef);
        popupMenu = new PopupMenu();

        frame.add(mainPanel, BorderLayout.NORTH);

        frame.setJMenuBar(new MenuBar());
        frame.add(popupMenu);
        frame.setSize(1500, 1000);
        frame.setPreferredSize(new Dimension(1500, 1000));
        frame.setLocationRelativeTo(null);
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                maybeShowPopup(e);
            }
            public void mouseReleased(MouseEvent e){
                maybeShowPopup(e);
            }
            private void maybeShowPopup(MouseEvent e){
                if(e.isPopupTrigger()){
                    popupMenu.show(e.getComponent(),e.getX(),e.getY());
                }
            }
        });


        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setCardLayout(String cardLayout) {
        mainPanel.setCardLayout(cardLayout);
    }

    public PlantList getPlantList() {
        return mainPanel.getPlantList();
    }

    public void showLoginError(boolean show) {
        mainPanel.showLoginError(show);
    }

    public void createPlantList() {
        mainPanel.createPlantList();
    }

    public void setTitle(String title) {
        mainPanel.setTitle(title);
    }

    public void setImage(ImageIcon icon) {
        mainPanel.setImage(icon);
    }

    public void showSearch(boolean show) {
        mainPanel.showSearch(show);
    }

    public String getSearch() {
        return mainPanel.getSearch();
    }
}
