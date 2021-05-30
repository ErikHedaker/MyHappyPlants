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
 * @author Viktor Johansson, Erik Hedåker
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

    public Controller() {
        this.database = new Database(new SimpleEncryption().readFile());
        this.view = new MainFrame(this);
        this.imageDefault = fetchImageFromURL("file:images/plant.jpg");
        this.activeProfile = new Profile()
            .setName("Guest")
            .setPlants(new ArrayList<>())
            .setImageIcon(new ImageIcon(imageDefault));
        //createPlantList();
        //createUserProfile();
    }

    public Plant getPlantFromIndex(int index) {
        Plant plant = null;
        try {
            plant = getPlantList().get(index);
        }catch (IndexOutOfBoundsException e) {
        }
        return plant;
    }

    public void setSelectedPlantFromIndex(int plantIndex) {
        selectedPlantIndex = plantIndex;
        Plant plant = getPlantFromIndex(plantIndex);
        view.setSelectedPlantName(plant.getNameWiki());
        ImageIcon imageIcon = plant.getImageIcon();

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

    /*public void createUserProfile(){
        view.createUserProfile();
    }*/

    public void buttonPushed(String button) {
        switch (button) {
            case "signIn":
                view.setCardLayout("signIn");
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
                view.setProfile("       " + activeProfile.getName() + "       ");
                view.showSearch(true);
                view.showSearchField();
                new Thread(() -> sendReminderMessage()).start();
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
        }
    }

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

    public void createPlant(String name, String hoursBetweenWatering) {
        Plant plant = new Plant();
        plant.setNameAlias(name);
        plant.setNameWiki(plantSearchInputName);
        plant.setDaysBetweenWatering(Utility.getStringToInt(hoursBetweenWatering));
        plant.setImageIcon(new ImageIcon(plantSearchImage));
        activeProfile.addPlant(plant);
        refreshPlantListGUI();
        new Thread(() -> upsertPlantDetails(plant, plantSearchImage)).start();
        view.setCreationMode(false);
    }

    private void upsertPlantDetails(Plant plant, byte[] icon) {
        int id = database.insertPlant(activeProfile.getDatabaseID(), plant);
        plant.setDatabaseID(id);
        database.upsertPlantImage(plant.getDatabaseID(), icon);
    }

    public void editSelectedPlant(String name, String hoursBetweenWatering) {
        if (hoursBetweenWatering.equals("0")) {
            new MessageDialog("Enter a valid watering interval 0 days is too little.");
            return;
        }
        Plant plant = getPlantFromIndex(selectedPlantIndex);
        plant.setNameAlias(name.length() < 1 ? plant.getNameAlias() : name);
        plant.setDaysBetweenWatering(Utility.getStringToInt(hoursBetweenWatering));
        database.updatePlant(plant);
        refreshPlantListGUI();
    }

    public void setCardLayout(String layout) {
        view.setCardLayout(layout);
    }

    public void refreshPlantListGUI()
    {
        createPlantList();
        buttonPushed("plantList");
    }

    public boolean validPlantIndex(int index) {
        return index >= 0 && index < activeProfile.getPlants().size();
    }

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

        byte[] image = database.getProfileImage(activeProfile.getDatabaseID());
        activeProfile.setImageIcon(new ImageIcon(image != null ? image : imageDefault));
        view.showLoginError(false);
        createPlantList();
        buttonPushed("plantList");
        new Thread(() -> loadPlantImagesFromDatabase()).start();
    }

    public boolean validPassword(String password, String passwordRepeat) {
        return password.length() > 4 && password.equals(passwordRepeat);
    }
}