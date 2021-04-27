package model.api.trefle;

import java.net.URL;
import java.util.ArrayList;

public class TreflePlant {

    public ArrayList<Plant> data;

    public Plant getPlant()  {
        try {
            return data.get(0);
        }
        catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    class Plant {
        public String common_name;
        public String scientific_name;
        public ArrayList<String> synonyms;
        public String image_url;
    }
}
