package model;

import java.util.ArrayList;

public class Profile
{
    private int databaseID;
    private String name;
    private ArrayList<Plant> plants = new ArrayList<>( );

    public void addPlant( Plant plant )
    {
        this.plants.add( plant );
    }

    public Profile setDatabaseID( int databaseID )
    {
        this.databaseID = databaseID;
        return this;
    }
    public Profile setName( String name )
    {
        this.name = name;
        return this;
    }
    public Profile setPlants( ArrayList<Plant> plants )
    {
        this.plants = plants;
        return this;
    }

    public int getDatabaseID()
    {
        return databaseID;
    }
    public String getName()
    {
        return name;
    }
    public ArrayList<Plant> getPlants() {
        return plants;
    }

    @Override
    public String toString( )
    {
        String output = "Profile\t{ databaseID = " + databaseID + ", name = " + name + " }\n";
        for( Plant plant : plants )
        {
            output += plant + "\n";
        }
        return output;
    }
}
