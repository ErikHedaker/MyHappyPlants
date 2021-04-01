package model;

import java.util.ArrayList;

public class Profile
{
    String name;
    ArrayList<Plant> plants;

    public Profile( String name )
    {
        this( name, null );
    }
    public Profile( String name, ArrayList<Plant> plants )
    {
        this.name = name;
        this.plants = plants;
    }

    public void setName( String name )
    {
        this.name = name;
    }
    public void setPlants( ArrayList<Plant> plants )
    {
        this.plants = plants;
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
