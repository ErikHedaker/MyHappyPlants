package view.panels.plant;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.SpringLayout;

/**
    * The class sux, but yeah...
    * @author Fandoggie
    */

public class PlantCreationPanel extends JPanel {
    private Controller controller;

    public PlantCreationPanel(Controller controller){
        this.controller = controller;
        createEditPanel();
    }

    public void createEditPanel(){
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(50, 340, 50, 340));


        setBorder(BorderFactory.createEmptyBorder(10, 340, 0, 340));
        JPanel editPanel = new JPanel(new BorderLayout());

        Border border = BorderFactory.createLineBorder(Color.lightGray, 1, true);
        editPanel.setBorder(border);

        Border margin1 = new EmptyBorder(50,50,50,50);
        editPanel.setBorder(new CompoundBorder(editPanel.getBorder(), margin1));
        editPanel.setBackground(Color.white);

        JLabel title = new JLabel("ENTER PLANT DETAILS");
        title.setBorder(BorderFactory.createEmptyBorder(0,66,30,0));
        title.setBounds(10,10,10,10);
        title.setFont(new Font("Calibri light", Font.BOLD, 25));
        editPanel.add(title, BorderLayout.NORTH);


        JPanel labelPanel = new JPanel(new GridLayout(10,0,0,0));
        labelPanel.setBackground(Color.white);
        JPanel plantName = new JPanel(new BorderLayout());
        plantName.setBackground(Color.white);
        JLabel plantLabel = new JLabel("Plant: ");
        plantLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        plantName.add(plantLabel, BorderLayout.WEST);

        JTextField plantTF = new JTextField();
        plantTF.setPreferredSize(new Dimension(200, 18));
        plantName.add(plantTF, BorderLayout.EAST);

        labelPanel.add(plantName); //lägger till textfield och label

        JPanel nicknamePanel = new JPanel(new BorderLayout());
        nicknamePanel.setBackground(Color.white);

        JLabel nicknameLabel = new JLabel("Nickname (optional): ");
        nicknameLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        nicknamePanel.add(nicknameLabel, BorderLayout.WEST);

        JTextField nicknameTF = new JTextField();
        nicknameTF.setPreferredSize(new Dimension(200, 18));
        nicknamePanel.add(nicknameTF, BorderLayout.EAST);
        labelPanel.add(nicknamePanel);

        JPanel sizePanel = new JPanel(new BorderLayout());
        sizePanel.setBackground(Color.white);

        JLabel sizeLabel = new JLabel("Size: ");
        sizeLabel.setBackground(Color.white);
        sizeLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        sizePanel.add(sizeLabel, BorderLayout.WEST);

        JPanel boxes = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JCheckBox smallBox = new JCheckBox("Small");
        JCheckBox mediumBox = new JCheckBox("Medium");
        JCheckBox bigBox = new JCheckBox("Large");
        sizePanel.add(smallBox);
        sizePanel.add(mediumBox);
        sizePanel.add(bigBox);
        sizePanel.add(boxes, BorderLayout.EAST);
        labelPanel.add(sizePanel);

        JPanel climatePanel = new JPanel(new BorderLayout());
        climatePanel.setBackground(Color.white);

        JLabel climateLabel = new JLabel("Climate: ");
        climateLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        climatePanel.add(climateLabel, BorderLayout.WEST);

        String[] choices = {"Inside", "Outside", "Wet room"};
        JComboBox<String> climateMenu = new JComboBox<String>(choices);
        climateMenu.setBackground(Color.white);
        climateMenu.setPreferredSize(new Dimension(200, 18));
        climatePanel.add(climateMenu, BorderLayout.EAST);
        labelPanel.add(climatePanel);

        JPanel savePanel = new JPanel(new BorderLayout());
        savePanel.setBackground(Color.white);

        JButton saveButton = new JButton("Save");
        savePanel.add(saveButton, BorderLayout.SOUTH);
        editPanel.add(savePanel, BorderLayout.SOUTH);

        editPanel.add(labelPanel, BorderLayout.CENTER);

        add(editPanel);
    }
}
