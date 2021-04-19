package controller;

import model.Database;
import model.api.JWiki;
import model.Plant;
import model.Profile;
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
    private byte[] imageDefault;

    public Controller() {
        this.database = new Database();
        this.view = new MainFrame(this);
        this.imageDefault = fetchImageFromURL("file:images/plant.jpg");
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
                new Thread(() -> loadPlantImagesFromDatabase()).start();
                for (PlantPanel panel : view.getPlantList().getPlantPanels()) {
                    panel.getLoadingThread().start();
                }
                break;
            case "loading-screen":
                view.setCardLayout("loading-screen");
                break;
        }
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
    public Profile profileLogin(String name, String password) {
        Profile profile = database.getProfile(name);
        byte[] hashed;
        byte[] stored;
        try
        {
            hashed = generatePasswordHash(password, profile.getPasswordSalt());
            stored = profile.getPasswordHash();
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
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

    public void loginAttempt(String username, String password) {
        activeProfile = profileLogin(username, password);
        if (activeProfile != null) {
            createPlantList();
            view.showLoginError(false);
            buttonPushed("plantList");
        } else {
            view.showLoginError(true);
        }
    }

    /*
    public boolean invalidPassword() {
        if(activeProfile.getPassword().length() < validP) {
            //l.invalidPasswordMessage();
            return false;
        }
    }
    */
}
