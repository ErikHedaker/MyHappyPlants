package model;

import java.util.ArrayList;

public class Profile
{
    private ArrayList<Plant> plants = new ArrayList<>( );
    private int databaseID;
    private String name;
    private byte[] passwordHash;
    private byte[] passwordSalt;

    public void addPlant( Plant plant )
    {
        this.plants.add( plant );
    }

    public Profile setPlants( ArrayList<Plant> plants )
    {
        this.plants = plants;
        return this;
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

    public Profile setPasswordHash( byte[] passwordHash )
    {
        this.passwordHash = passwordHash;
        return this;
    }

    public Profile setPasswordSalt( byte[] passwordSalt )
    {
        this.passwordSalt = passwordSalt;
        return this;
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }
    public int getDatabaseID()
    {
        return databaseID;
    }
    public String getName()
    {
        return name;
    }
    public byte[] getPasswordHash(){ return passwordHash;}
    public byte[] getPasswordSalt(){ return passwordSalt;}

    @Override
    public String toString( )
    {
        String output =
            "Profile\t{ databaseID = " + databaseID +
            ", name = '" + name +
            "', passwordHash = '" + passwordHash +
            "', passwordSalt = '" + passwordSalt + "' }\n";
        for( Plant plant : plants )
        {
            output += plant + "\n";
        }
        return output;
    }
}
