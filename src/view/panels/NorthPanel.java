package view.panels;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NorthPanel extends JPanel {

    private JButton searchBtn;
    private JPanel searchPanel;
    private JTextField searchField;
    private Controller controller;

    public NorthPanel(Controller controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        JPanel panelNorth = new JPanel(new BorderLayout());
        JPanel northHeader = new JPanel(new BorderLayout());
        northHeader.setPreferredSize(new Dimension(1500, 115));
        northHeader.setBackground(new Color(220, 229, 185));
        JLabel logoLabel = new JLabel();
        ImageIcon logo = new ImageIcon("./images/logo-happy.png");
        Image scaledInstance = logo.getImage().getScaledInstance(165, 165, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaledInstance));

        Border border = logoLabel.getBorder();
        Border margin = new EmptyBorder(0, 85, 0, 0);
        logoLabel.setBorder(new CompoundBorder(border, margin));

        northHeader.add(logoLabel, BorderLayout.NORTH);

        JPanel southHeader = new JPanel(new BorderLayout());
        southHeader.setPreferredSize(new Dimension(1500, 50));
        southHeader.setBackground(new Color(173, 193, 124));
        JLabel logoLabel1 = new JLabel();
        logoLabel1.setIcon(new ImageIcon(scaledInstance));
        Border margin1 = new EmptyBorder(0, 85, 115, 0);
        logoLabel1.setBorder(new CompoundBorder(border, margin1));
        southHeader.add(logoLabel1);

        searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(new Color(173, 193, 124));
        searchBtn = new JButton();
        ImageIcon searchIcon = new ImageIcon("./images/search.png");
        Image scaledSearchInstance = searchIcon.getImage().getScaledInstance(47, 45, Image.SCALE_SMOOTH);
        ImageIcon search1Icon = new ImageIcon("./images/search-hover.png");
        Image scaledSearch1Instance = search1Icon.getImage().getScaledInstance(47, 45, Image.SCALE_SMOOTH);

        searchBtn.setIcon(new ImageIcon(scaledSearchInstance));
        searchBtn.setRolloverIcon(new ImageIcon(scaledSearch1Instance));


        searchBtn.setHorizontalTextPosition(2);
        searchBtn.setIconTextGap(20);

        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchBtn.setForeground(Color.white);
        searchBtn.setFont(new Font("Times New Roman", Font.BOLD + Font.PLAIN, 30));
        searchBtn.setBorder(null);
        searchBtn.setBackground(new Color(176, 194, 147));
        searchBtn.addActionListener(new Action());


        searchPanel.add(searchBtn, BorderLayout.EAST);
        searchBtn.setVisible(false);
        Border margin2 = new EmptyBorder(0, 0, 0, 40);
        searchPanel.setBorder(new CompoundBorder(searchPanel.getBorder(), margin2));

        southHeader.add(searchPanel, BorderLayout.EAST);

        panelNorth.add(northHeader, BorderLayout.NORTH);
        panelNorth.add(southHeader, BorderLayout.SOUTH);

        add(panelNorth);
    }

    public JButton getSearchBtn() {
        return searchBtn;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    class Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(searchBtn)) {

                if (searchField == null) {
                    JPanel search = new JPanel();
                    search.setBackground(null);
                    searchField = new JTextField("aloe vera");
                    searchField.setHorizontalAlignment(JTextField.CENTER);
                    searchField.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            controller.buttonPushed("plantList");
                            searchField.setText("");
                        }
                    });
                    searchField.setFont(new Font("Arial", Font.BOLD, 20));
                    searchField.setForeground(Color.gray);
                    Border searchMargin = new EmptyBorder(0, 5, 0, 0);
                    searchBtn.setBorder(new CompoundBorder(searchBtn.getBorder(), searchMargin));

                    searchField.setPreferredSize(new Dimension(200, 40));
                    search.add(searchField);
                    searchPanel.add(search, BorderLayout.WEST);

                } else {
                    controller.buttonPushed("search");
                }
            }
        }
    }
}
