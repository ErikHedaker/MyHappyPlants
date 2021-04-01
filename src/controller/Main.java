package controller;

import model.*;
import view.PlantList;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Database database = Controller.getInstance().getDatabase();
        JFrame frame = new JFrame("test");
        frame.setSize(550,600);
        frame.setPreferredSize(new Dimension(500,500));

        JPanel panel = new JPanel(new BorderLayout());

        Profile profile = new Profile("Erik", database.getPlantsForProfile( "Erik" ));

        new PlantList(profile.getPlants(), panel);


        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}
