package model.api.trefle;

import java.net.URL;
import java.util.ArrayList;

/**Is not used because API taken down. But is kept for the possibility of further development if the API returns.
 * @Author Viktor Johansson
 */

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
