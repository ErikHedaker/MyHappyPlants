package model;

import java.util.ArrayList;

/**
 * The Profile represents a profile that the user can log into and see their plants
 *
 * @author Erik Hedåker, Viktor Johansson
 */
public class Profile
{
    private ArrayList<Plant> plants = new ArrayList<>( );
    private int databaseID;
    private String name;
    private byte[] passwordHash;
    private byte[] passwordSalt;

    /**
     * Adds a plant to the private ArrayList plants
     *
     * @param plant The plant that gets added
     */
    public void addPlant( Plant plant )
    {
        this.plants.add( plant );
    }

    /**
     * All Setter methods
     *
     * Param is the private attribute being set
     * @return A reference to the current object, in order to chain method calls
     */
    public synchronized Profile setPlants( ArrayList<Plant> plants )
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

    /**
     * All Getter methods
     *
     * @return The private attribute
     */
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
