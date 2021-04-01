package model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Database
{
    private final static String connectionURL =
        "jdbc:postgresql://134.122.77.196:5432/MyHappyPlants?user=MyHappyPlantsUser&password=MyHappyPlantsPass";

    public static ArrayList<String> GetAllProfileNames()
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

    public static ArrayList<Plant> GetPlantsForProfile( String name )
    {
        final String SQL =
            "SELECT * " +
            "FROM plant " +
            "JOIN profile " +
            "ON plant.profile_id = profile.id " +
            "JOIN RecentWatering() AS recent_watering " +
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
                    plant.setWateringHappenedLast( resultSet.getObject( "recent", LocalDateTime.class ) );
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

    public static Profile GetProfile( String name )
    {
        return new Profile( "Erik", GetPlantsForProfile( "Erik" ) );
    }

    public static void main(String[] args)
    {
        System.out.println( GetProfile( "Erik" ) );
    }
}
