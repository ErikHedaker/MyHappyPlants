package controller;

import model.*;
import model.api.JWiki;
import view.MainFrame;
import view.dialog.MessageDialog;
import view.panels.plant.PlantPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import javax.sound.sampled.*;

import static controller.Utility.*;

/**
 * The Controller class handles the relation between the view (Swing frame) and the model (Database and other classes),
 * and does necessary calculations that fall outside of a specific model's area of expertise.
 *
 * @author Viktor Johansson, Erik Hedåker, Fanny Rosdahl Rosenglim (written in method ButtonPushed)
 */
public class Controller {
    private Database database;
    private Profile activeProfile;
    private MainFrame view;
    private byte[] imageDefault;
    public int selectedPlantIndex;
    private String plantSearchInputName;
    private byte[] plantSearchImage;
    private boolean isUserReminded;

    /**
     * Constructor which initializes the database with the included encrypted file, initializes the Java Swing GUI,
     * loads the included default image, and creates a new guest account with default values
     */
    public Controller() {
        this.database = new Database(new SimpleEncryption().readFile());
        this.view = new MainFrame(this);
        this.imageDefault = fetchImageFromURL("file:images/plant.jpg");
        activeUserDefault();
        //createPlantList();
        //createUserProfile();
    }

    /**
     * Returns the plant which the user has clicked and selected
     *
     * @param index Index of the selected plant
     * @return The selected plant, or null if no plants exists
     */
    public Plant getPlantFromIndex(int index) {
        try {
            return getPlantList().get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }


    public void activeUserDefault() {
        this.activeProfile = new Profile()
                .setName("Guest")
                .setPlants(new ArrayList<>())
                .setImageIcon(new ImageIcon(imageDefault));
    }

    public void setSelectedPlantFromIndex(int plantIndex) {
        selectedPlantIndex = plantIndex;
        Plant plant = getPlantFromIndex(plantIndex);
        view.setSelectedPlantName(plant.getNameWiki());
        ImageIcon imageIcon = plant.getImageIcon();

        view.setSelectedImageIcon(imageIcon);
    }

    /*public void setSelectedPic() {
        ImageIcon imageIcon = activeProfile.getImageIcon();

        view.setImage(imageIcon);
    }*/

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

    /**
     * Creates a list of GUI elements for the existing plants
     */
    public void createPlantList() {
        view.createPlantList();
    }

    /*public void createUserProfile(){
        view.createUserProfile();
    }*/

    /**
     * Executes one of the available codeblocks for the assigned button
     *
     * @param button The codename of the corresponding button functionality
     */
    public void buttonPushed(String button) {
        switch (button) {
            case "signIn":
                view.setCardLayout("signIn");
                view.resetInputFields();
                break;
            case "plantList":
                if (view.getPlantList().getPlantPanels().size() < 1) {
                    view.setCardLayout("welcome info");
                } else {
                    for (PlantPanel panel : view.getPlantList().getPlantPanels()) {
                        if (!panel.getLoadingThread().isAlive()) {
                            panel.getLoadingThread().start();
                        }
                    }
                    view.setCardLayout("plantList");

                }
                view.setProfile(" " + activeProfile.getName() + "     ");
                view.showSearch(true);
                view.showSearchField();
                sendReminderMessage();
                break;
            case "search":
                if (view.getSearchInput().length() > 0) {
                    view.setCardLayout("loading-screen");
                    ArrayList<HashMap<String, String>> searchResult = database.searchPlant("%" + view.getSearchInput() + "%");
                    if (!searchResult.isEmpty()) {
                        HashMap<String, String> plant = searchResult.get(0);
                        String[] scientificNameWords = plant.get("scientific_name").split(" ");
                        JWiki wiki = new JWiki(plant.get("common_name"));
                        wiki = wiki.valid() ? wiki : new JWiki(plant.get("scientific_name"));
                        wiki = wiki.valid() ? wiki : new JWiki(scientificNameWords[0] + " " + scientificNameWords[1]);
                        wiki = wiki.valid() ? wiki : new JWiki(scientificNameWords[0]);
                        wiki = wiki.valid() ? wiki : new JWiki(plant.get("url_wikipedia_en").substring(plant.get("url_wikipedia_en").lastIndexOf("/") + 1));
                        plantSearchImage = fetchImageFromURL(wiki.getImageURL());
                        /*
                        if (plantSearchImage == imageDefault ) {
                            plantSearchImage = fetchImageFromURL(plant.get("image_url"));
                        }
                        */
                        view.setImageLabel(new ImageIcon(plantSearchImage));
                        view.showButton(true);
                        if (plant.get("common_name") != null) {
                            view.setTitle(plant.get("common_name") + " (" + plant.get("scientific_name") + ")");
                        } else {
                            view.setTitle(plant.get("scientific_name"));
                        }
                        view.setDescription(wiki.getText());
                        plantSearchInputName = wiki.getDisplayTitle().toLowerCase();
                    } else {
                        view.setTitle("No plant was found.");
                        view.setDescription("");
                        view.showButton(false);
                        view.setImageLabel(null);
                    }
                    view.setCardLayout("plant page");
                }
                break;
            case "show plant creation page":

                view.setNicknameTF("");
                view.setWaterTF("");

                if (!view.isCreationMode()) {
                    Plant plant = getPlantFromIndex(selectedPlantIndex);
                    view.setNicknameTF(plant.getNameAlias());
                    view.setWaterTF(String.valueOf(plant.getDaysBetweenWatering()));
                }

                view.setCardLayout("plant creation page");
                playSound(new File("sounds/WaterDrop.wav"));
                break;
            case "remove plant":
                if (validPlantIndex(selectedPlantIndex)) {
                    Plant plant = getPlantFromIndex(selectedPlantIndex);
                    activeProfile.getPlants().remove(plant);
                    refreshPlantListGUI();
                    playSound(new File("sounds/GlassBreak1.wav"));
                    new Thread(() -> database.deletePlant(plant.getDatabaseID())).start();
                }
                break;
            case "water plant":
                waterPlant(getPlantFromIndex(selectedPlantIndex));
                playSound(new File("sounds/WaterSound.wav"));
                break;
            case "change plant image":
                File filePlantImage = Utility.OpenFileChooser();
                if( filePlantImage == null ) {
                    break;
                }
                byte[] imagePlant = fetchImageFromURL("file:" + filePlantImage.getAbsolutePath());
                if (imagePlant == imageDefault) {
                    break;
                }
                Plant plant = getPlantFromIndex(selectedPlantIndex);
                plant.setImageIcon(new ImageIcon(imagePlant));
                database.upsertPlantImage(plant.getDatabaseID(), imagePlant);
                refreshPlantListGUI();
                break;
            case "change profile image":
                File fileProfileImage = Utility.OpenFileChooser();
                if( fileProfileImage == null ) {
                    break;
                }
                byte[] imageProfile = fetchImageFromURL("file:" + fileProfileImage.getAbsolutePath());
                if (imageProfile == imageDefault) {
                    break;
                }
                activeProfile.setImageIcon(new ImageIcon(imageProfile));
                database.upsertProfileImage(activeProfile.getDatabaseID(), imageProfile);
                break;
            case "rank page":
                view.setCardLayout("rank page");
                break;
            case "profile page":
                view.setCardLayout("profile page");
                view.setUserNameTF(activeProfile.getName());
                break;
            case "edit profile page":
                view.setCardLayout("edit profile page");
                view.setUserNameTF(activeProfile.getName());
                break;
        }
    }

    /**
     * Searches and creates available search terms in the trefle-api data, to be shown in the dropdown search bar
     *
     * @return A list of existing search terms
     */
    public synchronized ArrayList<String> getResultsArray() {
        ArrayList<HashMap<String, String>> searchResult = database.searchPlant("%" + view.getSearchInput() + "%", 15);
        ArrayList<String> results = new ArrayList<>();
        for (HashMap<String, String> plant : searchResult) {
            if (plant.get("common_name") != null) {
                results.add(plant.get("common_name"));
            } else {
                results.add(plant.get("scientific_name"));
            }
        }
        return results;
    }

    public void setPlantCreationMode(boolean enabled) {
        view.setCreationMode(enabled);
    }

    public synchronized void sendReminderMessage() {
        ArrayList<Plant> plants = activeProfile.getPlants();
        for (Plant plant : plants) {
            if (plant.getTimeRemaining() <= 7 && plant.getImageIcon() != null && !isUserReminded) {
                new Notification(plant.getImageIcon(), plant.getNameAlias() + " (" + plant.getNameWiki() + ")",
                        "Needs to be watered soon (" + plant.getTimeRemaining() + " days left)").display();
                isUserReminded = true;
            }
        }
    }

    /**
     * Creates plant with inputted parameters, adds it into the database, and refreshes the GUI
     *
     * @param name Optional name of plant
     * @param hoursBetweenWatering Time between watering
     */
    public void createPlant(String name, String hoursBetweenWatering) {
        Plant plant = new Plant();
        plant.setNameAlias(name);
        plant.setNameWiki(plantSearchInputName);
        plant.setDaysBetweenWatering(Utility.getStringToInt(hoursBetweenWatering));
        plant.setImageIcon(new ImageIcon(plantSearchImage));
        activeProfile.addPlant(plant);
        view.setCreationMode(false);
        refreshPlantListGUI();
        new Thread(() -> {
            int id = database.insertPlant(activeProfile.getDatabaseID(), plant);
            plant.setDatabaseID(id);
            database.upsertPlantImage(plant.getDatabaseID(), plantSearchImage);
            waterPlant(plant);
        }).start();
    }

    /**
     * Edits the selected plant with inputted parameters, updates the plant in the database, and refreshes the GUI
     *
     * @param name Optional name of plant
     * @param hoursBetweenWatering Time between watering
     */
    public void editSelectedPlant(String name, String hoursBetweenWatering) {
        Plant plant = getPlantFromIndex(selectedPlantIndex);
        plant.setNameAlias(name.length() < 1 ? plant.getNameAlias() : name);
        plant.setDaysBetweenWatering(Utility.getStringToInt(hoursBetweenWatering));
        database.updatePlant(plant);
        refreshPlantListGUI();
    }

    /**
     * Sets the Java Swing Card Layout
     *
     * @param layout The Swing layout
     */
    public void setCardLayout(String layout) {
        view.setCardLayout(layout);
    }

    /**
     * Refreshes the plant list by recreating it and doing some stuff
     */
    public void refreshPlantListGUI()
    {
        createPlantList();
        buttonPushed("plantList");
    }

    /**
     * Checks if index exists of selected plant
     *
     * @param index Index of selected plant
     * @return Boolean if index exists or not
     */
    public boolean validPlantIndex(int index) {
        return index >= 0 && index < activeProfile.getPlants().size();
    }

    /**
     * Waters the plant in the database and the program
     *
     * @param plant The specific plant
     */
    public void waterPlant(Plant plant) {
        LocalDateTime date = database.waterPlant(plant.getDatabaseID());
        plant.setLastTimeWatered(date);
        if (validPlantIndex(selectedPlantIndex)) {
            if (view.getPlantList().getPlantPanels().size() > 0) {
                view.updatePlantWateringComponents(selectedPlantIndex);
            }
        }
    }

    /**
     * Queries for images in the database, creates ImageIcons and sets them in the list of plants in the active profile
     */
    public synchronized void loadPlantImagesFromDatabase() {
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
            } catch (IOException ignored) { }
        }
        return imageDefault;
    }

    /**
     * Plays an audio file as sound effect
     *
     * @param file The selected audio file
     */
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

    /**
     * Attempt to register profile, and fails if rules are not met
     *
     * @param name A string representing the profile name that the user want to use
     * @param password A string representing the password that the user want to use
     * @param passwordRepeat A string representing the repeated password the user inputted
     * @return Boolean representing success or failure to register a profile
     */
    public boolean registerProfile(String name, String password, String passwordRepeat) {
        if (!validPassword(password, passwordRepeat)) {
            return false;
        }
        activeProfile = createProfile(name, password);
        if (activeProfile == null) {
            return false;
        }
        System.out.println("User " + activeProfile.getName() + " was created.");
        createPlantList();
        buttonPushed("plantList");
        return true;
    }

    /**
     * Attempt to login profile, and fails if inputs are wrong
     *
     * @param name The profile name
     * @param password The profile password
     */
    public void attemptLogin(String name, String password) {
        activeProfile = loginProfile(name, password);
        if (activeProfile == null) {
            view.showLoginError(true);
            setCardLayout("signIn");
            return;
        }

        byte[] image = database.getProfileImage(activeProfile.getDatabaseID());
        activeProfile.setImageIcon(new ImageIcon(image != null ? image : imageDefault));
        view.showLoginError(false);
        createPlantList();
        buttonPushed("plantList");
        new Thread(() -> loadPlantImagesFromDatabase()).start();
    }

    /**
     * Checks if password is valid according to minimum length and if the repeated input is the same
     *
     * @param password The intended password for a new profile
     * @param passwordRepeat The repeated password to ensure the password is as the user intended
     * @return Boolean representing if password is valid
     */
    public boolean validPassword(String password, String passwordRepeat) {
        return password.length() > 4 && password.equals(passwordRepeat);
    }
}