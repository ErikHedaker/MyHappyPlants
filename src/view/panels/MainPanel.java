package view.panels;

import controller.Controller;
import view.panels.plant.*;
import view.panels.login.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    private Controller controller;
    private PlantList plantList;
    private LoginPanel loginPanel;
    private PlantSearchPanel plantSearchPanel;
    private CardLayout cardLayout;
    private JPanel panelCenter;
    private NorthPanel northPanel;
    private SouthPanel southPanel;
    private PlantSelectionPanel plantSelectionPanel;
    private PlantCreationPanel plantCreationPanel;

    public MainPanel(Controller controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        createCenterPanel();
        northPanel = new NorthPanel(controller);
        southPanel = new SouthPanel();
    }

    public void createCenterPanel() {
        cardLayout = new CardLayout();
        panelCenter = new JPanel(cardLayout);

        loginPanel = new LoginPanel(controller);
        panelCenter.add(loginPanel, "signIn");
        plantSearchPanel = new PlantSearchPanel(controller);
        panelCenter.add(plantSearchPanel, "plant page");
        plantCreationPanel = new PlantCreationPanel(controller);
        panelCenter.add(plantCreationPanel, "plant creation page");

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.white);
        ImageIcon loadingGif = new ImageIcon("./images/loading_screen.gif");
        JLabel label = new JLabel(loadingGif);
        label.setBorder(BorderFactory.createEmptyBorder(0,0,150,0));
        panel.add(label, BorderLayout.CENTER);
        panelCenter.add(panel, "loading-screen");
        panelCenter.add(new PlantInfoPanel(), "welcome info");
        add(panelCenter);

    }


    public void createPlantList() {
        JPanel panelPlantList = new JPanel(new BorderLayout());

        plantSelectionPanel = new PlantSelectionPanel(controller);
        panelPlantList.add(plantSelectionPanel, BorderLayout.EAST);

        plantList = new PlantList(controller.getPlantList(), panelPlantList, controller);
        panelCenter.add(panelPlantList, "plantList");
        panelPlantList.revalidate();
        panelPlantList.repaint();
    }

    public void setSelectedPlantName(String plantName) {
        plantSelectionPanel.setPlantName(plantName);
    }

    public void setSelectedImageIcon(ImageIcon imageIcon) {
        new Thread(() -> plantSelectionPanel.setImageIcon(imageIcon)).start();
    }

    public boolean isCreationMode() {
        return plantCreationPanel.isCreationMode();
    }

    public void setWaterTF(String text) {
        plantCreationPanel.setWaterTF(text);
    }

    public void setNicknameTF(String text) {
        plantCreationPanel.setNicknameTF(text);
    }

    public PlantList getPlantList() {
        return plantList;
    }

    public NorthPanel getNorthPanel() {
        return northPanel;
    }

    public SouthPanel getSouthPanel() {
        return southPanel;
    }

    public PlantSelectionPanel getPlantSelectionPanel() {
        return plantSelectionPanel;
    }

    public void setTitle(String title) {
        plantSearchPanel.setTitle(title);
    }

    public void showLoginError(boolean show) {
        loginPanel.showLoginError(show);
    }

    public void showSearchBtn(boolean show) {
        northPanel.getSearchBtn().setVisible(show);
    }

    public void setDescription(String txt) {
        plantSearchPanel.setDescription(txt);
    }

    public void showButton(boolean show) {
        plantSearchPanel.showButton(show);
    }

    public void showSearch() {
        northPanel.showSearch();
    }

    public void setCardLayout(String constraint) {
        cardLayout.show(panelCenter, constraint);
        repaint();
    }

    public void setImageLabel(ImageIcon imageIcon) {
        plantSearchPanel.setImageLabel(imageIcon);
    }

    public void setCreationMode(boolean creationMode) {
        plantCreationPanel.setCreationMode(creationMode);
    }

    public void updatePlantWateringComponents(int index) {
        plantList.getPlantPanels().get(index).updateWateringComponents();
    }

    /*public String getSearchInput() {
        return northPanel.getSearchField().getText();
    }*/

    public String getSearchInput(){
        return northPanel.getSearchField();
    }
}
