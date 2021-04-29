package view;

import controller.Controller;
import view.menus.MenuBar;
import view.menus.PopupMenu;
import view.panels.MainPanel;
import view.panels.plant.PlantList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame{
    private Controller controllerRef;
    private MainPanel panel;
    private view.menus.PopupMenu popupMenu;

    public MainFrame( Controller controllerRef ) {
        this.controllerRef = controllerRef;
        setupFrame();
    }

    public void setupFrame() {
        JFrame frame = new JFrame("MyHappyPlants");

        panel = new MainPanel(controllerRef);
        popupMenu = new PopupMenu();
        frame.setLayout(new BorderLayout());
        frame.setJMenuBar(new MenuBar());
        frame.add(popupMenu);
        //frame.setSize(1500, 820);
        frame.add(panel.getNorthPanel(), BorderLayout.NORTH);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeWidth = (dim.width +800)/ 2;
        int sizeHeight = (dim.height + 600)/ 2;
        panel.setPreferredSize(new Dimension((sizeWidth + 300)/2, (sizeWidth + 300)/2));
        frame.add(panel, BorderLayout.CENTER);

        frame.setSize(sizeWidth, sizeHeight);
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
        frame.add(panel.getSouthPanel(), BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setCardLayout(String cardLayout) {
        panel.setCardLayout(cardLayout);
    }

    public PlantList getPlantList() {
        return panel.getPlantList();
    }

    public void showLoginError(boolean show) {
        panel.showLoginError(show);
    }

    public void createPlantList() {
        panel.createPlantList();
    }

    public void setTitle(String title) {
        panel.setTitle(title);
    }

    public void showButton(boolean show) {
        panel.showButton(show);
    }

    public void setDescription(String txt) {
        panel.setDescription(txt);
    }

    public void setImage(ImageIcon icon) {
        panel.setImage(icon);
    }

    public void showSearch(boolean show) {
        panel.showSearchBtn(show);
    }

    public String getSearchInput() {
        return panel.getSearchInput();
    }
}
