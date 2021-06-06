package model.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @API https://www.geoplugin.com/webservices/json
 * Class not used but kept due to the possibility of further development.
 * @Author Viktor Johansson
 */

public class MyLocation {

    private URLConnection request;
    private JsonObject rootObj;

    /**
     * Open connection
     * @throws IOException
     */
    private void connect() throws IOException {
        URL url = new URL("http://www.geoplugin.net/json.gp?ip=localhost");
        request = url.openConnection();
        request.connect();
    }

    /**
     * Reads data from GeoPlugin and creates Json object to navigate to certain information from the web.
     */
    public MyLocation() {
        try {
            connect();

            JsonParser jsonParser = new JsonParser();
            JsonElement root = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
            rootObj = root.getAsJsonObject();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Collect latitude from geoplugin.
     * @return returns latitude in form of string.
     */
    public String getLatitude() {
        String latitude = rootObj.get("geoplugin_latitude").getAsString();
        return latitude;
    }

    /**
     * Collect longitude from geoplugin.
     * @return returns longitude in form of string.
     */
    public String getLongitude() {
        String latitude = rootObj.get("geoplugin_longitude").getAsString();
        return latitude;
    }

}
