package controller;

import model.Database;
import model.JWiki;
import model.Plant;
import model.Profile;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    private static Controller controller;
    private Database database;

    private Controller() {
        database = new Database();
    }

    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public ArrayList<Plant> getPlantList() {
        Profile profile = database.getProfileByName( "Erik" );
        ArrayList<Plant> plants = profile.getPlants();
        for (Plant plant : plants) {
            JWiki wiki = new JWiki(plant.getNameWiki());
            try {
                plant.setImageIcon(wiki.getImage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return plants;
    }

    public Database getDatabase() {
        return database;
    }
}
