package controller;

import model.Database;
import model.JWiki;
import model.Plant;
import model.Profile;
import view.MainFrame;
import view.panels.PlantPanel;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * The Controller class handles the relation between the view (Swing frame) and the model (Database and other classes),
 * and does necessary calculations that fall outside of a specific model's area of expertise.
 * @author      Victor Johansson, Erik Hedåker
 */
public class Controller
{
    private MainFrame view;
    private Database database;
    private Profile activeProfile;

    public Controller( )
    {
        this.database = new Database( );
        this.activeProfile = database.getProfileByName( "Erik" );

        //Temporary methods, takes a lot of time and should be replaced later
        //upsertPlantImagesFromWikipediaToDatabase( );
        System.out.println( "OBS: Metoden loadPlantImagesFromDatabase tar lång tid att hämta bilder från databasen, do not be alarmed." );
        loadPlantImagesFromDatabase( );
        //Kanske göra async???

        view = new MainFrame( this );
    }

    /**
     * Getter method for the active profile's list of plants
     * @return              A list of plants
     */
    public ArrayList<Plant> getPlantList( )
    {
        return activeProfile.getPlants( );
    }

    public void buttonPushed(String button) {
        switch (button) {
            case "signIn":
                view.setCardLayout("signIn");
                break;
            case "plantList":
                view.setCardLayout("plantList");
                for (PlantPanel panel : view.getPlantList().getPlantPanels()) {
                    panel.getLoadingThread().start();
                }
                break;
        }
    }

    /**
     * Fetches images from Wikipedia with JWiki and upsert them into the database
     */
    public void upsertPlantImagesFromWikipediaToDatabase( )
    {
        for( Plant plant : activeProfile.getPlants( ) )
        {
            try
            {
                JWiki wiki = new JWiki( plant.getNameWiki( ) );
                database.upsertPlantImageRaw( wiki.getImageRaw( ), plant.getDatabaseID( ) );
            }
            catch ( IOException e )
            {
                e.printStackTrace( );
            }
        }
    }

    /**
     * Sets images for the list of plants from the active profile and queries for stored images on the database
     */
    public void loadPlantImagesFromDatabase( )
    {
        for( Plant plant : activeProfile.getPlants( ) )
        {
            plant.setImageIcon( getPlantImageIcon( plant.getDatabaseID( ) ) );
        }
    }

    /**
     * Creates an ImageIcon suitable for Swing GUI from a raw byte array queried from the database
     * @param   plantID     Integer representing a unique id required for database lookup
     * @return              A newly created ImageIcon
     */
    public ImageIcon getPlantImageIcon( int plantID )
    {
        return new ImageIcon( database.getPlantImageRaw( plantID ) );
    }

    /**
     * Calculates the amount of hours left from when the plant has to be watered, using difference between
     * the current date and the method getNextWateringDate
     * @param   plant       A specific Plant from the list of plants that the active profile has
     * @return              The amount of hours left
     */
    public long getNextWateringCountdown( Plant plant )
    {
        return ChronoUnit.HOURS.between( LocalDateTime.now(), getNextWateringDate( plant ) );
    }

    /**
     * Calculates the next date for when the plant has to be watered, using the attributes from the plant
     * @param   plant       A specific Plant from the list of plants that the active profile has
     * @return              A LocalDateTime which is the specific date in the future when the plant has to be watered
     */
    public LocalDateTime getNextWateringDate( Plant plant )
    {
        return plant.getLastTimeWatered().plusHours( plant.getHoursBetweenWatering() );
    }
}
