package view.panels.login;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginPanel extends JPanel implements ActionListener, KeyListener {

    private JButton signInBtn;
    private Controller controller;
    private JPasswordField passwordTF;
    private JPasswordField passwordTF1;
    private JTextField usernameTF;
    private JLabel wrongLogin;
    private JButton registerBtn;
    private JPanel passwordPanel;
    private JPanel loginPanel;
    private JPanel passwordsFields;
    private LoginStatus status;
    private JLabel title;
    private JLabel password1Label;
    private JLabel registerTxt;

    public LoginPanel(Controller controller) {
        this.controller = controller;
        createLoginPanel();
    }

    public void createLoginPanel() {
        if (loginPanel != null) loginPanel.setVisible(false);
        setBackground(new Color(245, 245, 245));

        setBorder(BorderFactory.createEmptyBorder(10, 340, 0, 340));
        loginPanel = new JPanel(new BorderLayout());
        Border border = BorderFactory.createLineBorder(Color.lightGray, 1, true);
        loginPanel.setBorder(border);

        Border margin1 = new EmptyBorder(50,50,50,50);
        loginPanel.setBorder(new CompoundBorder(loginPanel.getBorder(), margin1));
        loginPanel.setBackground(Color.white);


        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setBackground(Color.white);
        title = new JLabel("Sign in");
        title.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));
        title.setFont(new Font("Calibri", Font.BOLD, 25));
        usernamePanel.add(title, BorderLayout.NORTH);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Calibri light", Font.PLAIN, 22));
        usernamePanel.add(usernameLabel, BorderLayout.CENTER);

        usernameTF = new JTextField();
        usernameTF.addKeyListener(this);
        usernameTF.setPreferredSize(new Dimension(350, 40));
        usernamePanel.add(usernameTF, BorderLayout.SOUTH);
        loginPanel.add(usernamePanel, BorderLayout.NORTH);


        passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(Color.white);
        Border margin2 = new EmptyBorder(5,0,0,0);
        passwordPanel.setBorder(new CompoundBorder(passwordPanel.getBorder(), margin2));

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Calibri light", Font.PLAIN, 22));
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);

        passwordsFields = new JPanel(new BorderLayout());

        passwordTF = new JPasswordField(0);
        passwordTF.addKeyListener(this);
        passwordTF.setEchoChar('*');
        passwordTF.setPreferredSize(new Dimension(350, 40));
        passwordsFields.add(passwordTF, BorderLayout.NORTH);

        JPanel passwordPanel1 = new JPanel(new BorderLayout());
        passwordPanel1.setBackground(Color.white);
        Border passMargin = new EmptyBorder(5,0,0,0);
        passwordPanel1.setBorder(new CompoundBorder(passwordPanel1.getBorder(), passMargin));

        password1Label = new JLabel("Password (Confirm)");
        password1Label.setFont(new Font("Calibri light", Font.PLAIN, 22));
        password1Label.setVisible(false);
        passwordPanel1.add(password1Label, BorderLayout.NORTH);

        passwordTF1 = new JPasswordField(0);
        passwordTF1.setEchoChar('*');
        passwordTF1.setPreferredSize(new Dimension(350, 40));
        passwordTF1.setVisible(false);
        passwordPanel1.add(passwordTF1, BorderLayout.SOUTH);
        passwordsFields.add(passwordPanel1, BorderLayout.SOUTH);

        passwordPanel.add(passwordsFields, BorderLayout.CENTER);

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
        signInBtn.addActionListener(this);
        buttons.add(signInBtn, BorderLayout.CENTER);

        JPanel register = new JPanel(new FlowLayout());
        register.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
        register.setBackground(Color.white);


        registerBtn = new JButton("Create One");
        registerBtn.setForeground(new Color(26, 122, 169));
        registerBtn.setBorder(null);
        registerBtn.setBackground(Color.white);
        registerBtn.addActionListener(this);
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        register.add(registerBtn, FlowLayout.LEFT);

        registerTxt = new JLabel("Don't have an account? ");
        register.add(registerTxt, FlowLayout.LEFT);

        buttons.add(register, BorderLayout.SOUTH);
        loginPanel.add(buttons, BorderLayout.SOUTH);

        add(loginPanel);
    }

    public void setStatus(LoginStatus status) {
        this.status = status;
    }

    public LoginStatus getStatus() {
        return status;
    }

    public void createRegisterPanel() {
        showLoginError(false);
        setBorder(BorderFactory.createEmptyBorder(10, 340, 0, 340));
        password1Label.setVisible(true);
        passwordTF1.setVisible(true);
        title.setText("Register");
        signInBtn.setText("Create Account");
        wrongLogin.setText("Password should be longer than 4 letters and needs to match.");
        registerTxt.setText("Already registered? ");
        registerBtn.setText("Sign In");
        loginPanel.revalidate();
        loginPanel.repaint();
    }

    public JButton getSignInButton() {
        return signInBtn;
    }

    public void showLoginError(boolean show) {
        wrongLogin.setVisible(show);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(getSignInButton())) {
            signInOrRegister();
        }
        else if (e.getSource().equals(registerBtn)) {
            if (getStatus() != LoginStatus.REGISTER_PAGE) {
                setStatus(LoginStatus.REGISTER_PAGE);
                createRegisterPanel();
            } else {
                setStatus(LoginStatus.LOGIN_PAGE);
                createLoginPanel();
            }
        }
    }

    public void signInOrRegister() {
        if (getStatus() == LoginStatus.REGISTER_PAGE) {
            if (!controller.registerProfile(usernameTF.getText(), String.valueOf(passwordTF.getPassword()), String.valueOf(passwordTF1.getPassword()))) {
                showLoginError(true);
            }
        } else  {
            controller.attemptLogin(usernameTF.getText(), String.valueOf(passwordTF.getPassword()));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            signInOrRegister();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
