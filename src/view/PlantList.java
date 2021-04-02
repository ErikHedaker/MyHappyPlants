package view;

import model.Plant;
import view.panels.PlantPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class PlantList implements PropertyChangeListener {

    private ArrayList<JPanel> panels = new ArrayList<>();
    private JPanel container;

    public PlantList(ArrayList<Plant> plants, JPanel container) {
        this.container = container;
        for (Plant plant : plants) {
            JPanel panel = new JPanel(new GridLayout(2,1,0,0));
            panel.setBackground(Color.white);
            JLabel label = new JLabel((plant.getHoursBetweenWatering() != 0 ? " Happy " : " Sad ") + plant.getNameAlias());
            label.setFont(new Font("Garamond", Font.PLAIN, 30));
            Border margin = new EmptyBorder(0,115,0,0);
            label.setBorder(new CompoundBorder(label.getBorder(), margin));
            panel.add(label);

            PlantPanel flowerPanel = new PlantPanel(plant);
            flowerPanel.addListener(this);

            panel.add(flowerPanel);
            panels.add(panel);
            showItemList(panels, container);
        }
    }

    private void showItemList(ArrayList<JPanel> paneList, JPanel container) {
        DefaultListModel model = new DefaultListModel();
        container.setPreferredSize(new Dimension(1100, 720));
        container.setBorder(BorderFactory.createEmptyBorder(0, 340, 0, 340));
        container.setBackground(Color.WHITE);
        for (JPanel pane: paneList) {
            pane.setBorder(BorderFactory.createLineBorder(new Color(243, 243, 243), 1));
            model.addElement(pane);

        }
        JList list = new JList(model);
        list.setBorder(BorderFactory.createLineBorder(Color.white, 30));
        list.setBackground(Color.white);
        list.setFixedCellHeight(205);
        list.setSelectedIndex(-1);

        list.setCellRenderer(new PanelRenderer());
        JScrollPane scroll1 = new JScrollPane(list);

        container.add(scroll1);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        container.repaint();
    }

    class PanelRenderer implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JPanel renderer = (JPanel) value;
            renderer.setBackground(isSelected ? new Color(243, 243, 243) : list.getBackground());
            renderer.setBorder(isSelected ? BorderFactory.createLineBorder(new Color(177, 177, 177), 1, true) : BorderFactory.createLineBorder(new Color(243, 243, 243), 1));
            return renderer;
        }
    }
}
