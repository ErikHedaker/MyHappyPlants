package view.panels.plant;

import controller.Controller;

//Use for ranks as an about panel...

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlantRankPanel extends JPanel implements ActionListener {
    private Controller controller;
    private JButton backButton;

    public PlantRankPanel(Controller controller){
        this.controller = controller;
        createRankPanel();
    }

    public void createRankPanel() {
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(0, 300, 0, 300));

        setLayout(new BorderLayout());

        JPanel rankPanel = new JPanel(new GridBagLayout());
        rankPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1,0,3,0);

        backButton = new JButton("BACK");
        ImageIcon backImg = new ImageIcon("./images/backarrow.png");
        Image scaledEditImg = backImg.getImage().getScaledInstance(15, 15,
                Image.SCALE_AREA_AVERAGING);
        backButton.setIcon(new ImageIcon(scaledEditImg));
        backButton.setFont(new Font("Arial", Font.BOLD, 10));
        backButton.setBackground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 0;
        gbc.gridheight = 3;
        gbc.weighty = 2;
        gbc.ipady = 5;
        gbc.ipadx = 6;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        rankPanel.add(backButton, gbc);


        JLabel title = new JLabel("RANKING SYSTEM");
        title.setFont(new Font("Calibri light", Font.PLAIN, 25));
        title.setForeground(new Color(0x3B023B));
        title.setBorder(BorderFactory.createMatteBorder(0,0,1,0, new Color(0x3B023B)));
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 5;
        gbc.anchor = GridBagConstraints.NORTH;
        rankPanel.add(title, gbc);

        ImageIcon gimg = new ImageIcon("./images/gold.png");
        Image scaledGoldImage = gimg.getImage().getScaledInstance(170, 170,
                Image.SCALE_SMOOTH);
        JLabel goldLabel = new JLabel(new ImageIcon(scaledGoldImage));
        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.ipadx = 70;
        gbc.ipady = 70;
        gbc.anchor = GridBagConstraints.WEST;
        rankPanel.add(goldLabel, gbc);

        ImageIcon simg = new ImageIcon("./images/silver.png");
        Image scaledSilverImage = simg.getImage().getScaledInstance(170, 170,
                Image.SCALE_SMOOTH);
        JLabel silverLabel = new JLabel(new ImageIcon(scaledSilverImage));
        gbc.gridy = 2;
        gbc.gridx = 2;
        gbc.ipadx = 70;
        gbc.ipady = 70;
        gbc.anchor = GridBagConstraints.WEST;
        rankPanel.add(silverLabel, gbc);


        ImageIcon bimg = new ImageIcon("./images/bronze.png");
        Image scaledBronzeImage = bimg.getImage().getScaledInstance(170, 170,
                Image.SCALE_SMOOTH);
        JLabel BronzeLabel = new JLabel(new ImageIcon(scaledBronzeImage));
        gbc.gridy = 2;
        gbc.gridx = 3;
        gbc.ipadx = 70;
        gbc.ipady = 70;
        gbc.anchor = GridBagConstraints.WEST;
        rankPanel.add(BronzeLabel, gbc);


        add(rankPanel);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backButton){
            controller.buttonPushed("plantList");
        }
    }
}
