package model;

import java.sql.*;

public class Database
{
    public static void main(String[] args) {
        try
        {
            Class.forName("org.postgresql.Driver");
        }
        catch( ClassNotFoundException e )
        {
            e.printStackTrace();
        }
        String connectionURL = "jdbc:postgresql://134.122.77.196:5432/MyHappyPlants?user=MyHappyPlantsUser&password=MyHappyPlantsPass";
        try( Connection connection = DriverManager.getConnection( connectionURL );
             Statement statement = connection.createStatement() )
        {
            String SQL = "SELECT * FROM PlantTemp";
            ResultSet resultSet = statement.executeQuery( SQL );

            while( resultSet.next() )
            {
                System.out.println( resultSet.getInt( "id" ) + ", " + resultSet.getString( "name" ) );
            }
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
    }
}
