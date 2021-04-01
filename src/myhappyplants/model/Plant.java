package myhappyplants.model;

import javax.swing.*;
import java.time.LocalDateTime;

public class Plant
{
    private String nameAlias;
    private String nameWiki;
    private ImageIcon imageIcon;
    private String description;
    private int hoursBetweenWatering;
    private LocalDateTime lastTimeWatered;

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

    public LocalDateTime getLastTimeWatered() {
        return lastTimeWatered;
    }

    public int getHoursBetweenWatering() {
        return hoursBetweenWatering;
    }

    public String getNameAlias() {
        return nameAlias;
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
        return "Plant{" +
                "nameAlias='" + nameAlias + '\'' +
                ", nameWiki='" + nameWiki + '\'' +
                ", hoursBetweenWatering=" + hoursBetweenWatering +
                ", wateringHappenedLast=" + lastTimeWatered +
                '}';
    }
}
