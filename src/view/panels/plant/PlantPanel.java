package view.panels.plant;

import controller.Utility;
import model.Plant;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The PlantPanel class handles the visual elements of plants, this class is made to be multiplied into multiple instances in a list for each plant.
 * @author Viktor Johansson
 */

public class PlantPanel extends JPanel {

    private Plant plant;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private Thread loadingThread = new Thread(new Loading());
    private int startPosX = 365;
    private JLabel previousWaterLabel, nextWaterLabel;

    /**
     * Constructs labels for JPanel that tells about watering status.
     * @param plant
     */
    public PlantPanel(Plant plant) {
        this.plant = plant;
        setPreferredSize(new Dimension(200,200));
        setLayout(new BorderLayout());

        JPanel waterStatusPanel = new JPanel(new BorderLayout());
        waterStatusPanel.setOpaque(false);
        previousWaterLabel = new JLabel("Last time was: " + plant.getLastTimeWateredInterval() + " day(s) ago");
        previousWaterLabel.setFont(new Font("Calibri Light", Font.PLAIN, 18));

        nextWaterLabel = new JLabel(Utility.getPlantMoodStr(plant.getTimeRemaining()));
        nextWaterLabel.setFont(new Font("Calibri Light", Font.PLAIN, 18));

        nextWaterLabel.setBorder(BorderFactory.createEmptyBorder(0,205,15,0));

        waterStatusPanel.add(nextWaterLabel, BorderLayout.NORTH);
        waterStatusPanel.add(previousWaterLabel, BorderLayout.SOUTH);
        waterStatusPanel.setBorder(BorderFactory.createEmptyBorder(0,140,35,0));


        add(waterStatusPanel, BorderLayout.SOUTH);
    }

    public void updateWateringComponents() {
        nextWaterLabel.setText("Water me in: " + plant.getTimeRemaining() + " day(s)");
        previousWaterLabel.setText("Last time was: " + plant.getLastTimeWateredInterval() + " day(s) ago");
    }

    /**
     * @return returns the loading thread so that it can get accessed from other classes.
     */
    public Thread getLoadingThread() {
        return loadingThread;
    }

    /**
     * Adds a PropertyChangeListener class into PropertyChangeSupport.
     * @param listener
     */
    public void addListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Draws the loadingbar and plant image.
     * @param g used for drawing.
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.setColor(Color.darkGray);
        graphics2D.drawRoundRect(140, 50, 365, 20, 15, 15);
        graphics2D.setColor(new Color(16, startPosX/2, 219));

        if (startPosX > 1) {
            graphics2D.drawRoundRect(140, 50, startPosX, 20, 15, 15);
        }
        ImageIcon icon = plant.getImageIcon();
        if (icon == null) {
            icon = new ImageIcon("./images/plant.jpg");
        }
        graphics2D.drawImage(icon.getImage(), 17, 10, 105, 100, null);
    }


    class Loading implements Runnable {
        @Override
        public void run() {
            while (!Thread.interrupted()) {

                long daysLeft = plant.getTimeRemaining() < 0 ? 0 : plant.getTimeRemaining();
                int maxDays = plant.getDaysBetweenWatering() < 0 ? 0 : plant.getDaysBetweenWatering();

                double scale = (365 / maxDays) * daysLeft;

                boolean increasePos = startPosX <= scale;
                boolean decreasePos = scale + 140 >= 140 && startPosX != scale;

                //if (!(increasePos && decreasePos)) {
                if (daysLeft == maxDays) {
                    startPosX = 365;
                } else if (increasePos) {
                        startPosX++;
                    } else if (decreasePos) {
                        startPosX--;
                    }
                   // startPosX = (int) scale;
                //}
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
