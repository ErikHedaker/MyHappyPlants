package view.panels.profile;

import controller.Controller;
import view.panels.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserProfile extends JPanel implements ActionListener, KeyListener {
    private Controller controller;
    private JLabel title, pic;
    private JButton btnEdit, backButton;
    private JPasswordField passwordTF;
    private JTextField usernameTF;
    private JPanel profilePanel;


    public UserProfile(Controller controller){
        this.controller = controller;
        createProfilePanel();
    }

    public void createProfilePanel(){

        if(profilePanel != null) profilePanel.setVisible(false);
        setBackground(new Color(245,245,245));
        setBorder(BorderFactory.createEmptyBorder(0, 300, 0, 300));

        setLayout(new BorderLayout());
        profilePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(50,3,15,3);

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
        gbc.weighty = 250;
        gbc.ipady = 5;
        gbc.ipadx = 6;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        profilePanel.add(backButton, gbc);

        profilePanel.setBackground(Color.white);
        title = new JLabel("Profile");
        title.setFont(new Font("Calibri", Font.BOLD, 25));
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.gridwidth = 110;

        gbc.anchor = GridBagConstraints.PAGE_START;
        profilePanel.add(title, gbc);

        JLabel lblName = new JLabel("Name: ");
        lblName.setFont(new Font("Calibri light", Font.PLAIN, 22));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        profilePanel.add(lblName, gbc);

        usernameTF = new JTextField();
        usernameTF.setPreferredSize(new Dimension(100,20));
        usernameTF.setBackground(Color.white);
        usernameTF.setEditable(false);
        gbc.gridx = 8;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        profilePanel.add(usernameTF, gbc);

        JLabel lblPassword = new JLabel("Password: ");
        lblPassword.setFont(new Font("Calibri light", Font.PLAIN, 22));
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.weighty = 250;
        gbc.anchor = GridBagConstraints.WEST;
        profilePanel.add(lblPassword, gbc);

        passwordTF = new JPasswordField();
        passwordTF.setPreferredSize(new Dimension(100,20));
        passwordTF.setText("*******");
        passwordTF.setBackground(Color.white);
        passwordTF.setEditable(false);
        gbc.gridx = 8;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        profilePanel.add(passwordTF, gbc);


        btnEdit = new JButton(" EDIT ");
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.setFont(new Font("Arial", Font.PLAIN, 15));
        btnEdit.setBackground(Color.WHITE);
        btnEdit.setForeground(Color.black);
        btnEdit.setPreferredSize(new Dimension(30, 15));
        btnEdit.addActionListener(this);
        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.ipadx = 55;
        gbc.ipady = 10;
        gbc.anchor = GridBagConstraints.PAGE_END;
        profilePanel.add(btnEdit, gbc);

        JLabel lblPic = new JLabel("Profile Picture: ");
        lblPic.setFont(new Font("Calibri light", Font.PLAIN, 22));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        profilePanel.add(lblPic, gbc);

        pic = new JLabel();
        setPic(new ImageIcon("./images/defpic.jpg"));



        gbc.gridx = 8;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        profilePanel.add(pic, gbc);


        add(profilePanel);


    }

    public void setPic(ImageIcon imageIcon) {
        if (imageIcon == null) {
            imageIcon = new ImageIcon("./images/defpic.jpg");
        }
        Image img = imageIcon.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH);


        pic.setIcon(new ImageIcon(img));
    }

    public void setUsernameTF(String text) {
        this.usernameTF.setText(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            controller.buttonPushed("plantList");
        }
        if (e.getSource() == btnEdit){
            controller.buttonPushed("edit profile page");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

