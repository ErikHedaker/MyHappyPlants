package view.panels.plant;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PlantInfoPanel extends JPanel {

    private JPanel panelInfo;

    public PlantInfoPanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(0, 340, 0, 340));
        setLayout(new BorderLayout());
        ScrollPane scrollPane = new ScrollPane();
        panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,3,6,3);

        Border border = BorderFactory.createLineBorder(Color.lightGray, 1, true);
        panelInfo.setBorder(border);

        ImageIcon logo = new ImageIcon("./images/logo-happy.png");
        ImageIcon scaledLogo = new ImageIcon(logo.getImage().getScaledInstance(200,200, Image.SCALE_SMOOTH));
        JLabel logoLabel = new JLabel(scaledLogo);
        gbc.gridy = 0;
        gbc.gridx = 3;
        gbc.weighty = 20;
        panelInfo.add(logoLabel, gbc);

        JLabel title = new JLabel("Welcome To MyHappyPlants!");
        title.setFont(new Font("Calibri", Font.TRUETYPE_FONT + Font.BOLD, 25));
        gbc.gridy = 1;
        gbc.gridx = 3;
        gbc.weighty = 70;
        panelInfo.add(title, gbc);

        JTextArea body = new JTextArea("This");
        body.setFont(new Font("Calibri light", Font.TRUETYPE_FONT, 23));
        gbc.gridy = 2;
        gbc.gridx = 3;
        gbc.weighty = 0.5;
        panelInfo.add(body, gbc);
        panelInfo.setPreferredSize(new Dimension(500, 1000));

        scrollPane.add(panelInfo);

        JScrollPane scrollbar = new JScrollPane(scrollPane);

        add(scrollbar);
    }
}
