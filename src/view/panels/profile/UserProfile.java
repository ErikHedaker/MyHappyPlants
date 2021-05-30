package view.panels.profile;

import controller.Controller;
import controller.Utility;
import model.Profile;
import view.dialog.ConfirmationDialog;
import view.dialog.DialogType;
import view.dialog.MessageDialog;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserProfile extends JPanel implements ActionListener, KeyListener {
    private Profile profile;
    private Controller controller;
    private JLabel title;
    private JButton btnEdit;
    private JButton btnSave, editBackButton, backButton, pictureBtn;
    private JPasswordField passwordTF;
    private JTextField usernameTF;

    private JPanel profilePanel;
    private JPanel editProfilePanel;

    public UserProfile(Controller controller){
        this.controller = controller;
        createProfilePanel();
        //createEditProfilePanel();
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
        lblName.setFont(new Font("Calibri", Font.PLAIN, 22));
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
        lblPassword.setFont(new Font("Calibri", Font.PLAIN, 22));
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

        JLabel lblpic = new JLabel("Profile Picture: ");
        lblName.setFont(new Font("Calibri", Font.PLAIN, 22));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        profilePanel.add(lblpic, gbc);

        pictureBtn = new JButton("pic");
        pictureBtn.setPreferredSize(new Dimension(100,20));
        pictureBtn.setBackground(Color.white);

        gbc.gridx = 8;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        profilePanel.add(pictureBtn, gbc);


        add(profilePanel);


    }

    public void createEditProfilePanel(){

        if(profilePanel != null) profilePanel.setVisible(false);
        setBackground(new Color(245,245,245));
        setBorder(BorderFactory.createEmptyBorder(0, 300, 0, 300));

        setLayout(new BorderLayout());
        editProfilePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(50,3,15,3);

        editBackButton = new JButton("BACK");
        ImageIcon backImg = new ImageIcon("./images/backarrow.png");
        Image scaledEditImg = backImg.getImage().getScaledInstance(15, 15,
                Image.SCALE_AREA_AVERAGING);
        editBackButton.setIcon(new ImageIcon(scaledEditImg));
        editBackButton.setFont(new Font("Arial", Font.BOLD, 10));
        editBackButton.setBackground(Color.WHITE);
        editBackButton.setFocusPainted(false);
        editBackButton.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 250;
        gbc.ipady = 5;
        gbc.ipadx = 6;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        profilePanel.add(backButton, gbc);

        profilePanel.setBackground(Color.white);
        title = new JLabel("Edit Profile");
        title.setFont(new Font("Calibri", Font.BOLD, 25));
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.gridwidth = 110;

        gbc.anchor = GridBagConstraints.PAGE_START;
        editProfilePanel.add(title, gbc);

        JLabel lblName = new JLabel("New Name: ");
        lblName.setFont(new Font("Calibri", Font.PLAIN, 22));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        editProfilePanel.add(lblName, gbc);

        usernameTF = new JTextField();
        usernameTF.setPreferredSize(new Dimension(100,20));
        usernameTF.setBackground(Color.white);
        gbc.gridx = 8;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        editProfilePanel.add(usernameTF, gbc);

        JLabel lblPassword = new JLabel("New Password: ");
        lblPassword.setFont(new Font("Calibri", Font.PLAIN, 22));
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.weighty = 250;
        gbc.anchor = GridBagConstraints.WEST;
        profilePanel.add(lblPassword, gbc);

        passwordTF = new JPasswordField();
        passwordTF.setPreferredSize(new Dimension(100,20));
        passwordTF.setBackground(Color.white);
        passwordTF.setEchoChar('*');
        gbc.gridx = 8;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        editProfilePanel.add(passwordTF, gbc);

        btnSave = new JButton(" SAVE CHANGES ");
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.setFont(new Font("Arial", Font.PLAIN, 15));
        btnSave.setBackground(Color.WHITE);
        btnSave.setForeground(Color.black);
        btnSave.setPreferredSize(new Dimension(30, 15));
        btnSave.addActionListener(this);
        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.ipadx = 55;
        gbc.ipady = 10;
        gbc.anchor = GridBagConstraints.PAGE_END;
        editProfilePanel.add(btnSave, gbc);

        add(editProfilePanel);

    }

    public void setUsernameTF(String text) {
        this.usernameTF.setText(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() ==  backButton) {
            controller.buttonPushed("plantList");
        }
        if (e.getSource() ==  editBackButton) {
            new ConfirmationDialog(controller)
                    .setConfirmationMessage("Are you sure you want to exit, you edits will not be saved?")
                    .showConfirmationDialog(DialogType.PROCEED_BACK_CONFIRMATION_DIALOG);
        } else if (e.getSource() == btnSave) {
        if(e.getSource() == pictureBtn){
            controller.buttonPushed("");
        }
        }

    }

    public void setProfile(String name) {usernameTF.setText(name);
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

