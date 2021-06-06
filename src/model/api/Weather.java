package model.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
Class not used but kept due to the possibility of further development.
 * @Author Viktor Johansson
 */

public class Weather extends MyLocation {

    private URLConnection request;
    private JsonObject rootObj;
    private JsonArray currentWeather;

    /**
     * Open connection for ip location
     * @throws IOException
     */
    private void connect() throws IOException {


        System.out.println("lat/" + getLatitude() + " long/" + getLongitude());

        URL url = new URL("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/"
                + getLongitude() + "/lat/" + getLatitude() + "/data.json");
        request = url.openConnection();
        request.connect();
    }

    /**
     * Reads data from SMHI and creates Json object to navigate to certain information from the web.
     */
    public Weather() {
        try {
            connect();

            JsonParser jsonParser = new JsonParser();
            JsonElement root = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
            rootObj = root.getAsJsonObject();

            JsonArray timeSeries = rootObj.get("timeSeries").getAsJsonArray();
            currentWeather = timeSeries.get(0).getAsJsonObject().get("parameters").getAsJsonArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return navigates in json object to the "values" key and collects humidity.
     */
    public int getHumidity() {
        return currentWeather.get(5).getAsJsonObject().get("values").getAsJsonArray().get(0).getAsInt();
    }

    /**
     * @return navigates in json object to the "values" key and collects celsius.
     */
    public int getCelsius() {
        return currentWeather.get(0).getAsJsonObject().get("values").getAsJsonArray().get(0).getAsInt();
    }


    public static void main(String[] args) {

        Weather weather = new Weather();

        System.out.println(weather.getHumidity() + "%");
        System.out.println(weather.getCelsius() + "°C");
    }
}
