package view.panels.profile;

import model.Profile;
import view.dialog.ConfirmationDialog;
import view.dialog.DialogType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import controller.Controller;
import java.awt.event.ActionListener;


public class EditProfile extends JPanel implements ActionListener{
    private Profile profile;
    private Controller controller;
    private JLabel title;
    private JButton btnSave, editBackButton, pictureBtn;
    private JPasswordField passwordTF;
    private JTextField usernameTF;
    private JPanel editProfilePanel;

    public EditProfile(Controller controller) {
        this.controller = controller;
        this.profile = new Profile();
        createEditProfilePanel();
    }

    public void createEditProfilePanel(){
        if( editProfilePanel != null)  editProfilePanel.setVisible(false);

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
        editProfilePanel.add(editBackButton, gbc);

        editProfilePanel.setBackground(Color.white);
        title = new JLabel("Edit Profile");
        title.setFont(new Font("Calibri", Font.BOLD, 25));
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.gridwidth = 110;

        gbc.anchor = GridBagConstraints.PAGE_START;
        editProfilePanel.add(title, gbc);

        JLabel lblName = new JLabel("New Name: ");
        lblName.setFont(new Font("Calibri light", Font.PLAIN, 22));
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
        lblPassword.setFont(new Font("Calibri light", Font.PLAIN, 22));
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.weighty = 250;
        gbc.anchor = GridBagConstraints.WEST;
        editProfilePanel.add(lblPassword, gbc);

        passwordTF = new JPasswordField();
        passwordTF.setPreferredSize(new Dimension(100,20));
        passwordTF.setBackground(Color.white);
        passwordTF.setEchoChar('*');
        gbc.gridx = 8;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        editProfilePanel.add(passwordTF, gbc);


        JLabel lblPic = new JLabel("New Picture: ");
        lblPic.setFont(new Font("Calibri light", Font.PLAIN, 22));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        editProfilePanel.add(lblPic, gbc);

        pictureBtn = new JButton();
        pictureBtn.setPreferredSize(new Dimension(30,60));
        pictureBtn.setBackground(Color.white);
        pictureBtn.setIcon(new ImageIcon("./images/defpic.jpg"));
        gbc.gridx = 8;
        gbc.gridy = 5;
        gbc.ipady = 70;
        gbc.ipadx = 60;
        gbc.anchor = GridBagConstraints.EAST;
        editProfilePanel.add(pictureBtn, gbc);

        btnSave = new JButton();

        btnSave.setPreferredSize(new Dimension(100,20));
        btnSave.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ImageIcon saveImg = new ImageIcon("./images/save-icon.png");
        Image scaledEditImage = saveImg.getImage().getScaledInstance(25, 20,
                Image.SCALE_AREA_AVERAGING);

        btnSave.setLayout(new FlowLayout(FlowLayout.LEADING));
        JLabel iconLabel = new JLabel(new ImageIcon(scaledEditImage));
        JLabel textLabel = new JLabel("SAVE");
        btnSave.add(iconLabel);
        btnSave.add(textLabel);
        textLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        btnSave.setBackground(Color.white);
        btnSave.addActionListener(this);
        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.ipadx = 55;
        gbc.ipady = 10;
        gbc.anchor = GridBagConstraints.PAGE_END;
        editProfilePanel.add(btnSave, gbc);



        add(editProfilePanel);

    }

    public void setImage(ImageIcon imageIcon) {
        if (imageIcon == null) {
            imageIcon = new ImageIcon("./images/defpic.jpg");
        }
        Image img = imageIcon.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH);


        pictureBtn.setIcon(new ImageIcon(img));
    }

    public void setUsernameTF(String text) {
        this.usernameTF.setText(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editBackButton) {
            new ConfirmationDialog(controller)
                    .setConfirmationMessage("Are you sure you want to exit (your edits will not be saved)?")
                    .showConfirmationDialog(DialogType.PROCEED_BACK_PROFILE_CONFIRMATION_DIALOG);
        } else if (e.getSource() == btnSave) {
            //controller.updateProfile(usernameTF.getText(), String.valueOf(passwordTF.getPassword()),
                    //(ImageIcon) pictureBtn.getIcon());{

            //}
            if (e.getSource() == pictureBtn) {
                System.out.println("clicky");
                controller.buttonPushed("change profile image");
            }

        }


    }
}
