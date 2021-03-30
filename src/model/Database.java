package model;

import java.sql.*;
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
                "ON profile.id=plant.profile_id " +
                "WHERE profile.name='" + name + "'";
        ArrayList<Plant> plants = new ArrayList<>(  );

        try( Connection connection = DriverManager.getConnection( connectionURL );
             Statement statement = connection.createStatement( );
             ResultSet resultSet = statement.executeQuery( SQL ) )
        {
            while( resultSet.next() )
            {
                Plant plant = new Plant();
                plant.setNameAlias( resultSet.getString( "name" ) );
                plant.setNameCommon( resultSet.getString( "name_common" ) );
                plant.setNameScientific( resultSet.getString( "name_scientific" ) );
                plant.setWikipediaLink( resultSet.getString( "wikipedia_link" ) );
                plant.setDescription( resultSet.getString( "description" ) );
                plants.add( plant );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace( );
        }

        return plants;
    }

    public static void main(String[] args)
    {
        Profile profile = new Profile("Erik", GetPlantsForProfile( "Erik" ));
        System.out.println( profile );
    }
}
