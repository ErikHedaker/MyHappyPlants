package controller;

import model.*;
import model.api.JWiki;
import view.MainFrame;
import view.dialog.ConfirmationDialog;
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
    private boolean isUserReminded;

    public Controller() {
        this.database = new Database(new SimpleEncryption().readFile());
        this.view = new MainFrame(this);
        this.imageDefault = fetchImageFromURL("file:images/plant.jpg");
        this.activeProfile = new Profile().setName("Guest").setPlants(new ArrayList<>());
        createPlantList();
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

    public void buttonPushed(String button) {
        switch (button) {
            case "signIn":
                view.setCardLayout("signIn");
                break;
            case "plantList":
                if (view.getPlantList().getPlantPanels().size() < 1) {
                    view.setCardLayout("welcome info");
                } else {
                    view.setCardLayout("plantList");
                    new Thread(() -> loadPlantImagesFromDatabase()).start();
                    for (PlantPanel panel : view.getPlantList().getPlantPanels()) {
                        if (!panel.getLoadingThread().isAlive()) {
                            panel.getLoadingThread().start();
                        }
                    }
                    new Thread(new ReminderTask(this)).start();
                }
                view.setProfile(" - " + activeProfile.getName() + " - ");
                view.showSearch(true);
                view.showSearchField();
                break;
            case "search":
                if (view.getSearchInput().length() > 0) {
                    view.setCardLayout("loading-screen");
                    ArrayList<HashMap<String, String>> searchResult = database.searchPlant("%" + view.getSearchInput() + "%");
                    if (!searchResult.isEmpty()) {
                        HashMap<String, String> plant = searchResult.get(0);

                        String[] individualScientificName = plant.get("scientific_name").split(" ");
                        JWiki wiki = new JWiki(plant.get("common_name"));
                        wiki = wiki.valid() ? wiki : new JWiki(plant.get("scientific_name"));
                        wiki = wiki.valid() ? wiki : new JWiki(individualScientificName[0] + individualScientificName[1]);
                        wiki = wiki.valid() ? wiki : new JWiki(individualScientificName[0]);
                        wiki = wiki.valid() ? wiki : new JWiki(plant.get("url_wikipedia_en").substring(plant.get("url_wikipedia_en").lastIndexOf("/") + 1));
                        try {
                            ImageIcon imageIcon = new ImageIcon(new URL(plant.get("image_url")));
                            if (imageIcon.getIconHeight() == -1 ||
                                imageIcon.getIconWidth()  == -1 ) {
                                throw new Exception();
                            }
                            view.setImageLabel(imageIcon);
                        } catch (Exception e) {
                            view.setImageLabel(new ImageIcon(fetchImageFromURL(wiki.getImageURL())));
                        }
                        view.showButton(true);
                        view.setTitle(plant.get("common_name") != null ? plant.get("common_name") + " (" + wiki.getDisplayTitle() + ")" : wiki.getDisplayTitle());
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
                    view.setWaterTF(String.valueOf(plant.getHoursBetweenWatering()));
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
        }
    }

    public ArrayList<String> getResultsArray() {
        ArrayList<String> results = new ArrayList<>();
        results.add(view.getSearchInput());

        ArrayList<HashMap<String, String>> searchResult = database.searchPlant("%" + view.getSearchInput() + "%");
        if (!searchResult.isEmpty()) {
            String plant = searchResult.get(0).get("common_name");
            results.add(plant);
        }

        results.add("example2");
        return results;
    }

    public void setPlantCreationMode(boolean enabled) {
        view.setCreationMode(enabled);
    }

    public void sendReminderMessage() {
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
        int timeBetweenWatering = Utility.getStringToInt(hoursBetweenWatering);
        if (timeBetweenWatering == 0) {
            new MessageDialog("Enter valid watering interval days.\nThis is needed to remind you whenever \nyour plants needs to be watered.");
            view.setCardLayout("plant creation page");
            return;
        }
        Plant plant = new Plant();
        plant.setNameAlias(name);
        plant.setNameWiki(plantSearchInputName);

        plant.setHoursBetweenWatering(timeBetweenWatering);
        activeProfile.addPlant(plant);
        refreshPlantListGUI();

        JWiki wiki = new JWiki(plantSearchInputName);
        byte[] icon = fetchImageFromURL(wiki.getImageURL());
        plant.setImageIcon(new ImageIcon(icon));

        new Thread(() -> upsertPlantDetails(plant, icon)).start();
        view.setCreationMode(false);
    }

    private void upsertPlantDetails(Plant plant, byte[] icon) {
        plant.setImageIcon(new ImageIcon(icon));
        int id = database.insertPlant(activeProfile.getDatabaseID(), plant);
        plant.setDatabaseID(id);
        waterPlant(plant);
        database.upsertPlantImage(plant.getDatabaseID(), icon);
    }

    public void editSelectedPlant(String name, String hoursBetweenWatering) {
        if (hoursBetweenWatering.equals("0")) {
            new MessageDialog("Enter a valid watering interval 0 days is too little.");
            return;
        }
        Plant plant = getPlantFromIndex(selectedPlantIndex);
        plant.setNameAlias(name.length() < 1 ? plant.getNameAlias() : name);
        plant.setHoursBetweenWatering(Utility.getStringToInt(hoursBetweenWatering));
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

        view.showLoginError(false);
        createPlantList();
        buttonPushed("plantList");
    }

    public boolean validPassword(String password, String passwordRepeat) {
        return password.length() > 4 && password.equals(passwordRepeat);
    }
}