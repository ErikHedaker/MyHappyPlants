package view.panels;

import controller.Controller;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoadingPanel extends JPanel {
    Controller controller;

    public LoadingPanel(Controller controller){
        this.controller = controller;
        createLoadingPanel();
    }

    public void createLoadingPanel(){
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(50, 340, 50, 340));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 340, 0, 340));
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBackground(Color.white);

        Border margin1 = new EmptyBorder(50, 50, 50, 50);
        panel.setBorder(new CompoundBorder(panel.getBorder(), margin1));
        panel.setBackground(Color.white);
        Border border = BorderFactory.createLineBorder(Color.lightGray, 1, true);
        panel.setBorder(border);

        JLabel loginLabel = new JLabel("Loading your plants...");
        loginLabel.setFont(new Font("Calibri light", Font.BOLD, 30));
        loginLabel.setForeground(new Color(220, 229, 185));
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginLabel, gbc);


    }
}
