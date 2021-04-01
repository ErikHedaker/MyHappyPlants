package model;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Database
{
    private final String connectionURL =
        "jdbc:postgresql://134.122.77.196:5432/MyHappyPlants?user=MyHappyPlantsUser&password=MyHappyPlantsPass";

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
                    return new Profile(
                            resultSet.getString( "name" ),
                            getPlantsByProfileName( resultSet.getString( "name" ) )
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

        return null;
    }
    public ArrayList<Plant> getPlantsByProfileName( String name )
    {
        final String SQL =
            "SELECT name_alias, name_wiki, hours_between_watering, last_time_watered " +
            "FROM plant " +
            "JOIN profile " +
            "ON profile.id = plant.profile_id " +
            "LEFT JOIN RecentWatering() AS recent_watering " +
            "ON plant.id = recent_watering.plant_id " +
            "WHERE profile.name = ?";
        ArrayList<Plant> plants = new ArrayList<>(  );

        try( Connection connection = DriverManager.getConnection( connectionURL );
             PreparedStatement preparedStatement = connection.prepareStatement( SQL ) )
        {
            preparedStatement.setString( 1, name );

            try( ResultSet resultSet = preparedStatement.executeQuery( ) )
            {
                while( resultSet.next() )
                {
                    Plant plant = new Plant();
                    plant.setNameAlias( resultSet.getString( "name_alias" ) );
                    plant.setNameWiki( resultSet.getString( "name_wiki" ) );
                    plant.setHoursBetweenWatering( resultSet.getInt( "hours_between_watering" ) );
                    plant.setLastTimeWatered( resultSet.getObject( "last_time_watered", LocalDateTime.class ) );
                    plants.add( plant );
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
    public void insertPlant( Plant plant, String name )
    {
        final String SQL =
            "INSERT INTO plant (name_alias, name_wiki, profile_id, hours_between_watering)" +
            "SELECT ?, ?, profile.id, ? " +
            "FROM profile " +
            "WHERE profile.name = ?";

        try( Connection connection = DriverManager.getConnection( connectionURL );
             PreparedStatement preparedStatement = connection.prepareStatement( SQL ) )
        {
            preparedStatement.setString( 1, plant.getNameAlias() );
            preparedStatement.setString( 2, plant.getNameWiki() );
            preparedStatement.setInt( 3, plant.getHoursBetweenWatering() );
            preparedStatement.setString( 4, name );
            preparedStatement.executeUpdate();
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
        }
    }
    public void insertProfile( String name )
    {
        final String SQL =
            "INSERT INTO profile (name)" +
            "VALUES (?)";

        try( Connection connection = DriverManager.getConnection( connectionURL );
             PreparedStatement preparedStatement = connection.prepareStatement( SQL ) )
        {
            preparedStatement.setString( 1, name );
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
