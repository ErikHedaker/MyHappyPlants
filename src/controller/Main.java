package controller;

import model.*;
import view.PlantList;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Controller controller = Controller.getInstance();
        JFrame frame = new JFrame("test");
        frame.setSize(550,600);
        frame.setPreferredSize(new Dimension(500,500));

        JPanel panel = new JPanel(new BorderLayout());

        new PlantList(controller.getPlantList(), panel);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}
