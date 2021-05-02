package model;

import javax.swing.*;
import java.time.LocalDateTime;

/**
 * The Plant class represents a plant that the profile has and tracks
 *
 * @author Erik Hedåker, Viktor Johansson
 */
public class Plant
{
    private int databaseID;
    private String nameAlias;
    private String nameWiki;
    private int hoursBetweenWatering;
    private LocalDateTime lastTimeWatered;
    private ImageIcon imageIcon;
    private String description;

    /**
     * All Setter methods
     *
     * Param is the private attribute being set
     * @return A reference to the current object, in order to chain method calls
     */
    public Plant setDatabaseID( int databaseID )
    {
        this.databaseID = databaseID;
        return this;
    }
    public Plant setNameAlias( String nameAlias )
    {
        this.nameAlias = nameAlias;
        return this;
    }
    public Plant setNameWiki( String nameWiki )
    {
        this.nameWiki = nameWiki;
        return this;
    }
    public Plant setHoursBetweenWatering( int hoursBetweenWatering )
    {
        this.hoursBetweenWatering = hoursBetweenWatering;
        return this;
    }
    public Plant setLastTimeWatered( LocalDateTime wateringHappenedLast )
    {
        this.lastTimeWatered = wateringHappenedLast;
        return this;
    }
    public Plant setDescription(String description) {
        this.description = description;
        return this;
    }
    public Plant setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        return this;
    }

    /**
     * All Getter methods
     *
     * @return The private attribute
     */
    public int getDatabaseID()
    {
        return databaseID;
    }
    public String getNameAlias() {
        return nameAlias;
    }
    public String getNameWiki() {
        return nameWiki;
    }
    public int getHoursBetweenWatering() {
        return hoursBetweenWatering;
    }
    public LocalDateTime getLastTimeWatered() {
        return lastTimeWatered;
    }
    public String getDescription() {
        return description;
    }
    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    @Override
    public String toString( )
    {
        return
            "Plant\t{ databaseID = " + databaseID +
            ", nameAlias = '" + nameAlias +
            "', nameWiki = '" + nameWiki +
            "', hoursBetweenWatering = " + hoursBetweenWatering +
            ", lastTimeWatered = " + lastTimeWatered + " }";
    }
}
