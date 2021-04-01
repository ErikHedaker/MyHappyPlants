package myhappyplants.view;

import myhappyplants.model.Plant;
import myhappyplants.view.panels.PlantPanel;

import javax.swing.*;
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
            label.setFont(new Font("Times New Roman", Font.BOLD + Font.HANGING_BASELINE, 30));
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

        for (JPanel pane: paneList) {
            pane.setBorder(BorderFactory.createLineBorder(new Color(243, 243, 243), 1));
            model.addElement(pane);

        }
        JList list = new JList(model);
        list.setBorder(BorderFactory.createLineBorder(new Color(243, 243, 243), 30));
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
