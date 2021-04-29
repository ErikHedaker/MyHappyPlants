package view.panels;

import controller.Controller;
import view.panels.plant.PlantList;
import view.panels.login.LoginPanel;
import view.panels.plant.PlantPagePanel;

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

    public MainPanel(Controller controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        createCenterPanel();
        northPanel = new NorthPanel(controller);
        southPanel = new SouthPanel();
        setCardLayout("signIn");
    }

    public void createCenterPanel() {
        cardLayout = new CardLayout();
        panelCenter = new JPanel(cardLayout);

        loginPanel = new LoginPanel(controller);
        panelCenter.add(loginPanel, "signIn");
        plantPagePanel = new PlantPagePanel(controller);
        panelCenter.add(plantPagePanel, "show plant page");
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        JLabel label = new JLabel("Loading...");
        label.setFont(new Font("Times New Roman", Font.BOLD, 30));
        panel.add(label);
        panelCenter.add(panel, "loading-screen");
        add(panelCenter);
    }


    public void createPlantList() {
        JPanel panelPlantList = new JPanel(new BorderLayout());
        plantList = new PlantList(controller.getPlantList(), panelPlantList);
        panelCenter.add(panelPlantList, "plantList");
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

    public void setImage(ImageIcon icon) {
        plantPagePanel.setImage(icon);
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

    public String getSearchInput() {
        return northPanel.getSearchField().getText();
    }
}
