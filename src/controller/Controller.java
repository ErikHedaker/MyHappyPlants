package controller;

import com.squareup.okhttp.internal.framed.Settings;
import model.Database;
import model.api.JWiki;
import model.Plant;
import model.Profile;
import model.api.trefle.PlantAPI;
import view.MainFrame;
import view.panels.plant.PlantPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import javax.sound.sampled.*;

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
    public int selectedPlantIndex;
    private ImageIcon imageIcon;
    private String plantSearchInputName;
    private String wikiPlantDescription;
    private String wikiPlantImageURL;

    public Controller() {
        this.database = new Database();
        this.view = new MainFrame(this);
        this.imageDefault = fetchImageFromURL("file:images/plant.jpg");
        this.activeProfile = new Profile().setName("Guest").setPlants(new ArrayList<>());
        imageIcon = new ImageIcon(imageDefault);
        createPlantList();
    }

    public Plant getPlantFromIndex(int index) {
        return getPlantList().get(index);
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setSelectedPlantFromIndex(int plantIndex) {
        selectedPlantIndex = plantIndex;
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
                view.showSearchField();
                new Thread(() -> loadPlantImagesFromDatabase()).start();
                for (PlantPanel panel : view.getPlantList().getPlantPanels()) {
                    if (!panel.getLoadingThread().isAlive()) {
                        panel.getLoadingThread().start();
                    }
                }
                break;
            case "search":
                if (view.getSearchInput().length() > 0) {
                    ArrayList<String> searchResults = database.searchPlant("%" + view.getSearchInput() + "%");

                    try {
                        plantSearchInputName = Utility.getMatchingString(searchResults, view.getSearchInput());
                    } catch (IndexOutOfBoundsException e) {
                    }

                    displayPlantSearchPage();
                    view.setCardLayout("plant page");
                }
                break;
            case "show plant creation page":
                view.setCreationMode(true);
                view.setCardLayout("plant creation page");
                playSound(new File("sounds/WaterDrop.wav"));
                break;
            case "remove plant":
                view.showMessageDialog(true);
                /*if(validPlantIndex(selectedPlantIndex)) {
                    removePlant(activeProfile.getPlants().get(selectedPlantIndex));
                    refreshPlantListGUI();
                    playSound(new File("sounds/GlassBreak1.wav"));
                }*/
                break;
            case "water plant":
                waterPlant(getPlantFromIndex(selectedPlantIndex));
                playSound(new File("sounds/WaterSound.wav"));
                break;
        }
    }

    public void createPlant(String name, String wikiName, String hoursBetweenWatering) {
        Plant plant = new Plant();
        plant.setNameAlias(name);
        plant.setNameWiki(wikiName);
        plant.setHoursBetweenWatering(Utility.getStringToInt(hoursBetweenWatering));
        addPlant(plant);

        JWiki wiki = new JWiki(name);
        byte[] icon = fetchImageFromURL(wiki.getImageURL());

        plant.setImageIcon(new ImageIcon(icon));
        new Thread(() -> database.upsertPlantImage(plant.getDatabaseID(), icon)).start();
        waterPlant(plant);
        refreshPlantListGUI();
        view.setCreationMode(false);
    }

    public void editSelectedPlant(String name, String wikiName, String hoursBetweenWatering) {
        Plant plant = getPlantFromIndex(selectedPlantIndex);
        plant.setNameAlias(name.length() < 1 ? plant.getNameAlias() : name);
        plant.setNameWiki(wikiName.length() < 1 ? plant.getNameWiki() : wikiName);
        plant.setHoursBetweenWatering(Utility.getStringToInt(hoursBetweenWatering));
        database.updatePlant(plant);
        refreshPlantListGUI();
        view.setCreationMode(false);
    }

    public void setCardLayout(String layout) {
        view.setCardLayout(layout);
    }

    public void refreshPlantListGUI()
    {
        createPlantList();
        view.setCardLayout("plantList");
    }

    public boolean validPlantIndex(int index) {
        return index >= 0 && index < activeProfile.getPlants().size();
    }

    public void waterPlant(Plant plant) {
        LocalDateTime date = database.waterPlant(plant.getDatabaseID());
        plant.setLastTimeWatered(date);
        if (view.getPlantList().getPlantPanels().size() > 0) {
            view.updatePlantWateringComponents(selectedPlantIndex);
        }
    }



    public void addPlant(Plant plant) {
        int id = database.insertPlant(activeProfile.getDatabaseID(), plant);
        plant.setDatabaseID(id);
        activeProfile.addPlant(plant);
    }

    public void removePlant( Plant plant ) {
        database.deletePlant(plant.getDatabaseID());
        activeProfile.getPlants().remove(plant);
    }

    public void displayPlantSearchPage() {
        if (plantFound()) {
            showPlantPage(true);
        } else {
            showPlantPage(false);
        }
    }

    public void setCreationMode(boolean creationMode) {
        view.setCreationMode(creationMode);
    }

    public void showPlantPage(boolean isPlantFound) {
        if (isPlantFound) {
            view.showButton(true);
            view.setTitle(plantSearchInputName);
            new Thread(() -> upsertSearchDetails()).start();

        } else  {
            view.setTitle("No plant was found.");
            view.setDescription("");
            view.showButton(false);
            view.setImageLabel(null);
        }
    }

    private void upsertSearchDetails() {
        view.setDescription(wikiPlantDescription);

        try {
            URL wikiImageURL = new URL(wikiPlantImageURL);
            view.setImageLabel(new ImageIcon(wikiImageURL));
        } catch (MalformedURLException e) {
        }
    }

    public boolean plantFound() {
        JWiki wiki = new JWiki(plantSearchInputName);
        if (!(plantSearchInputName == "" || wiki.getText() == null
                || wiki.getText().equalsIgnoreCase("null may refer to:"))) {
            wikiPlantDescription = wiki.getText();
            wikiPlantImageURL = wiki.getImageURL();
            return true;
        }
        return false;
    }

    /**
     * Queries for images in the database, creates ImageIcons and sets them in the list of plants in the active profile
     */
    public synchronized void loadPlantImagesFromDatabase() {
        //FUNKAR INTE >:(
        synchronized (activeProfile.getPlants())
        {
            Iterator<Plant> it = activeProfile.getPlants().iterator();
            while (it.hasNext()) {
                try {
                    Plant plant = it.next();
                    byte[] image = database.getPlantImage(plant.getDatabaseID());
                    plant.setImageIcon(new ImageIcon(image != null ? image : imageDefault));
                } catch (ConcurrentModificationException e) {
                }
            }
        }

        /*
        for (Plant plant : activeProfile.getPlants()) {
            byte[] image = database.getPlantImage(plant.getDatabaseID());
            plant.setImageIcon( new ImageIcon( image != null ? image : imageDefault ) );
        }
        */
    }

    /**
     * Attempts to get an image from the URL, otherwise returns a default image
     *
     * @param imageURL A URL to an image (png, jpg, gif, or similar)
     * @return An image in the byte array format
     */
    public byte[] fetchImageFromURL(String imageURL) {
        if(imageURL !=null) {
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

    public void playSound(File file){
        AudioInputStream as = null;
        try {
            as = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            clip.open(as);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        clip.start();
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
            setCardLayout("signIn");
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