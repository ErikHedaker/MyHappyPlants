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
    private JButton btnExit;
    private JButton btnSave;
    private JPasswordField passwordField;
    private JTextField usernameTF;

    private JPanel profilePanel;
    private JPanel editProfilePanel;

    public UserProfile(Controller controller){
        this.controller = controller;
        createProfilePanel();
        createEditProfilePanel();
    }

    public void createProfilePanel(){
        if(profilePanel != null) profilePanel.setVisible(false);
        setBackground(new Color(245,245,245));

        setLayout(new BorderLayout());
        profilePanel = new JPanel(new GridBagLayout());

        JPanel userInformationPanel = new JPanel(new BorderLayout());
        userInformationPanel.setBackground(Color.white);
        title = new JLabel("Profile");
        title.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));
        title.setFont(new Font("Calibri", Font.BOLD, 25));
        profilePanel.add(userInformationPanel, BorderLayout.NORTH);

        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Calibri", Font.PLAIN, 22));
        lblName.setBounds(10,70,50,30);
        profilePanel.add(lblName);

        usernameTF = new JTextField();
        usernameTF.setBounds(100,80,200,20);
        usernameTF.setEditable(false);
        userInformationPanel.add(usernameTF);
        usernameTF.setColumns(10);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Calibri", Font.PLAIN, 22));
        lblPassword.setBounds(10,115,80,20);
        userInformationPanel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(100,110,200,20);
        passwordField.setEditable(false);
        passwordField.setEchoChar('*');
        userInformationPanel.add(passwordField);
        passwordField.setColumns(10);

        btnExit = new JButton("Exit");
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExit.setFont(new Font("Arial", Font.PLAIN, 21));
        btnExit.setBackground(Color.lightGray);
        btnExit.setForeground(Color.black);
        btnExit.setPreferredSize(new Dimension(150, 40));
        btnExit.addActionListener(this);
        userInformationPanel.add(btnExit, BorderLayout.CENTER);

        btnEdit = new JButton("Edit profile");
        btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEdit.setFont(new Font("Arial", Font.PLAIN, 21));
        btnEdit.setBackground(Color.lightGray);
        btnEdit.setForeground(Color.black);
        btnEdit.setPreferredSize(new Dimension(150, 40));
        btnEdit.addActionListener(this);
        userInformationPanel.add(btnEdit, BorderLayout.CENTER);

    }

    public void createEditProfilePanel(){
        if(profilePanel != null) profilePanel.setVisible(false);
        setBackground(new Color(245,245,245));

        setLayout(new BorderLayout());
        editProfilePanel = new JPanel(new GridBagLayout());

        JPanel userInformationPanel = new JPanel(new BorderLayout());
        userInformationPanel.setBackground(Color.white);
        title = new JLabel("Edit profile");
        title.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));
        title.setFont(new Font("Calibri", Font.BOLD, 25));
        editProfilePanel.add(userInformationPanel, BorderLayout.NORTH);

        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Calibri", Font.PLAIN, 22));
        lblName.setBounds(10,70,50,30);
        editProfilePanel.add(lblName);

        usernameTF = new JTextField();
        usernameTF.setBounds(100,80,200,20);
        usernameTF.setEditable(false);
        userInformationPanel.add(usernameTF);
        usernameTF.setColumns(10);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Calibri", Font.PLAIN, 22));
        lblPassword.setBounds(10,115,80,20);
        userInformationPanel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(100,110,200,20);
        passwordField.setEditable(true);
        passwordField.setEchoChar('*');
        userInformationPanel.add(passwordField);
        passwordField.setColumns(10);

        JLabel lblPassword2 = new JLabel("Password");
        lblPassword2.setFont(new Font("Calibri", Font.PLAIN, 22));
        lblPassword2.setBounds(10,115,80,20);
        userInformationPanel.add(lblPassword2);

        passwordField = new JPasswordField();
        passwordField.setBounds(100,110,200,20);
        passwordField.setEditable(true);
        passwordField.setEchoChar('*');
        userInformationPanel.add(passwordField);
        passwordField.setColumns(10);

        btnExit = new JButton("Exit");
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExit.setFont(new Font("Arial", Font.PLAIN, 21));
        btnExit.setBackground(Color.lightGray);
        btnExit.setForeground(Color.black);
        btnExit.setPreferredSize(new Dimension(150, 40));
        btnExit.addActionListener(this);
        userInformationPanel.add(btnExit, BorderLayout.CENTER);

        btnSave = new JButton("Save changes");
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.setFont(new Font("Arial", Font.PLAIN, 21));
        btnSave.setBackground(Color.lightGray);
        btnSave.setForeground(Color.black);
        btnSave.setPreferredSize(new Dimension(150, 40));
        btnSave.addActionListener(this);
        userInformationPanel.add(btnSave, BorderLayout.CENTER);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() ==  btnExit) {
            new ConfirmationDialog(controller)
                    .setConfirmationMessage("Are you sure you want to exit?")
                    .showConfirmationDialog(DialogType.PROCEED_BACK_CONFIRMATION_DIALOG);
        } else if (e.getSource() == btnSave) {

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
