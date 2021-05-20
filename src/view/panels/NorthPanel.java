package view.panels;

import controller.Controller;
import view.dialog.ConfirmationDialog;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class NorthPanel extends JPanel implements ActionListener, KeyListener {

    private JButton searchBtn;
    private JTextField searchField;
    private Controller controller;
    private JPanel searchPanel;
    private JPanel search;
    private ConfirmationDialog dialog;

    public NorthPanel(Controller controller) {
        this.controller = controller;
        this.dialog = new ConfirmationDialog(controller);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1500, 150));

        searchPanel = new JPanel(new BorderLayout());
        searchBtn = new JButton();
        ImageIcon searchIcon = new ImageIcon("./images/search.png");
        Image scaledSearchInstance = searchIcon.getImage().getScaledInstance(47, 45, Image.SCALE_SMOOTH);
        ImageIcon search1Icon = new ImageIcon("./images/search-hover.png");
        Image scaledSearch1Instance = search1Icon.getImage().getScaledInstance(47, 45, Image.SCALE_SMOOTH);

        searchBtn.setIcon(new ImageIcon(scaledSearchInstance));
        searchBtn.setRolloverIcon(new ImageIcon(scaledSearch1Instance));


        searchBtn.setHorizontalTextPosition(2);
        searchBtn.setIconTextGap(20);
        searchBtn.setFocusable(false);
        searchBtn.setContentAreaFilled(false);
        searchBtn.setOpaque(true);

        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchBtn.setForeground(Color.white);
        searchBtn.setFont(new Font("Times New Roman", Font.BOLD + Font.PLAIN, 30));
        searchBtn.setBorder(null);
        searchBtn.setBackground(new Color(176, 194, 147));
        searchBtn.addActionListener(this);
        searchBtn.setVisible(false);

        searchField = new JTextField("tomato");
        searchField.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        search = new JPanel();
        search.setBackground(new Color(173, 193, 124));

        searchField.setBorder(BorderFactory.createMatteBorder(1,1,3,1, new Color(93, 118, 77)));
        searchField.setFont(new Font("Arial", Font.BOLD, 20));
        searchField.setForeground(new Color(93, 118, 77));
        searchField.addKeyListener(this);
        searchField.setPreferredSize(new Dimension(200, 40));
        searchField.setBackground(new Color(173, 193, 124));

        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                searchField.setBackground(Color.WHITE);
                controller.buttonPushed("plantList");
                searchField.setText("");
            }
        });
        search.setVisible(false);
        search.add(searchField, BorderLayout.WEST);

        searchPanel.add(search, BorderLayout.WEST);

        Border margin2 = new EmptyBorder(100, 0, 0, 40);
        setBorder(new CompoundBorder(getBorder(), margin2));

        searchPanel.add(searchBtn, BorderLayout.EAST);

        add(searchPanel, BorderLayout.EAST);
    }

    public JButton getSearchBtn() {
        return searchBtn;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public void showSearch() {
        search.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.setColor(new Color(220, 229, 185));
        graphics2D.fillRect(0,0, 4000, 100);

        graphics2D.setColor(new Color(173, 193, 124));
        graphics2D.fillRect(0,100, 4000, 50);

        ImageIcon logo = new ImageIcon("./images/logo-happy.png");
        Image img = logo.getImage().getScaledInstance(150, 150,
                        Image.SCALE_SMOOTH);
        graphics2D.drawImage(new ImageIcon(img).getImage(), 50, 0, 150, 150, null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(searchBtn)) {
            if (!searchField.getBackground().equals(Color.WHITE)) {
                searchField.setBackground(Color.WHITE);
            } else {
                new Thread(() -> controller.buttonPushed("search")).start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            new Thread(() -> controller.buttonPushed("search")).start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
