package view;

import controller.Controller;
import view.menus.MenuBar;
import view.menus.PopupMenu;
import view.panels.MainPanel;
import view.panels.plant.PlantList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * This class is made for the purpose of visualizing the GUI and connecting to controller.
 * @Author Viktor Johansson
 */

public class MainFrame{
    private Controller controllerRef;
    private MainPanel panel;
    private view.menus.PopupMenu popupMenu;
    private JFrame frame;
    private MenuBar menuBar;

    public MainFrame( Controller controllerRef ) {
        this.controllerRef = controllerRef;
        setupFrame();
    }

    public void setupFrame() {
        frame = new JFrame("MyHappyPlants");

        panel = new MainPanel(controllerRef);
        popupMenu = new PopupMenu();

        frame.setIconImage(new ImageIcon("images/logo-happy.png").getImage());

        menuBar = new MenuBar(controllerRef);
        frame.setJMenuBar(menuBar);

        frame.add(popupMenu);
        frame.setLayout(new BorderLayout());

        JLabel logoLabel = new JLabel();
        ImageIcon logo = new ImageIcon("./images/logo-happy.png");
        Image scaledInstance = logo.getImage().getScaledInstance(165, 165, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaledInstance));
        logoLabel.setOpaque(false);

        frame.add(panel.getNorthPanel(), BorderLayout.NORTH);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeWidth = (dim.width +1080)/ 2;
        int sizeHeight = (dim.height + 750)/ 2;
        panel.setPreferredSize(new Dimension((sizeWidth + 300)/2, (sizeWidth + 300)/2));
        frame.getContentPane().add(panel, BorderLayout.CENTER);

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

    public void resetInputFields() {
        panel.resetInputFields();
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

    /*public void createUserProfile(){
        panel.createUserProfile();
    }*/

    public void setImageLabel(ImageIcon imageIcon) {
        panel.setImageLabel(imageIcon);
    }

    public void setSelectedPlantName(String plantName) {
        panel.setSelectedPlantName(plantName);
    }

    public void setImage(ImageIcon icon){
        menuBar.setImage(icon);
    }

    public void showConnectivityError() {
        JOptionPane.showMessageDialog(null, "API Down. Please try again later.");
    }

    public void setCreationMode(boolean creationMode) {
        panel.setCreationMode(creationMode);
    }

    public void updatePlantWateringComponents(int index) {
        panel.updatePlantWateringComponents(index);
    }

    public void showSearchField() {
        panel.showSearch();
    }

    public void setSelectedImageIcon(ImageIcon imageIcon) {
        panel.setSelectedImageIcon(imageIcon);
    }

    public void setWaterTF(String text) {
        panel.setWaterTF(text);
    }

    public void setProfile(String name) {
        menuBar.setProfile(name);
    }

    public void setNicknameTF(String text) {
        panel.setNicknameTF(text);
    }

    public void setUserNameTF(String text){
        panel.setUserNameTF(text);
    }

    public boolean isCreationMode() {
        return panel.isCreationMode();
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

    public void showSearch(boolean show) {
        panel.showSearchBtn(show);
    }

    public String getSearchInput() {
        return panel.getSearchInput();
    }

}
