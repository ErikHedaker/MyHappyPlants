package view.panels.plant;

import model.Plant;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class PlantList implements PropertyChangeListener {

    private ArrayList<JPanel> panels = new ArrayList<>();
    private JPanel container;
    private PlantPanel plantPanel;
    private ArrayList<PlantPanel> plantPanels = new ArrayList<>();
    private JList list;

    public PlantList(ArrayList<Plant> plants, JPanel container) {
        this.container = container;
        container.setPreferredSize(new Dimension(200,200));
        for (Plant plant : plants) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(Color.white);
            JLabel label = new JLabel((plant.getHoursBetweenWatering() != 0 ? " Happy " : " Sad ") + plant.getNameAlias());
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            label.setFont(new Font("Garamond", Font.PLAIN, 30));
            panel.add(label, BorderLayout.NORTH);

            plantPanel = new PlantPanel(plant);
            plantPanels.add(plantPanel);
            plantPanel.addListener(this);
            panel.add(plantPanel, BorderLayout.CENTER);
            panels.add(panel);

        }
        showItemList(panels, container);
    }

    public ArrayList<PlantPanel> getPlantPanels() {
        return plantPanels;
    }

    private void showItemList(ArrayList<JPanel> paneList, JPanel container) {
        DefaultListModel model = new DefaultListModel();
        container.setBorder(BorderFactory.createEmptyBorder(0, 340, 0, 0));
        container.setBackground(Color.WHITE);

        for (JPanel pane: paneList) {
            model.addElement(pane);
        }
        list = new JList(model);
        list.setBorder(BorderFactory.createLineBorder(Color.white, 30));
        list.setBackground(Color.white);
        list.setFixedCellHeight(150);
        list.setSelectedIndex(-1);

        PanelRenderer p = new PanelRenderer();
        list.setCellRenderer(p);
        JScrollPane scroll1 = new JScrollPane(list);
        scroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        container.add(scroll1);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        container.repaint();
    }

    class PanelRenderer implements ListCellRenderer {

        private JPanel panel;

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            panel = (JPanel) value;
            panel.setBackground(isSelected ? new Color(243, 243, 243) : list.getBackground());
            panel.setBorder(isSelected ? BorderFactory.createLineBorder(new Color(177, 177, 177), 1, true) : BorderFactory.createLineBorder(new Color(243, 243, 243), 1));

            if (isSelected) {

            }

            return panel;
        }
    }
}
