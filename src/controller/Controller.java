package controller;

import model.Database;
import model.api.JWiki;
import model.Plant;
import model.Profile;
import model.api.trefle.PlantAPI;
import view.MainFrame;
import view.panels.plant.PlantPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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
    private ImageIcon imageIcon;
    private String plantSearchInputName;
    private String wikiPlantDescription;

    public Controller() {
        this.database = new Database();
        this.view = new MainFrame(this);
        this.imageDefault = fetchImageFromURL("file:images/plant.jpg");
        imageIcon = new ImageIcon(imageDefault);
        ArrayList<Plant> plants = new ArrayList<>();
        activeProfile = new Profile().setName("Guest").setPlants(plants);
        createPlantList();
    }

    public byte[] getImageDefault() {
        return imageDefault;
    }

    public Plant getPlantFromIndex(int index) {
        return getPlantList().get(index);
    }

    public void setSelectedPlantFromIndex(int plantIndex) {

        Plant plant = getPlantFromIndex(plantIndex);
        view.setSelectedPlantName(plant.getNameAlias());
        ImageIcon imageIcon = plant.getImageIcon();

        if (imageIcon == null) {
            imageIcon = this.imageIcon;
        }

        view.setSelectedImageIcon(imageIcon);
    }

    public void showConnectivityError() {
        view.showConnectivityError();
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
                    if (!panel.getLoadingThread().isAlive()) {
                        panel.getLoadingThread().start();
                    }
                }
                break;
            case "search":
                if (view.getSearchInput().length() > 0) {
                    plantAPI = new PlantAPI(this);

                    plantAPI.beginSearch(view.getSearchInput());
                    plantSearchInputName = plantAPI.getPlantAlias();

                    displayPlantSearchPage();
                    view.setCardLayout("plant page");
                }
                break;
            case "show plant creation page":
                view.setCardLayout("plant creation page");
                break;

            case "add plant":
                /*Plant plant = new Plant();
                plant.setDatabaseID(33).setNameWiki("cactus").setNameAlias("Spiky Boy").setHoursBetweenWatering(4).setLastTimeWatered(LocalDateTime.now());
                database.insertPlant(activeProfile.getDatabaseID(), plant);*/
        }
    }

    public void displayPlantSearchPage() {
        if (plantFound()) {
            showPlantPage(true);
        } else {
            showPlantPage(false);
        }
    }

    public void showPlantPage(boolean isPlantFound) {
        if (isPlantFound) {
            view.showButton(true);
            view.setTitle(plantSearchInputName);
            new Thread(() -> view.setDescription(wikiPlantDescription)).start();
        } else  {
            view.setTitle("No plant was found.");
            view.setDescription("");
            view.showButton(false);
        }
    }

    public boolean plantFound() {
        JWiki wiki = new JWiki(plantSearchInputName);
        if (!(plantSearchInputName == "" || wiki.getText() == null
                || wiki.getText().equalsIgnoreCase("null may refer to:"))) {
            wikiPlantDescription = wiki.getText();
            return true;
        }
        return false;
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
        try {
            hashed = generatePasswordHash(password, profile.getPasswordSalt());
            stored = profile.getPasswordHash();
        }
        catch (NullPointerException e) {
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

    public boolean register(String username, String password, String password1) {
        if (validPassword(password, password1)) {
            activeProfile = createProfile(username, password);
            System.out.println("User " + activeProfile.getName() + " was created.");
            createPlantList();
            buttonPushed("plantList");
            return true;
        }
        return false;
    }

    public void attemptLogin(String username, String password) {
        activeProfile = profileLogin(username, password);
        if (activeProfile == null) {
            view.showLoginError(true);
            return;
        }

        view.showLoginError(false);
        createPlantList();
        buttonPushed("plantList");
    }


    public boolean validPassword(String password, String password1) {
        if(password.length() > 4 && password.equals(password1)) {
            return true;
        }
        return false;
    }

}
