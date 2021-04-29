package view;

import controller.Controller;
import view.menus.MenuBar;
import view.menus.PopupMenu;
import view.panels.MainPanel;
import view.panels.plant.PlantList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame{
    private Controller controllerRef;
    private MainPanel panel;
    private view.menus.PopupMenu popupMenu;

    public MainFrame( Controller controllerRef ) {
        this.controllerRef = controllerRef;
        setupFrame();
    }

    public void setupFrame() {
        JFrame frame = new JFrame("MyHappyPlants");

        panel = new MainPanel(controllerRef);
        popupMenu = new PopupMenu();

        frame.setJMenuBar(new MenuBar());
        //frame.add(popupMenu);
        frame.setLayout(new BorderLayout());
        //frame.setSize(1500, 820);

        JLabel logoLabel = new JLabel();
        ImageIcon logo = new ImageIcon("./images/logo-happy.png");
        Image scaledInstance = logo.getImage().getScaledInstance(165, 165, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaledInstance));
        logoLabel.setOpaque(false);



        frame.add(panel.getNorthPanel(), BorderLayout.NORTH);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeWidth = (dim.width +800)/ 2;
        int sizeHeight = (dim.height + 700)/ 2;
        panel.setPreferredSize(new Dimension((sizeWidth + 300)/2, (sizeWidth + 300)/2));
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        frame.setSize(sizeWidth, sizeHeight);
        frame.setLocationRelativeTo(null);
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                maybeShowPopup(e);
            }
            public void mouseReleased(MouseEvent e){
                maybeShowPopup(e);
            }
            private void maybeShowPopup(MouseEvent e){
                if(e.isPopupTrigger()){
                    popupMenu.show(e.getComponent(),e.getX(),e.getY());
                }
            }
        });
        frame.add(panel.getSouthPanel(), BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public static void main(String[] args) {

        // JLayeredPane = Swing container that provides a
        //    third dimension for positioning components
        //    ex. depth, Z-index

        JLabel label1= new JLabel();
        label1.setOpaque(true);
        label1.setBackground(Color.RED);
        label1.setBounds(50,50,200,200);

        JLabel label2= new JLabel();
        label2.setOpaque(true);
        label2.setBackground(Color.GREEN);
        label2.setBounds(100,100,200,200);

        JLabel label3= new JLabel();
        label3.setOpaque(true);
        label3.setBackground(Color.BLUE);
        label3.setBounds(150,150,200,200);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0,0,500,500);

        //layeredPane.add(label1, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(label1, Integer.valueOf(0));
        layeredPane.add(label2, Integer.valueOf(2));
        layeredPane.add(label3, Integer.valueOf(1));

        JFrame frame = new JFrame("JLayeredPane");
        frame.add(layeredPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 500));
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void setCardLayout(String cardLayout) {
        panel.setCardLayout(cardLayout);
    }

    public PlantList getPlantList() {
        return panel.getPlantList();
    }

    public void showLoginError(boolean show) {
        panel.showLoginError(show);
    }

    public void createPlantList() {
        panel.createPlantList();
    }

    public void setTitle(String title) {
        panel.setTitle(title);
    }

    public void showButton(boolean show) {
        panel.showButton(show);
    }

    public void setDescription(String txt) {
        panel.setDescription(txt);
    }

    public void setImage(ImageIcon icon) {
        panel.setImage(icon);
    }

    public void showSearch(boolean show) {
        panel.showSearchBtn(show);
    }

    public String getSearchInput() {
        return panel.getSearchInput();
    }
}
