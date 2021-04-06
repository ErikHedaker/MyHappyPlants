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
    private JFrame f = new JFrame();

    public LoginPanel(Controller controller) {
        this.controller = controller;
        createLoginPanel();
    }

    public void createLoginPanel() {
        setPreferredSize(new Dimension(1100, 720));
        setBorder(BorderFactory.createEmptyBorder(0, 340, 0, 340));

        JPanel loginPanel = new JPanel(new BorderLayout());

        Border margin1 = new EmptyBorder(270,0,0,0);
        loginPanel.setBorder(new CompoundBorder(loginPanel.getBorder(), margin1));

        JPanel usernamePanel = new JPanel(new BorderLayout());
        JLabel usernameLabel = new JLabel("Username    ");
        usernameLabel.setFont(new Font("Calibri light", Font.PLAIN, 25));
        usernamePanel.add(usernameLabel, BorderLayout.WEST);
        JTextField usernameTF = new JTextField();
        usernameTF.setPreferredSize(new Dimension(150, 30));
        usernamePanel.add(usernameTF, BorderLayout.EAST);
        loginPanel.add(usernamePanel, BorderLayout.NORTH);


        JPanel passwordPanel = new JPanel(new BorderLayout());

        Border margin2 = new EmptyBorder(5,0,0,0);
        passwordPanel.setBorder(new CompoundBorder(passwordPanel.getBorder(), margin2));

        JLabel passwordLabel = new JLabel("Password    ");
        passwordLabel.setFont(new Font("Calibri light", Font.PLAIN, 25));
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        JPasswordField passwordTF = new JPasswordField(0);
        passwordTF.setEchoChar('*');
        passwordTF.setPreferredSize(new Dimension(150, 30));
        passwordPanel.add(passwordTF, BorderLayout.EAST);
        loginPanel.add(passwordPanel, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new BorderLayout());

        Border margin3 = new EmptyBorder(5,0,0,0);
        buttons.setBorder(new CompoundBorder(buttons.getBorder(), margin3));

        signInBtn = new JButton("Sign in");
        signInBtn.setPreferredSize(new Dimension(150, 25));
        signInBtn.addActionListener(new Action());
        buttons.add(signInBtn, BorderLayout.EAST);
        JButton registerBtn = new JButton("Register");
        registerBtn.setPreferredSize(new Dimension(150, 25));
        buttons.add(registerBtn, BorderLayout.WEST);
        loginPanel.add(buttons, BorderLayout.SOUTH);

        add(loginPanel);

    }

    public JButton getSignInButton() {
        return signInBtn;
    }

    public void invalidPasswordMessage(){
        JOptionPane.showMessageDialog(f,"The Password Must Be At Least 6 Characters Long");
    }

    class Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(getSignInButton())) {
                controller.buttonPushed("plantList");
            }
        }
    }
}
