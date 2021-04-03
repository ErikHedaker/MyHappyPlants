package controller;

import model.Database;
import model.JWiki;
import model.Plant;
import model.Profile;
import view.MainFrame;
import view.panels.PlantPanel;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The Controller class handles the relation between the view (Swing frame) and the model (Database and other classes)
 * @author      Erik Hedåker, ViKtor Johansson
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
        loadPlantImagesFromDatabase( );

        view = new MainFrame( this );
    }

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
     * @param   plantID integer representing a unique id required for database lookup
     * @return          a newly created ImageIcon
     */
    public ImageIcon getPlantImageIcon( int plantID )
    {
        return new ImageIcon( database.getPlantImageRaw( plantID ) );
    }
}
