package view.panels.plant;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * The class is to be used as a graphical user interface for editing your plant.
 * @author Fanny Rosdahl Rosenglim
 */

public class PlantCreationPanel extends JPanel implements ActionListener {
    private Controller controller;
    private JButton backButton;
    private JButton saveButton;
    private JTextField nicknameTF;
    private JTextField waterTF;
    boolean creationMode = true;

    public PlantCreationPanel(Controller controller) {
        this.controller = controller;
        createPanelEdit();
    }
    /** This model is for creating the edit panel
     * @author Fanny Rosdahl Rosenglim **/
    public void createPanelEdit() {
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(50, 340, 50, 340));

        setBorder(BorderFactory.createEmptyBorder(10, 340, 0, 340));
        JPanel editPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 20, 20, 8);

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

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weighty = 2;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        editPanel.add(backButton, gbc);

        saveButton = new JButton();
        saveButton.setPreferredSize(new Dimension(120, 45));
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ImageIcon saveImg = new ImageIcon("./images/save-icon.png");
        Image scaledEditImage = saveImg.getImage().getScaledInstance(26, 25,
                Image.SCALE_AREA_AVERAGING);

        saveButton.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel iconLabel = new JLabel(new ImageIcon(scaledEditImage));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        JLabel textLabel = new JLabel("SAVE");
        saveButton.add(iconLabel);
        saveButton.add(textLabel);
        textLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        saveButton.setBackground(Color.white);
        saveButton.addActionListener(this);
        saveButton.setFont(new Font("Arial", Font.BOLD, 10));
        gbc.gridx = 3;
        gbc.gridy = 20;
        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.PAGE_END;
        editPanel.add(saveButton, gbc);

        JLabel title = new JLabel("ENTER PLANT DETAILS");
        title.setFont(new Font("Calibri light", Font.BOLD, 25));
        gbc.gridy = 3;
        gbc.gridx = 3;
        gbc.weighty = 5;
        gbc.gridheight = 1;
        editPanel.add(title, gbc);

        JLabel nicknameLabel = new JLabel("Nickname (optional): ");
        nicknameLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.weighty = 15;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        editPanel.add(nicknameLabel, gbc);

        nicknameTF = new JTextField();
        nicknameTF.setPreferredSize(new Dimension(275, 30));
        nicknameTF.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nicknameTF.setText("");
            }
        });
        gbc.gridx = 4;
        gbc.gridy = 13;
        gbc.weighty = 150;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.EAST;
        editPanel.add(nicknameTF, gbc);

        JLabel climateLabel = new JLabel("Climate: ");
        climateLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.weighty = 0;
        gbc.weightx = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        editPanel.add(climateLabel, gbc);

        String[] choices = {"Inside", "Outside"};
        JComboBox<String> climateMenu = new JComboBox<>(choices);
        climateMenu.setBackground(Color.white);
        climateMenu.setPreferredSize(new Dimension(275, 30));
        gbc.gridx = 4;
        gbc.gridy = 14;
        gbc.weighty = 5;
        gbc.weightx = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.EAST;
        editPanel.add(climateMenu, gbc);

        JLabel waterLabel = new JLabel("Interval Between Watering: ");
        waterLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.weighty = 15;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        editPanel.add(waterLabel, gbc);

        waterTF = new JTextField();
        waterTF.setPreferredSize(new Dimension(275, 30));
        waterTF.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                waterTF.setText("");
            }
        });
        gbc.gridx = 4;
        gbc.gridy = 15;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.EAST;
        editPanel.add(waterTF, gbc);

        JLabel sizeLabel = new JLabel("Size: ");
        sizeLabel.setFont(new Font("Calibri light", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 16;
        gbc.weighty = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        editPanel.add(sizeLabel, gbc);

        DefaultListModel model = new DefaultListModel();

        Image img1 = new ImageIcon("images/flower_small.png").getImage().getScaledInstance(70,110, Image.SCALE_SMOOTH);
        Image img2 = new ImageIcon("images/flower_medium.png").getImage().getScaledInstance(80,120, Image.SCALE_SMOOTH);
        Image img3 = new ImageIcon("images/flower_big.png").getImage().getScaledInstance(90,130, Image.SCALE_SMOOTH);

        model.addElement(new ImageIcon(img1));
        model.addElement(new ImageIcon(img2));
        model.addElement(new ImageIcon(img3));

        JList list = new JList(model);
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        list.setVisibleRowCount(1);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        gbc.gridx = 4;
        gbc.gridy = 16;
        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        editPanel.add(list, gbc);

        add(editPanel);

    }

    public void setCreationMode(boolean creationMode) {
        this.creationMode = creationMode;
    }

    /**
     *
     * @author Viktor Johansson
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            controller.buttonPushed("plantList");
        } else if (e.getSource() == saveButton) {
            new Thread(() -> upsertPlantDetails()).start();
        }
    }

    public void setWaterTF(String text) {
        waterTF.setText(text);
    }

    public void setNicknameTF(String text) {
        nicknameTF.setText(text);
    }

    public boolean isCreationMode() {
        return creationMode;
    }

    public void upsertPlantDetails() {
        controller.setCardLayout("loading-screen");
        if (creationMode) {
            controller.createPlant(nicknameTF.getText(), waterTF.getText());
        } else {
            controller.editSelectedPlant(nicknameTF.getText(), waterTF.getText());
        }
        if (saveButton != null) {
            getRootPane().setDefaultButton(saveButton);
        }
    }

}