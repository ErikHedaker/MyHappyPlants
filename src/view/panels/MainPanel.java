package view.panels;

import controller.Controller;
import view.MainFrame;
import view.PlantList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel panelCenter;

    public MainPanel() {
        setLayout(new BorderLayout());
        createNorthPanel();
        createCenterPanel();
        setCardLayout("plant_list");
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
        logoLabel1.setBorder(new CompoundBorder(border, margin));
        southHeader.add(logoLabel1, BorderLayout.SOUTH);

        JButton btn = new JButton();
        btn.setIcon(new ImageIcon("./images/logo-happy.png"));

        panelNorth.add(northHeader, BorderLayout.NORTH);
        panelNorth.add(southHeader, BorderLayout.SOUTH);

        add(panelNorth, BorderLayout.NORTH);
    }

    public void createCenterPanel() {
        cardLayout = new CardLayout();
        panelCenter = new JPanel(cardLayout);

        JPanel panelPlantList = new JPanel(new BorderLayout());
        Controller controller = Controller.getInstance();

        new PlantList(controller.getPlantList(), panelPlantList);

        panelCenter.add(panelPlantList, "plant_list");

        add(panelCenter, BorderLayout.SOUTH);
    }

    public void setCardLayout(String constraint) {
        cardLayout.show(panelCenter, constraint);
        repaint();
    }
}
