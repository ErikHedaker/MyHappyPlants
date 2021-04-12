package controller;

import model.Database;
import model.api.JWiki;
import model.Plant;
import model.Profile;
import org.apache.commons.codec.binary.Base64;
import view.MainFrame;
import view.panels.PlantPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Controller class handles the relation between the view (Swing frame) and the model (Database and other classes),
 * and does necessary calculations that fall outside of a specific model's area of expertise.
 * @author Viktor Johansson, Erik Hedåker
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

        //this.activeProfile = ProfileLogin( "Admin", "Admin" );
        this.view = new MainFrame( this );
        this.imageDefault = fetchImageFromURL( "file:images/plant.jpg" );
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
                view.createPlantList();
                view.setCardLayout("plantList");
                new Thread( ( ) -> loadPlantImagesFromDatabase( ) ).start( );
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
    public void upsertPlantImagesFromWikipediaToDatabase( )
    {
        for( Plant plant : activeProfile.getPlants( ) )
        {
            JWiki wiki = new JWiki( plant.getNameWiki( ) );
            database.upsertPlantImage( plant.getDatabaseID( ), fetchImageFromURL( wiki.getImageURL( ) ) );
        }
    }

    /**
     * Queries for images in the database, creates ImageIcons and sets them in the list of plants in the active profile
     */
    public void loadPlantImagesFromDatabase( )
    {
        for( Plant plant : activeProfile.getPlants( ) )
        {
            if (database.getPlantImage( plant.getDatabaseID( ) ) == null) plant.setImageIcon(new ImageIcon(imageDefault));
            else
            plant.setImageIcon( new ImageIcon( database.getPlantImage( plant.getDatabaseID( ) ) ) );
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

    public Profile ProfileLogin( String name, String password )
    {
        Profile profile = database.getProfile( name );
        byte[] hashed = GetPasswordHash( password, profile.getPasswordSalt() );
        byte[] stored = profile.getPasswordHash( );
        return Arrays.equals( hashed, stored ) ? profile : null;
    }

    public Profile CreateProfile( String name, String password )
    {
        byte[] salt = GenerateRandomSalt();
        Profile profile = new Profile()
            .setName( name )
            .setPasswordHash( GetPasswordHash( password, salt ) )
            .setPasswordSalt( salt );
        int id = database.insertProfile( profile );
        profile.setDatabaseID( id );
        return profile;
    }

    public static byte[] GenerateRandomSalt()
    {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[20];
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] GetPasswordHash( String password, byte[] salt )
    {
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance( "SHA-512" );
            messageDigest.update( salt );
            return messageDigest.digest( StringToByte( password ) );
        }
        catch( NoSuchAlgorithmException e )
        {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] StringToByte( String input ) {
        if (Base64.isBase64(input)) {
            return Base64.decodeBase64(input);
        } else {
            return Base64.encodeBase64(input.getBytes());
        }
    }

    public void loginAttempt(String username, String password) {
        if (database.getProfile(username) != null) {
            activeProfile = database.getProfile(username);
                view.showLoginError(false);
                buttonPushed("plantList");
        } else {
            view.showLoginError(true);
        }
    }

    /*public boolean invalidPassword() {
        if(activeProfile.getPassword().length() < validP){
            //l.invalidPasswordMessage();
            return false;
        }
    }*/

    public static String ByteToString( byte[] input )
    {
        return Base64.encodeBase64String( input );
    }
}
