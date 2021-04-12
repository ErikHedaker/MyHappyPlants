package model.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @API https://trefle.io/api/v1/plants/search?q=aloe%20vera&token=G0dYZMd8_VZu3_3XLF9iLympM5EUoB7tVePMK_i5dPk
 */

public class PlantAPI {

    private URLConnection request;
    private JsonObject rootObj;
    private String plantSearchTerm;
    private JsonObject result;

    /**
     * Open connection
     * @throws IOException
     */
    private void connect() throws IOException {
        URL url = new URL("https://trefle.io/api/v1/plants/search?q=" + plantSearchTerm + "&token=G0dYZMd8_VZu3_3XLF9iLympM5EUoB7tVePMK_i5dPk");
        request = url.openConnection();
        request.connect();
    }

    /**
     * Reads data from GeoPlugin and creates Json object to navigate to certain information from the web.
     */
    public PlantAPI(String plantSearchTerm) {
        this.plantSearchTerm = plantSearchTerm;
        try {
            connect();

            JsonParser jsonParser = new JsonParser();
            JsonElement root = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
            rootObj = root.getAsJsonObject();
            JsonArray timeSeries = rootObj.get("data").getAsJsonArray();
            result = timeSeries.get(0).getAsJsonObject();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Collects family name from the chosen plant.
     * @return familyName
     */
    public String getFamilyName() {
        String familyName = result.get("family_common_name").getAsString();
        return familyName;
    }

    public static void main(String args[]) {
        System.out.println(new PlantAPI("monstera").getFamilyName());
    }

}
