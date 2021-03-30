package model;

import java.time.LocalDateTime;

public class Plant
{
    int id;
    String nameAlias;
    String nameCommon;
    String nameScientific;
    String wikipediaLink;
    String description;
    Long intervalBetweenWatering;
    LocalDateTime wateringHappenedLast;

    public void setId( int id )
    {
        this.id = id;
    }

    public void setNameAlias( String nameAlias )
    {
        this.nameAlias = nameAlias;
    }

    public void setNameCommon( String nameCommon )
    {
        this.nameCommon = nameCommon;
    }

    public void setNameScientific( String nameScientific )
    {
        this.nameScientific = nameScientific;
    }

    public void setWikipediaLink( String wikipediaLink )
    {
        this.wikipediaLink = wikipediaLink;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public void setIntervalBetweenWatering( Long intervalBetweenWatering )
    {
        this.intervalBetweenWatering = intervalBetweenWatering;
    }

    public void setWateringHappenedLast( LocalDateTime wateringHappenedLast )
    {
        this.wateringHappenedLast = wateringHappenedLast;
    }

    @Override
    public String toString( )
    {
        return "Plant{" +
                "id=" + id +
                ", nameAlias='" + nameAlias + '\'' +
                ", nameCommon='" + nameCommon + '\'' +
                ", nameScientific='" + nameScientific + '\'' +
                ", wikipediaLink='" + wikipediaLink + '\'' +
                ", description='" + description + '\'' +
                ", intervalBetweenWatering=" + intervalBetweenWatering +
                ", wateringHappenedLast=" + wateringHappenedLast +
                '}';
    }
}
