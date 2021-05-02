package view.panels.plant;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
    * The class sux, but yeah...
    * @author Fandoggie
    */

public class PlantCreationPanel extends JPanel {
    private Controller controller;

    public PlantCreationPanel(Controller controller){
        this.controller = controller;
        createPanelEdit();
    }

    public void createPanelEdit(){
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(50, 340, 50, 340));

        setBorder(BorderFactory.createEmptyBorder(10, 340, 0, 340));
        JPanel editPanel = new JPanel(new BorderLayout());

        Border border = BorderFactory.createLineBorder(Color.lightGray, 1, true);
        editPanel.setBorder(border);

        Border margin1 = new EmptyBorder(50,50,50,50);
        editPanel.setBorder(new CompoundBorder(editPanel.getBorder(), margin1));
        editPanel.setBackground(Color.white);

        JLabel title = new JLabel("ENTER PLANT DETAILS", JLabel.CENTER);
        title.setBounds(10,10,10,10);
        title.setFont(new Font("Calibri light", Font.BOLD, 25));
        editPanel.add(title, BorderLayout.NORTH);

        JPanel labelPanel = new JPanel(new GridLayout(20,0,0,10));
        labelPanel.setBackground(Color.white);
        JPanel plantName = new JPanel(new BorderLayout());
        plantName.setBackground(Color.white);
        JLabel plantLabel = new JLabel("Plant: ");
        plantLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        plantName.add(plantLabel, BorderLayout.WEST);

        JTextField plantTF = new JTextField();
        plantTF.setPreferredSize(new Dimension(200, 18));
        plantName.add(plantTF, BorderLayout.EAST);

        labelPanel.add(plantName);

        JPanel nicknamePanel = new JPanel(new BorderLayout());
        nicknamePanel.setBackground(Color.white);

        JLabel nicknameLabel = new JLabel("Nickname (optional): ");
        nicknameLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        nicknamePanel.add(nicknameLabel, BorderLayout.WEST);

        JTextField nicknameTF = new JTextField();
        nicknameTF.setPreferredSize(new Dimension(200, 18));
        nicknamePanel.add(nicknameTF, BorderLayout.EAST);
        labelPanel.add(nicknamePanel);

        JPanel sizePanel = new JPanel();
        sizePanel.setBackground(Color.white);

        JLabel sizeLabel = new JLabel("Size: ");
        sizeLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        sizePanel.add(sizeLabel, BorderLayout.WEST);

        JPanel picPanel = new JPanel();

        picPanel.setBackground(Color.white);
        ImageIcon imageIcon = new ImageIcon("./images/plant.jpg");
        Image image = imageIcon.getImage();
        Image smallimg = image.getScaledInstance(25,25,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(smallimg);

        ImageIcon imageIcon2 = new ImageIcon("./images/plant.jpg");
        Image image2 = imageIcon2.getImage();
        Image mediumimg = image2.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIcon2 = new ImageIcon(mediumimg);

        ImageIcon imageIcon3 = new ImageIcon("./images/plant.jpg");
        Image image3 = imageIcon3.getImage();
        Image largeimg = image3.getScaledInstance(70, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIcon3 = new ImageIcon(largeimg);

        JButton button1 = new JButton(imageIcon);
        button1.setBackground(Color.white);
        button1.setBorderPainted(false);
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("small");
            }
        });

        JButton button2 = new JButton(imageIcon2);
        button2.setBackground(Color.white);
        button2.setBorderPainted(false);
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("medium");
            }
        });

        JButton button3 = new JButton(imageIcon3);
        button3.setBackground(Color.white);
        button3.setBorderPainted(false);
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("bik");
            }
        });

        picPanel.add(button1);
        picPanel.add(button2);
        picPanel.add(button3);

        sizePanel.add(picPanel, BorderLayout.EAST);

        labelPanel.add(sizePanel, BorderLayout.CENTER);

        JPanel climatePanel = new JPanel(new BorderLayout());
        climatePanel.setBackground(Color.white);

        JLabel climateLabel = new JLabel("Climate: ");
        climateLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        climatePanel.add(climateLabel, BorderLayout.WEST);

        String[] choices = {"Inside", "Outside"};
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
