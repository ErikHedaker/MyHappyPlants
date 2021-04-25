package controller;

import model.Database;
import model.api.JWiki;
import model.Plant;
import model.Profile;
import model.api.trefle.PlantAPI;
import view.MainFrame;
import view.panels.PlantPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static controller.Utility.*;

/**
 * The Controller class handles the relation between the view (Swing frame) and the model (Database and other classes),
 * and does necessary calculations that fall outside of a specific model's area of expertise.
 *
 * @author Viktor Johansson, Erik Hedåker
 */
public class Controller {
    private Database database;
    private Profile activeProfile;
    private MainFrame view;
    private PlantAPI plantAPI;
    private byte[] imageDefault;

    public Controller() {
        this.database = new Database();
        this.view = new MainFrame(this);
        this.imageDefault = fetchImageFromURL("file:images/plant.jpg");
        this.activeProfile = new Profile().setName("Guest").setPlants(new ArrayList<>());
        createPlantList();
    }

    /**
     * Getter method for the active profile's list of plants
     *
     * @return A list of plants
     */
    public ArrayList<Plant> getPlantList() {
        return activeProfile.getPlants();
    }

    public void createPlantList() {
        view.createPlantList();
    }

    public void buttonPushed(String button) {
        switch (button) {
            case "signIn":
                view.setCardLayout("signIn");
                break;
            case "plantList":
                view.setCardLayout("plantList");
                view.showSearch(true);
                new Thread(() -> loadPlantImagesFromDatabase()).start();
                for (PlantPanel panel : view.getPlantList().getPlantPanels()) {
                    panel.getLoadingThread().start();
                }
                break;
            case "search":
                if (view.getSearch().length() > 2) {
                    plantAPI = new PlantAPI(view.getSearch());
                    plantAPI.start();
                    view.setCardLayout("show plant page");
                    view.setTitle(plantAPI.getPlantAlias());
                }
                break;
            case "Add Plant":
                Plant plant = new Plant()
                    .setImageIcon(new ImageIcon(imageDefault))
                    .setNameAlias("Temp")
                    .setNameWiki("Rose")
                    .setHoursBetweenWatering(10);
                addPlant(plant);
                System.out.println("Added plant");
                System.out.println(plant);
                System.out.println("Doesn't refresh the GUI :(");
                break;
            case "Remove Plant":
                System.out.println("Remove Plant");
                break;
            case "Water Plant":
                System.out.println("Water Plant");
                break;
        }
    }

    public void addPlant( Plant plant ) {
        int id = database.insertPlant(activeProfile.getDatabaseID(), plant);
        plant.setDatabaseID(id);
        activeProfile.addPlant(plant);
    }

    public void setImage(String strURL) {

    }

    public PlantAPI getPlantAPI() {
        return plantAPI;
    }

    /**
     * Fetches images from Wikipedia with JWiki and upsert them into the database
     */
    public void upsertPlantImagesFromWikipediaToDatabase() {
        for (Plant plant : activeProfile.getPlants()) {
            JWiki wiki = new JWiki(plant.getNameWiki());
            database.upsertPlantImage(plant.getDatabaseID(), fetchImageFromURL(wiki.getImageURL()));
        }
    }

    /**
     * Queries for images in the database, creates ImageIcons and sets them in the list of plants in the active profile
     */
    public void loadPlantImagesFromDatabase() {
        for (Plant plant : activeProfile.getPlants()) {
            byte[] image = database.getPlantImage(plant.getDatabaseID());
            plant.setImageIcon( new ImageIcon( image != null ? image : imageDefault ) );
        }
    }

    /**
     * Attempts to get an image from the URL, otherwise returns a default image
     *
     * @param imageURL A URL to an image (png, jpg, gif, or similar)
     * @return An image in the byte array format
     */
    public byte[] fetchImageFromURL(String imageURL) {
        if (imageURL != null) {
            try (ByteArrayOutputStream baoStream = new ByteArrayOutputStream()) {
                String extension = imageURL.substring(imageURL.lastIndexOf(".") + 1);
                ImageIO.write(ImageIO.read(new URL(imageURL)), extension, baoStream);
                baoStream.flush();
                return baoStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageDefault;
    }

    /**
     * Attempts to login the user by getting the profile from the database,
     * creating a password hash from the inputted password and matching the stored password hash
     *
     * @param name A string representing the profile name that the user want to login with
     * @param password A string representing the password that the user want to login with
     * @return A Profile if the login attempt is successful, null if not
     */
    public Profile loginProfile(String name, String password) {
        Profile profile = database.getProfile(name);
        byte[] hashed;
        byte[] stored;
        try {
            hashed = generatePasswordHash(password, profile.getPasswordSalt());
            stored = profile.getPasswordHash();
        } catch (NullPointerException e) {
            return null;
        }
        return Arrays.equals(hashed, stored) ? profile : null;
    }

    /**
     * Creates a new profile based on the name and password
     *
     * @param name A string representing the profile name that the user want to use
     * @param password A string representing the password that the user want to use
     * @return A Profile if the name is not already used, null if it is
     */
    public Profile createProfile(String name, String password) {
        byte[] salt = generateRandomSalt(20);
        Profile profile = new Profile()
            .setName(name)
            .setPasswordHash(generatePasswordHash(password, salt))
            .setPasswordSalt(salt);
        int id = database.insertProfile(profile);
        profile.setDatabaseID(id);
        return id != -1 ? profile : null;
    }

    public boolean registerProfile(String username, String password, String passwordRepeat) {
        if (validPassword(password, passwordRepeat)) {
            activeProfile = createProfile(username, password);
            System.out.println("User " + activeProfile.getName() + " was created.");
            createPlantList();
            buttonPushed("plantList");
            return true;
        }
        return false;
    }

    public void attemptLogin(String username, String password) {
        activeProfile = loginProfile(username, password);
        if (activeProfile == null) {
            view.showLoginError(true);
            return;
        }

        view.showLoginError(false);
        createPlantList();
        buttonPushed("plantList");
    }

    public boolean validPassword(String password, String passwordRepeat) {
        return password.length() > 4 && password.equals(passwordRepeat);
    }
}
