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
    private int x = 330;
    private JButton waterBtn;

    /**
     * Constructs labels for JPanel that tells about watering status.
     * @param plant
     */
    public PlantPanel(Plant plant) {
        this.plant = plant;
        setSize(200,200);
        setPreferredSize(new Dimension(200,200));
        setLayout(new BorderLayout());
        JLabel label = new JLabel( Utility.centerText("Watering Status", 90));
        label.setFont(new Font("Times New Roman", Font.HANGING_BASELINE + Font.BOLD, 17));
        label.setBorder(BorderFactory.createEmptyBorder(0,0,4,0));
        label.setForeground(Color.darkGray);
        JLabel label1 = new JLabel( Utility.centerText("Previous: " + 0 + "d ago", 90)
                + "                     Next: " + plant.getHoursBetweenWatering() + " days left");
        label1.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));
        label1.setForeground(Color.darkGray);
        label1.setFont(new Font("Times New Roman", Font.HANGING_BASELINE + Font.BOLD, 17));

        waterBtn = new JButton("Water");
        waterBtn.setVisible(false);
        add(label, BorderLayout.CENTER);
        add(label1, BorderLayout.SOUTH);
        add(waterBtn, BorderLayout.EAST);
    }

    public void showWaterBtn(boolean visibility) {
        waterBtn.setVisible(visibility);
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
        graphics2D.drawRoundRect(140, 45, 330, 20, 15, 15);
        graphics2D.setColor(new Color(16, 219 - x / 5, 219));

        if (x > 1)
            graphics2D.drawRoundRect(140, 45, x, 20, 15, 15);
        ImageIcon icon = plant.getImageIcon();
        if (icon == null) {
            icon = new ImageIcon("./images/plant.jpg");
        }
        graphics2D.drawImage(icon.getImage(), 05, 05, 105, 100, null);

    }


    class Loading implements Runnable {
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
