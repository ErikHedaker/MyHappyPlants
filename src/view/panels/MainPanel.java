package view.panels;

import controller.Controller;
import view.PlantList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    private Controller controllerRef;
    private CardLayout cardLayout;
    private JPanel panelCenter;
    private PlantList plantList;
    private LoginPanel loginPanel;
    private JButton btn;

    public MainPanel(Controller controllerRef) {
        this.controllerRef = controllerRef;
        setLayout(new BorderLayout());
        createNorthPanel();
        createCenterPanel();
        createSouthPanel();
        setCardLayout("signIn");
    }

    public void createNorthPanel() {
        JPanel panelNorth = new JPanel(new BorderLayout());
        JPanel northHeader = new JPanel(new BorderLayout());
        northHeader.setPreferredSize(new Dimension(1500, 115));
        northHeader.setBackground(new Color(220, 229, 185));
        JLabel logoLabel = new JLabel();
        ImageIcon logo = new ImageIcon("./images/logo-happy.png");
        Image scaledInstance = logo.getImage().getScaledInstance(165, 165, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaledInstance));

        Border border = logoLabel.getBorder();
        Border margin = new EmptyBorder(0,85,0,0);
        logoLabel.setBorder(new CompoundBorder(border, margin));

        northHeader.add(logoLabel, BorderLayout.NORTH);

        JPanel southHeader = new JPanel(new BorderLayout());
        southHeader.setPreferredSize(new Dimension(1500, 50));
        southHeader.setBackground(new Color(173, 193, 124));
        JLabel logoLabel1 = new JLabel();
        logoLabel1.setIcon(new ImageIcon(scaledInstance));
        Border margin1 = new EmptyBorder(0,85,115,0);
        logoLabel1.setBorder(new CompoundBorder(border, margin1));
        southHeader.add(logoLabel1);

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(new Color(173, 193, 124));
        btn = new JButton();

        ImageIcon searchIcon = new ImageIcon("./images/search.png");
        Image scaledSearchInstance = searchIcon.getImage().getScaledInstance(47, 45, Image.SCALE_SMOOTH);
        ImageIcon search1Icon = new ImageIcon("./images/search-hover.png");
        Image scaledSearch1Instance = search1Icon.getImage().getScaledInstance(47, 45, Image.SCALE_SMOOTH);

        btn.setIcon(new ImageIcon(scaledSearchInstance));
        btn.setRolloverIcon(new ImageIcon(scaledSearch1Instance));
        btn.setPressedIcon(new ImageIcon(scaledSearch1Instance));
        btn.setHorizontalTextPosition(2);
        btn.setIconTextGap(20);
        btn.setText("Search");
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setForeground(Color.white);
        btn.setFont(new Font("Times New Roman", Font.BOLD + Font.PLAIN, 30));
        btn.setBorder(null);
        btn.setBackground(new Color(176, 194, 147));
        searchPanel.add(btn);
        Border margin2 = new EmptyBorder(0,0,0,85);
        searchPanel.setBorder(new CompoundBorder(searchPanel.getBorder(), margin2));
        southHeader.add(searchPanel, BorderLayout.EAST);

        panelNorth.add(northHeader, BorderLayout.NORTH);
        panelNorth.add(southHeader, BorderLayout.SOUTH);

        add(panelNorth, BorderLayout.NORTH);
    }

    public void createCenterPanel() {
        cardLayout = new CardLayout();
        panelCenter = new JPanel(cardLayout);
        panelCenter.setPreferredSize(new Dimension(1500, 720));
        loginPanel = new LoginPanel(controllerRef);
        panelCenter.add(loginPanel, "signIn");
        add(panelCenter, BorderLayout.CENTER);
    }

    public void createPlantList() {
        JPanel panelPlantList = new JPanel(new BorderLayout());
        plantList = new PlantList(controllerRef.getPlantList(), panelPlantList);
        panelCenter.add(panelPlantList, "plantList");
    }

    public PlantList getPlantList() {
        return plantList;
    }


    public void createSouthPanel() {
        JPanel panelSouth = new JPanel(new BorderLayout());

        panelSouth.setBackground(new Color(220, 229, 185));
        panelSouth.setPreferredSize(new Dimension(1500, 50));

        JLabel label = new JLabel("MyHappyPlants - Team 24 (Sys) \u00A9");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panelSouth.add(label);
        add(panelSouth, BorderLayout.SOUTH);
    }

    public void showLoginError(boolean show) {
        loginPanel.showLoginError(show);
    }

    public void setCardLayout(String constraint) {
        cardLayout.show(panelCenter, constraint);
        repaint();
    }

    class Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btn)) {
                controllerRef.buttonPushed("search");
            }
        }
    }
}
