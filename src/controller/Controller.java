package controller;

import model.Database;
import model.JWiki;
import model.Plant;
import model.Profile;
import view.MainFrame;
import view.panels.PlantPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * The Controller class handles the relation between the view (Swing frame) and the model (Database and other classes),
 * and does necessary calculations that fall outside of a specific model's area of expertise.
 * @author Victor Johansson, Erik Hedåker
 */
public class Controller
{
    private Database database;
    private Profile activeProfile;
    private MainFrame view;
    private byte[] imageDefault;

    public Controller( )
    {
        this.database = new Database( );
        this.activeProfile = database.getProfileByName( "Erik" );
        this.view = new MainFrame( this );
        this.imageDefault = fetchImageFromURL( "file:images/plant.jpg" );
        new Thread( ( ) -> loadPlantImagesFromDatabase( ) ).start( );
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
            JWiki wiki = new JWiki( plant.getNameWiki( ) );
            database.upsertPlantImageRaw( fetchImageFromURL( wiki.getImageURL( ) ), plant.getDatabaseID( ) );
        }
    }

    /**
     * Attempts to get an image from the URL, otherwise returns a default image
     * @param   imageURL    A URL to an image (png, jpg, gif, or similar)
     * @return              An image in the byte array format
     */
    public byte[] fetchImageFromURL( String imageURL )
    {
        if( imageURL != null )
        {
            try( ByteArrayOutputStream baoStream = new ByteArrayOutputStream() )
            {
                String extension = imageURL.substring( imageURL.lastIndexOf( "." ) + 1 );
                ImageIO.write( ImageIO.read( new URL( imageURL ) ), extension, baoStream );
                baoStream.flush();
                return baoStream.toByteArray();
            }
            catch( IOException e )
            {
                e.printStackTrace();
            }
        }

        return imageDefault;
    }

    /**
     * Sets images for the list of plants in the active profile
     */
    public void loadPlantImagesFromDatabase( )
    {
        for( Plant plant : activeProfile.getPlants( ) )
        {
            plant.setImageIcon( getPlantImageIconFromDatabase( plant.getDatabaseID( ) ) );
        }
    }

    /**
     * Creates an ImageIcon suitable for Swing GUI from a raw byte array queried from the database
     * @param   plantID     Integer representing a unique id required for database lookup
     * @return              A newly created ImageIcon
     */
    public ImageIcon getPlantImageIconFromDatabase( int plantID )
    {
        return new ImageIcon( database.getPlantImageRaw( plantID ) );
    }

    /**
     * Calculates the amount of hours left from when the plant has to be watered, using difference between
     * the current date and the method getNextWateringDate
     * @param   plant       A specific Plant from the list of plants that the active profile has
     * @return              The amount of hours left
     */
    public static long getNextWateringCountdown( Plant plant )
    {
        return ChronoUnit.HOURS.between( LocalDateTime.now(), getNextWateringDate( plant ) );
    }

    /**
     * Calculates the next date for when the plant has to be watered, using the attributes from the plant
     * @param   plant       A specific Plant from the list of plants that the active profile has
     * @return              A LocalDateTime which is the specific date in the future when the plant has to be watered
     */
    public static LocalDateTime getNextWateringDate( Plant plant )
    {
        return plant.getLastTimeWatered().plusHours( plant.getHoursBetweenWatering() );
    }
}
