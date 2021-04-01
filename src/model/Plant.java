package model;

import java.time.LocalDateTime;

public class Plant
{
    private String nameAlias;
    private String nameWiki;
    private int hoursBetweenWatering;
    private LocalDateTime wateringHappenedLast;

    public void setNameAlias( String nameAlias )
    {
        this.nameAlias = nameAlias;
    }
    public void setNameWiki( String nameWiki )
    {
        this.nameWiki = nameWiki;
    }
    public void setHoursBetweenWatering( int hoursBetweenWatering )
    {
        this.hoursBetweenWatering = hoursBetweenWatering;
    }
    public void setWateringHappenedLast( LocalDateTime wateringHappenedLast )
    {
        this.wateringHappenedLast = wateringHappenedLast;
    }

    @Override
    public String toString( )
    {
        return
            "Plant { nameAlias = '" + nameAlias +
            "', nameWiki = '" + nameWiki +
            "', hoursBetweenWatering = " + hoursBetweenWatering +
            ", wateringHappenedLast = " + wateringHappenedLast + " }";
    }
}
