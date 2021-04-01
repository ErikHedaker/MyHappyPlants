package model;

import java.util.ArrayList;

public class Profile
{
    private String name;
    private ArrayList<Plant> plants;

    public Profile()
    {
        this( "Default", new ArrayList<>( ) );
    }
    public Profile( String name, ArrayList<Plant> plants )
    {
        this.name = name;
        this.plants = plants;
    }

    public void addPlant( Plant plant )
    {
        this.plants.add( plant );
    }

    public void setName( String name )
    {
        this.name = name;
    }
    public void setPlants( ArrayList<Plant> plants )
    {
        this.plants = plants;
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
        String output = name + "\n";
        for( Plant plant : plants )
        {
            output += plant + "\n";
        }
        return output;
    }
}
