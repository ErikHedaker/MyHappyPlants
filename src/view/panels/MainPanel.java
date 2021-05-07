package view.panels;

import controller.Controller;
import view.panels.plant.PlantCreationPanel;
import view.panels.plant.PlantList;
import view.panels.login.LoginPanel;
import view.panels.plant.PlantPagePanel;
import view.panels.plant.PlantSelectionPanel;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private Controller controller;
    private PlantList plantList;
    private LoginPanel loginPanel;
    private PlantPagePanel plantPagePanel;
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
        plantPagePanel = new PlantPagePanel(controller);
        panelCenter.add(plantPagePanel, "plant page");
        plantCreationPanel = new PlantCreationPanel(controller);
        panelCenter.add(plantCreationPanel, "plant creation page");

        setOpaque(false);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.white);
        JLabel label = new JLabel("Loading...", JLabel.CENTER);
        label.setFont(new Font("Times New Roman", Font.BOLD, 30));
        panel.add(label, BorderLayout.CENTER);
        panelCenter.add(panel, "loading-screen");
        add(panelCenter);
    }


    public void createPlantList() {
        JPanel panelPlantList = new JPanel(new BorderLayout());

        plantSelectionPanel = new PlantSelectionPanel(controller);
        panelPlantList.add(plantSelectionPanel, BorderLayout.EAST);

        plantList = new PlantList(controller.getPlantList(), panelPlantList, controller);
        panelCenter.add(panelPlantList, "plantList");
    }

    public void setSelectedPlantName(String plantName) {
        plantSelectionPanel.setPlantName(plantName);
    }

    public void setSelectedImageIcon(ImageIcon imageIcon) {
        plantSelectionPanel.setImageIcon(imageIcon);
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
        plantPagePanel.setTitle(title);
    }

    public void showLoginError(boolean show) {
        loginPanel.showLoginError(show);
    }

    public void showSearchBtn(boolean show) {
        northPanel.getSearchBtn().setVisible(show);
    }

    public void setDescription(String txt) {
        plantPagePanel.setDescription(txt);
    }

    public void showButton(boolean show) {
        plantPagePanel.showButton(show);
    }

    public void setCardLayout(String constraint) {
        cardLayout.show(panelCenter, constraint);
        repaint();
    }

    public void setCreationMode(boolean creationMode) {
        plantCreationPanel.setCreationMode(creationMode);
    }

    public String getSearchInput() {
        return northPanel.getSearchField().getText();
    }
}
