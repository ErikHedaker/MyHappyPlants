package view.panels;

import view.Utilities;
import model.Plant;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PlantPanel extends JPanel {

    private Plant plant;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public PlantPanel(Plant plant) {
        this.plant = plant;
        setLayout(new GridLayout(2,1,0,0));

        JLabel label = new JLabel(Utilities.centerText("Watering Status", 0));
        label.setFont(new Font("Times New Roman", Font.HANGING_BASELINE + Font.BOLD, 17));

        JLabel label1 = new JLabel(Utilities.centerText("Previous: " + 0 + "d ago", 0)
                + "                     Next: " + plant.getHoursBetweenWatering() + " days left");
        label1.setFont(new Font("Times New Roman", Font.HANGING_BASELINE + Font.BOLD, 17));

        label.setBackground(null);
        add(label);
        add(label1);
        new Inner().start();
    }

    public void addListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    int x = 320;

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.drawRoundRect(120, 45, 320, 20, 15, 15);
        graphics2D.setColor(new Color(16, 219 - x / 5, 219));

        if (x > 1)
            graphics2D.drawRoundRect(120, 45, x, 20, 15, 15);
        ImageIcon icon = plant.getImageIcon();
        if (icon == null) {
            icon = new ImageIcon("files/icon.jpg");
        }
        graphics2D.drawImage(icon.getImage(), 05, 05, 105, 100, null);

    }


    class Inner extends Thread {
        @Override
        public void run() {
            while (true) {
                int daysLeft = plant.getHoursBetweenWatering();
                if (x > ((daysLeft) * (plant.getHoursBetweenWatering() == 0 ? 20 : 5/*plant.getDaysWithoutWater()*/)))
                    x--;

                repaint();
                propertyChangeSupport.firePropertyChange("update", null, null);
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
