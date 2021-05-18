package view.panels.plant;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class PlantInfoPanel extends JPanel implements ActionListener {

    private JPanel panelInfo;

    public PlantInfoPanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(0, 340, 0, 340));
        setLayout(new BorderLayout());
        ScrollPane scrollPane = new ScrollPane();
        panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,0,3,0);

        Border border = BorderFactory.createLineBorder(Color.lightGray, 1, true);
        panelInfo.setBorder(border);

        ImageIcon logo = new ImageIcon("./images/plants.png");
        ImageIcon scaledLogo = new ImageIcon(logo.getImage().getScaledInstance(361,168, Image.SCALE_SMOOTH));
        JLabel logoLabel = new JLabel(scaledLogo);
        gbc.gridy = 0;
        panelInfo.add(logoLabel, gbc);

        JLabel title = new JLabel("Looks a little empty");
        title.setFont(new Font("Calibri", Font.TRUETYPE_FONT + Font.BOLD, 25));
        gbc.ipady = 70;
        gbc.gridy = 1;
        panelInfo.add(title, gbc);

        JTextArea body = new JTextArea("Search for the plant you want to track to get started.");
        body.setEditable(false);
        body.setFont(new Font("Calibri light", Font.TRUETYPE_FONT, 23));
        gbc.gridy = 2;
        panelInfo.add(body, gbc);

        JButton helpBtn = new JButton("Learn More");
        helpBtn.setBackground(Color.lightGray);
        helpBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        helpBtn.setForeground(Color.BLACK);
        helpBtn.addActionListener(this);
        gbc.gridy = 3;
        gbc.ipadx = 35;
        gbc.ipady = 12;
        panelInfo.add(helpBtn, gbc);

        scrollPane.add(panelInfo);

        add(panelInfo, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Desktop.isDesktopSupported()){
            try{
                File myFile = new File("files/Manual.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex){

            }
        }
    }
}
