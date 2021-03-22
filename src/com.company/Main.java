package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("test");
        frame.setSize(550,600);
        frame.setPreferredSize(new Dimension(500,500));

        JPanel panel = new JPanel(new BorderLayout());

        JWiki wiki = new JWiki("monstera");
        JWiki wiki2 = new JWiki("aloe vera");
        JWiki wiki3 = new JWiki("cactus");
        JWiki wiki4 = new JWiki("elon musk");

        Plant plant = new Plant(wiki2.getDisplayTitle(), wiki2.getImage(), wiki2.getText());
        plant.setDaysLeft(10);
        Plant plant2 = new Plant(wiki.getDisplayTitle(), wiki.getImage(), wiki.getText());
        Plant plant3 = new Plant(wiki3.getDisplayTitle(), wiki3.getImage(), wiki3.getText());
        plant3.setDaysLeft(0);
        Plant plant4 = new Plant(wiki4.getDisplayTitle(), wiki4.getImage(), wiki4.getText());
        plant4.setDaysLeft(12);

        ArrayList<Plant> flowers = new ArrayList<>();

        flowers.add(plant);
        flowers.add(plant2);
        flowers.add(plant3);
        flowers.add(plant4);

        new PlantList(flowers, panel);


        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}
