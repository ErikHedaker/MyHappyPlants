package model;

import java.security.UnresolvedPermission;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The Database class which handles all queries to a PostgreSQL database
 * @author      Erik Hedåker
 */
public class Database
{
    private final String connectionURL;

    public Database( String connectionURL )
    {
        this.connectionURL = connectionURL;
    }
    public Database( )
    {
        this("jdbc:postgresql://134.122.77.196:5432/MyHappyPlants?user=MyHappyPlantsUser&password=MyHappyPlantsPass");
    }

    public ArrayList<String> getAllProfileNames()
    {
        final String SQL = "SELECT name FROM profile";
        ArrayList<String> names = new ArrayList<>(  );

        try( Connection connection = DriverManager.getConnection( connectionURL );
             Statement statement = connection.createStatement( );
             ResultSet resultSet = statement.executeQuery( SQL ) )
        {
            while( resultSet.next() )
            {
                names.add( resultSet.getString( "name" ) );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
        }

        return names;
    }
    public Profile getProfileByName( String name )
    {
        final String SQL =
            "SELECT id, name " +
            "FROM profile " +
            "WHERE profile.name = ?";

        try( Connection connection = DriverManager.getConnection( connectionURL );
             PreparedStatement preparedStatement = connection.prepareStatement( SQL ) )
        {
            preparedStatement.setString( 1, name );

            try( ResultSet resultSet = preparedStatement.executeQuery( ) )
            {
                if( resultSet.next() )
                {
                    int id = resultSet.getInt( "id" );
                    return new Profile( )
                        .setDatabaseID( id )
                        .setName( name )
                        .setPlants( getPlantsByProfileID( id ) );
                }
            }
            catch( SQLException e )
            {
                e.printStackTrace( );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
        }

        return null;
    }
    public ArrayList<Plant> getPlantsByProfileID( int profileID )
    {
        final String SQL =
            "SELECT plant.id AS plantID, name_alias, name_wiki, hours_between_watering, last_time_watered " +
            "FROM plant " +
            "JOIN profile " +
            "ON profile.id = plant.profile_id " +
            "LEFT JOIN RecentWatering() AS recent_watering " +
            "ON plant.id = recent_watering.plant_id " +
            "WHERE profile.id = ?";
        ArrayList<Plant> plants = new ArrayList<>(  );

        try( Connection connection = DriverManager.getConnection( connectionURL );
             PreparedStatement preparedStatement = connection.prepareStatement( SQL ) )
        {
            preparedStatement.setInt( 1, profileID );

            try( ResultSet resultSet = preparedStatement.executeQuery( ) )
            {
                while( resultSet.next() )
                {
                    plants.add(
                        new Plant()
                            .setDatabaseID( resultSet.getInt( "plantID" ) )
                            .setNameAlias( resultSet.getString( "name_alias" ) )
                            .setNameWiki( resultSet.getString( "name_wiki" ) )
                            .setHoursBetweenWatering( resultSet.getInt( "hours_between_watering" ) )
                            .setLastTimeWatered( resultSet.getObject( "last_time_watered", LocalDateTime.class ) )
                    );
                }

            }
            catch( SQLException e )
            {
                e.printStackTrace( );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
        }

        return plants;
    }

    public void waterPlant( int plantID )
    {
        waterPlant( plantID, LocalDateTime.now() );
    }
    public void waterPlant( int plantID, LocalDateTime happened )
    {
        final String SQL =
            "INSERT INTO watering (plant_id, happened)" +
            "VALUES (?, ?)";

        try( Connection connection = DriverManager.getConnection( connectionURL );
             PreparedStatement preparedStatement = connection.prepareStatement( SQL ) )
        {
            preparedStatement.setInt( 1, plantID );
            preparedStatement.setObject( 2, happened );
            preparedStatement.executeUpdate();
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
        }
    }
    public int insertPlant( Plant plant, int profileID )
    {
        final String SQL =
            "INSERT INTO plant (name_alias, name_wiki, profile_id, hours_between_watering)" +
            "VALUES (?, ?, ?, ?)";

        try( Connection connection = DriverManager.getConnection( connectionURL );
             PreparedStatement preparedStatement = connection.prepareStatement( SQL, PreparedStatement.RETURN_GENERATED_KEYS ) )
        {
            preparedStatement.setString( 1, plant.getNameAlias() );
            preparedStatement.setString( 2, plant.getNameWiki() );
            preparedStatement.setInt( 3, profileID );
            preparedStatement.setInt( 4, plant.getHoursBetweenWatering() );
            preparedStatement.executeUpdate();

            try( ResultSet resultSet = preparedStatement.getGeneratedKeys() )
            {
                if( resultSet.next() )
                {
                    return resultSet.getInt( 1 );
                }
            }
            catch( SQLException e )
            {
                e.printStackTrace( );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
        }

        return -1;
    }
    public int insertProfile( String name )
    {
        final String SQL =
            "INSERT INTO profile (name)" +
            "VALUES (?)";

        try( Connection connection = DriverManager.getConnection( connectionURL );
             PreparedStatement preparedStatement = connection.prepareStatement( SQL ) )
        {
            preparedStatement.setString( 1, name );
            preparedStatement.executeUpdate();

            try( ResultSet resultSet = preparedStatement.getGeneratedKeys() )
            {
                if( resultSet.next() )
                {
                    return resultSet.getInt( 1 );
                }
            }
            catch( SQLException e )
            {
                e.printStackTrace( );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
        }

        return -1;
    }
    public void updatePlant( Plant plant )
    {
        final String SQL =
            "UPDATE plant " +
            "SET name_alias = ?, name_wiki = ?, hours_between_watering = ?" +
            "WHERE id = ?";

        try( Connection connection = DriverManager.getConnection( connectionURL );
             PreparedStatement preparedStatement = connection.prepareStatement( SQL ) )
        {
            preparedStatement.setString( 1, plant.getNameAlias() );
            preparedStatement.setString( 2, plant.getNameWiki() );
            preparedStatement.setInt( 3, plant.getHoursBetweenWatering() );
            preparedStatement.setInt( 4, plant.getDatabaseID() );
            preparedStatement.executeUpdate();
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
        }
    }
    public void updateProfile( Profile profile )
    {
        final String SQL =
            "UPDATE profile " +
            "SET name = ? " +
            "WHERE id = ?";

        try( Connection connection = DriverManager.getConnection( connectionURL );
             PreparedStatement preparedStatement = connection.prepareStatement( SQL ) )
        {
            preparedStatement.setString( 1, profile.getName() );
            preparedStatement.setInt( 2, profile.getDatabaseID() );
            preparedStatement.executeUpdate();
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
        }
    }
    public void deletePlant( int plantID )
    {
        final String SQL =
            "DELETE FROM plant " +
            "WHERE id = ?";

        try( Connection connection = DriverManager.getConnection( connectionURL );
             PreparedStatement preparedStatement = connection.prepareStatement( SQL ) )
        {
            preparedStatement.setInt( 1, plantID );
            preparedStatement.executeUpdate();
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
        }
    }
    public void deleteProfile( int profileID )
    {
        final String SQL =
            "DELETE FROM profile " +
            "WHERE id = ?";

        try( Connection connection = DriverManager.getConnection( connectionURL );
             PreparedStatement preparedStatement = connection.prepareStatement( SQL ) )
        {
            preparedStatement.setInt( 1, profileID );
            preparedStatement.executeUpdate();
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
        }
    }
    public byte[] getPlantImageRaw( int plantID )
    {
        final String SQL =
            "SELECT raw_data " +
            "FROM plant_image " +
            "WHERE plant_id = ?";

        try( Connection connection = DriverManager.getConnection( connectionURL );
             PreparedStatement preparedStatement = connection.prepareStatement( SQL ) )
        {
            preparedStatement.setInt( 1, plantID );

            try( ResultSet resultSet = preparedStatement.executeQuery( ) )
            {
                if( resultSet.next() )
                {
                    return resultSet.getBytes( "raw_data" );
                }
            }
            catch( SQLException e )
            {
                e.printStackTrace( );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
        }

        return null;
    }

    /**
     * Insert or update the table holding image data for plants
     * @author          Erik Hedåker
     * @param   plantID integer representing a unique id required for database lookup
     */
    public void upsertPlantImageRaw( byte[] imageData, int plantID )
    {
        final String SQL =
            "INSERT INTO plant_image (plant_id, raw_data) " +
            "VALUES (?, ?) " +
            "ON CONFLICT (plant_id) DO UPDATE " +
            "SET raw_data = ? " +
            "WHERE excluded.plant_id = ?";

        try( Connection connection = DriverManager.getConnection( connectionURL );
             PreparedStatement preparedStatement = connection.prepareStatement( SQL ) )
        {
            preparedStatement.setInt( 1, plantID );
            preparedStatement.setBytes( 2, imageData );
            preparedStatement.setBytes( 3, imageData );
            preparedStatement.setInt( 4, plantID );
            preparedStatement.executeUpdate();
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
        }
    }

    public static void main(String[] args)
    {
        // Används för att testa databas metoder utan GUI

        Database database = new Database();
        Profile profile = database.getProfileByName( "Erik" );
        System.out.println( profile );
    }
}
