package view.panels.plant;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * The class sux, but yeah...
 * @author Fandoggie
 */

public class PlantCreationPanel extends JPanel implements ActionListener {
    private Controller controller;
    private JButton backButton;
    private JToggleButton smallButton;
    private JToggleButton mediumButton;
    private JToggleButton largeButton;
    private JTextField waterTF;

    public PlantCreationPanel(Controller controller) {
        this.controller = controller;
        createPanelEdit();
    }
    /** Fandog was here **/
    public void createPanelEdit() {
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(50, 340, 50, 340));

        setBorder(BorderFactory.createEmptyBorder(10, 340, 0, 340));
        JPanel editPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 100, 15, 8);

        Border border = BorderFactory.createLineBorder(Color.lightGray, 1, true);
        editPanel.setBorder(border);

        Border margin1 = new EmptyBorder(50, 50, 50, 50);
        editPanel.setBorder(new CompoundBorder(editPanel.getBorder(), margin1));
        editPanel.setBackground(Color.white);

        backButton = new JButton("BACK");
        ImageIcon backImg = new ImageIcon("./images/backarrow.png");
        Image scaledEditImg = backImg.getImage().getScaledInstance(15, 15,
                Image.SCALE_AREA_AVERAGING);
        backButton.setIcon(new ImageIcon(scaledEditImg));
        backButton.setFont(new Font("Arial", Font.BOLD, 10));
        backButton.setBackground(Color.white);
        backButton.setFocusPainted(false);
        backButton.addActionListener(this);

        //gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0; // col
        gbc.gridy = 0; // row
        gbc.gridwidth = 1; // specify the num of cols
        gbc.gridheight = 1; // specify the number of rows
        gbc.weighty = 2; // separate space between rows
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        editPanel.add(backButton, gbc);

        JButton saveButton = new JButton();
        saveButton.setPreferredSize(new Dimension(120, 45));
        ImageIcon saveImg = new ImageIcon("./images/save-icon.png");
        Image scaledEditImage = saveImg.getImage().getScaledInstance(26, 25,
                Image.SCALE_AREA_AVERAGING);

        saveButton.setLayout(new FlowLayout(FlowLayout.LEFT));
        //saveButton.setIcon(new ImageIcon(scaledEditImage));
        JLabel iconLabel = new JLabel(new ImageIcon(scaledEditImage));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        JLabel textLabel = new JLabel("SAVE");
        saveButton.add(iconLabel);
        saveButton.add(textLabel);
        textLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        //saveButton.setIconTextGap(5);
        //saveButton.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        //saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
        //saveButton.setText("SAVE");
        //saveButton.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        saveButton.setBackground(Color.white);
        saveButton.setFont(new Font("Arial", Font.BOLD, 10));
        gbc.gridx = 4; // col
        gbc.gridy = 25; // row
        gbc.weighty = 0; // separate space between rows
        gbc.weightx = 0; // separate space between cols
        gbc.gridwidth = 1; // specify num of cols
        gbc.gridheight = 1; // specify num of rows
        gbc.anchor = GridBagConstraints.PAGE_END;
        editPanel.add(saveButton, gbc);

        JLabel title = new JLabel("ENTER PLANT DETAILS");
        title.setFont(new Font("Calibri light", Font.BOLD, 25));
        title.setBounds(10, 10, 10, 10);
        //gbc.fill = GridBagConstraints.CENTER;// if the component's da is larger
        // than component's requested size to resize the component.
        gbc.gridx = 4; // col
        gbc.gridy = 3; // row
        gbc.weighty = 5; // separate space between rows
        gbc.gridwidth = 1; // specify the num of cols
        gbc.gridheight = 1; // specify the num of rows
        gbc.anchor = GridBagConstraints.CENTER;
        editPanel.add(title, gbc);

        JLabel plantLabel = new JLabel("Plant: ");
        plantLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0; // col
        gbc.gridy = 7; // row
        gbc.weighty = 15;  // separate space between rows
        gbc.gridheight = 2;  // specify num of rows
        gbc.anchor = GridBagConstraints.WEST;
        editPanel.add(plantLabel, gbc);

        JTextField plantTF = new JTextField();
        plantTF.setPreferredSize(new Dimension(275, 30));
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 6; // col
        gbc.gridy = 7; // row
        gbc.weighty = 150; // separate space between rows
        gbc.gridwidth = 1; // specify num of cols
        gbc.gridheight = 1; // specify num of rows
        gbc.anchor = GridBagConstraints.EAST;
        editPanel.add(plantTF, gbc);

        JLabel nicknameLabel = new JLabel("Nickname (optional): ");
        nicknameLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0; // col
        gbc.gridy = 10; // row
        gbc.weighty = 15; // separate space between rows
        gbc.gridwidth = 1; // specify num of cols
        gbc.gridheight = 1; // specify num of rows
        gbc.anchor = GridBagConstraints.WEST;
        editPanel.add(nicknameLabel, gbc);

        JTextField nicknameTF = new JTextField();
        nicknameTF.setPreferredSize(new Dimension(275, 30));
        gbc.gridx = 6; // col
        gbc.gridy = 10; // row
        gbc.weighty = 150; // separate space between rows
        gbc.gridwidth = 1; // specify num of cols
        gbc.gridheight = 1; // specify num of rows
        gbc.anchor = GridBagConstraints.EAST;
        editPanel.add(nicknameTF, gbc);

        JLabel climateLabel = new JLabel("Climate: ");
        climateLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        gbc.gridx = 0; // col
        gbc.gridy = 17; // row
        gbc.weighty = 0; // separate space between rows
        gbc.weightx = 2; // separate space between cols
        gbc.gridwidth = 1; // specify num of cols
        gbc.gridheight = 1; // specify num of rows
        gbc.anchor = GridBagConstraints.WEST;
        editPanel.add(climateLabel, gbc);

        String[] choices = {"Inside", "Outside"};
        JComboBox<String> climateMenu = new JComboBox<String>(choices);
        climateMenu.setBackground(Color.white);
        climateMenu.setPreferredSize(new Dimension(275, 30));
        gbc.gridx = 6; // col
        gbc.gridy = 17; // row
        gbc.weighty = 5; // separate space between rows
        gbc.weightx = 2; // separate space between cols
        gbc.gridwidth = 1; // specify num of cols
        gbc.gridheight = 1; // specify num of rows
        gbc.anchor = GridBagConstraints.EAST;
        editPanel.add(climateMenu, gbc);

        JLabel sizeLabel = new JLabel("Size: ");
        sizeLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        //gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0; // col
        gbc.gridy = 11; // row
        gbc.weighty = 3; // separate space between rows
        gbc.gridwidth = 1; // specify num of cols
        gbc.gridheight = 1; // specify num of rows
        gbc.anchor = GridBagConstraints.WEST;
        editPanel.add(sizeLabel, gbc);

        ImageIcon imageIcon = new ImageIcon("./images/plant.jpg");
        Image image = imageIcon.getImage();
        Image smallimg = image.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(smallimg);

        ImageIcon imageIcon2 = new ImageIcon("./images/plant.jpg");
        Image image2 = imageIcon2.getImage();
        Image mediumimg = image2.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
        imageIcon2 = new ImageIcon(mediumimg);

        ImageIcon imageIcon3 = new ImageIcon("./images/plant.jpg");
        Image image3 = imageIcon3.getImage();
        Image largeimg = image3.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
        imageIcon3 = new ImageIcon(largeimg);

        smallButton = new JToggleButton(imageIcon);
        smallButton.setBackground(Color.white);
        smallButton.setFocusPainted(false);
        smallButton.setBorderPainted(false);
        //gbc.fill = GridBagConstraints.BELOW_BASELINE_TRAILING;
        gbc.gridx = 6; // col
        gbc.gridy = 11; // row
        gbc.weighty = 0; // separate space between rows
        gbc.weightx = 0; // separate space between cols
        gbc.gridwidth = 1; // specify num of cols
        gbc.gridheight = 1; // specify num of rowsg
        gbc.anchor = GridBagConstraints.LINE_START;
        editPanel.add(smallButton, gbc);

        smallButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("small");
                //myButtonGroup.add(smallButton);
                UIManager.put("ToggleButton.select", new Color(220, 229, 185));
                SwingUtilities.updateComponentTreeUI(smallButton);
            }
        });

        mediumButton = new JToggleButton(imageIcon2);
        mediumButton.setBackground(Color.white);
        mediumButton.setBorderPainted(false);
        mediumButton.setFocusPainted(false);
        //gbc.fill = GridBagConstraints.BELOW_BASELINE_TRAILING;
        gbc.gridx = 6; // col
        gbc.gridy = 11; // row
        gbc.weighty = 0; // separate space between rows
        gbc.weightx = 0; // separate space between cols
        gbc.gridwidth = 1; // specify num of cols
        gbc.gridheight = 1; // specify num of rows
        gbc.anchor = GridBagConstraints.CENTER;
        editPanel.add(mediumButton, gbc);

        mediumButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("medium");
                UIManager.put("ToggleButton.select", new Color(220, 229, 185));
                SwingUtilities.updateComponentTreeUI(mediumButton);
                //myButtonGroup.add(mediumButton);
            }
        });

        largeButton = new JToggleButton(imageIcon3);
        largeButton.setBackground(Color.white);
        largeButton.setBorderPainted(false);
        largeButton.setFocusPainted(false);
        //gbc.fill = GridBagConstraints.BELOW_BASELINE_TRAILING;
        gbc.gridx = 6; // col
        gbc.gridy = 11; // row
        gbc.weighty = 0; // separate space between rows
        gbc.weightx = 0; // separate space between cols
        gbc.gridwidth = 1; // specify num of cols
        gbc.gridheight = 1; // specify num of rows
        gbc.anchor = GridBagConstraints.LINE_END;
        editPanel.add(largeButton, gbc);

        largeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("bik");
                UIManager.put("ToggleButton.select", new Color(220, 229, 185));
                SwingUtilities.updateComponentTreeUI(largeButton);
                //myButtonGroup.add(largeButton);
            }
        });

        JLabel waterLabel = new JLabel("Time for water: ");
        waterLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0; // col
        gbc.gridy = 19; // row
        gbc.weighty = 15; // separate space between rows
        gbc.gridwidth = 1; // specify num of cols
        gbc.gridheight = 1; // specify num of rows
        gbc.anchor = GridBagConstraints.WEST;
        editPanel.add(waterLabel, gbc);

        waterTF = new JTextField();
        waterTF.setPreferredSize(new Dimension(275, 30));
        gbc.gridx = 6; // col
        gbc.gridy = 19; // row
        gbc.weighty = 0; // separate space between rows
        gbc.gridwidth = 1; // specify num of cols
        gbc.gridheight = 1; // specify num of rows
        gbc.anchor = GridBagConstraints.EAST;
        editPanel.add(waterTF, gbc);

        add(editPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            controller.buttonPushed("plantList");
        }
    }


    ButtonGroup myButtonGroup = new ButtonGroup() {

        @Override
        public void setSelected(ButtonModel model, boolean selected) {
            if (selected) {
                super.setSelected(model, selected);

            } else
                clearSelection();
        }
    };
}

