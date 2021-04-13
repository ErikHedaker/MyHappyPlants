package view.panels;

import controller.Controller;
import view.Utilities;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private JButton signInBtn;
    private Controller controller;
    private JPasswordField passwordTF;
    private JTextField usernameTF;
    private JLabel wrongLogin;

    public LoginPanel(Controller controller) {
        this.controller = controller;
        createLoginPanel();
    }

    public void createLoginPanel() {
        setBackground(new Color(245, 245, 245));
        setPreferredSize(new Dimension(1100, 720));
        setBorder(BorderFactory.createEmptyBorder(0, 340, 0, 340));
        setBorder(BorderFactory.createEmptyBorder(120,0,0,0));
        JPanel loginPanel = new JPanel(new BorderLayout());

        Border border = BorderFactory.createLineBorder(Color.lightGray, 1, true);
        loginPanel.setBorder(border);

        Border margin1 = new EmptyBorder(50,50,50,50);
        loginPanel.setBorder(new CompoundBorder(loginPanel.getBorder(), margin1));
        loginPanel.setBackground(Color.white);


        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setBackground(Color.white);
        JLabel loginText = new JLabel("Sign in");
        loginText.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));
        loginText.setFont(new Font("Calibri", Font.BOLD, 25));
        usernamePanel.add(loginText, BorderLayout.NORTH);
        JLabel usernameLabel = new JLabel("Username    ");
        usernameLabel.setFont(new Font("Calibri light", Font.PLAIN, 25));
        usernamePanel.add(usernameLabel, BorderLayout.CENTER);

        usernameTF = new JTextField();
        usernameTF.setPreferredSize(new Dimension(350, 40));
        usernamePanel.add(usernameTF, BorderLayout.SOUTH);
        loginPanel.add(usernamePanel, BorderLayout.NORTH);


        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(Color.white);
        Border margin2 = new EmptyBorder(5,0,0,0);
        passwordPanel.setBorder(new CompoundBorder(passwordPanel.getBorder(), margin2));

        JLabel passwordLabel = new JLabel("Password    ");
        passwordLabel.setFont(new Font("Calibri light", Font.PLAIN, 25));
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordTF = new JPasswordField(0);
        passwordTF.setEchoChar('*');
        passwordTF.setPreferredSize(new Dimension(350, 40));
        passwordPanel.add(passwordTF, BorderLayout.CENTER);

        wrongLogin = new JLabel("Invalid username or password.");
        wrongLogin.setForeground(new Color(142, 25, 25));
        wrongLogin.setVisible(false);
        passwordPanel.add(wrongLogin, BorderLayout.SOUTH);

        loginPanel.add(passwordPanel, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new BorderLayout());
        buttons.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
        buttons.setBackground(Color.white);
        Border margin3 = new EmptyBorder(5,0,0,0);
        buttons.setBorder(new CompoundBorder(buttons.getBorder(), margin3));

        signInBtn = new JButton("Sign in");
        signInBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signInBtn.setFont(new Font("Calibri", Font.PLAIN, 21));
        signInBtn.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
        signInBtn.setBackground(new Color(51, 51, 52));
        signInBtn.setForeground(Color.white);
        signInBtn.setPreferredSize(new Dimension(150, 50));
        signInBtn.addActionListener(new Action());
        buttons.add(signInBtn, BorderLayout.CENTER);

        JPanel register = new JPanel(new FlowLayout());
        register.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
        register.setBackground(Color.white);


        JButton registerBtn = new JButton("Create One");
        registerBtn.setForeground(new Color(26, 122, 169));
        registerBtn.setBorder(null);
        registerBtn.setBackground(Color.white);
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        register.add(registerBtn, FlowLayout.LEFT);

        JLabel registerTxt = new JLabel("Don't have an account? ");
        register.add(registerTxt, FlowLayout.LEFT);

        buttons.add(register, BorderLayout.SOUTH);
        loginPanel.add(buttons, BorderLayout.SOUTH);

        add(loginPanel);
    }

    public JButton getSignInButton() {
        return signInBtn;
    }

    public void showLoginError(boolean show) {
        wrongLogin.setVisible(show);
    }

    public void invalidPasswordMessage(){
        JOptionPane.showMessageDialog(null,"The Password Must Be At Least 6 Characters Long");
    }

    class Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(getSignInButton())) {
                controller.buttonPushed("loading-screen");
                new Thread( ( ) -> controller.loginAttempt(usernameTF.getText(), String.valueOf(passwordTF.getPassword())) ).start( );
            }
        }
    }
}
