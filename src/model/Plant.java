package model;

import javax.swing.*;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * The Plant class represents a plant that the profile has and tracks
 *
 * @author Erik Hedåker, Viktor Johansson
 */
public class Plant {
    private int databaseID;
    private String nameAlias;
    private String nameWiki;
    private int daysBetweenWatering;
    private LocalDateTime lastTimeWatered;
    private ImageIcon imageIcon;
    private String currentSize;

    /**
     * All Setter methods
     *
     * Param is the private attribute being set
     * @return A reference to the current object, in order to chain method calls
     */
    public Plant setDatabaseID(int databaseID)
    {
        this.databaseID = databaseID;
        return this;
    }
    public Plant setNameAlias(String nameAlias)
    {
        this.nameAlias = nameAlias;
        return this;
    }
    public Plant setNameWiki(String nameWiki)
    {
        this.nameWiki = nameWiki;
        return this;
    }
    public Plant setDaysBetweenWatering(int daysBetweenWatering)
    {
        this.daysBetweenWatering = daysBetweenWatering;
        return this;
    }
    public Plant setLastTimeWatered(LocalDateTime wateringHappenedLast)
    {
        this.lastTimeWatered = wateringHappenedLast;
        return this;
    }
    public Plant setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        return this;
    }
    public Plant setCurrentSize(String currentSize) {
        this.currentSize = currentSize;
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
    public int getDaysBetweenWatering() {
        return daysBetweenWatering;
    }
    public String getCurrentSize() {
        return currentSize;
    }

    public int getTimeRemaining() {
        int timeRemaining = daysBetweenWatering - getLastTimeWateredInterval();

        return timeRemaining;
    }

    public LocalDateTime getLastTimeWatered() {
        return lastTimeWatered;
    }

    public int getLastTimeWateredInterval() {
        if (lastTimeWatered == null) {
            return 0;
        }
        LocalDateTime date1 = lastTimeWatered;
        LocalDateTime date2 = LocalDateTime.now();
        int daysBetween = (int) Duration.between(date1, date2).toDays();

        return daysBetween;
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
            "', hoursBetweenWatering = " + daysBetweenWatering +
            "', currentSize = " + currentSize +
            ", lastTimeWatered = " + lastTimeWatered + " }";
    }
}
