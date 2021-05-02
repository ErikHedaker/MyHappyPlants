package model.api.trefle;

import com.google.gson.*;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import controller.Controller;
import model.api.Buffer;

import java.io.*;
import java.net.URL;

/**
 * @API https://trefle.io/api/v1/plants/search?q=aloe%20vera&token=G0dYZMd8_VZu3_3XLF9iLympM5EUoB7tVePMK_i5dPk
 */

public class PlantAPI {

    private final OkHttpClient client = new OkHttpClient();
    public static Buffer<TreflePlant> buffer = new Buffer<>();
    private Controller controller;

    /**
     * Reads data from GeoPlugin and creates Json object to navigate to certain information from the web.
     */
    public PlantAPI(Controller controller) {
        this.controller = controller;
    }

    public void beginSearch(String plantName) {
        Request request = new Request.Builder()
                .url("https://trefle.io/api/v1/plants/search?q=" + plantName + "&token=G0dYZMd8_VZu3_3XLF9iLympM5EUoB7tVePMK_i5dPk")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                controller.showConnectivityError();
                System.out.println("API down");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();

                    TreflePlant plant = gson.fromJson(response.body().charStream(), TreflePlant.class);
                    buffer.put(plant);
                }
            }
        });
    }

    /**
     * Collects family name from the chosen plant.
     * @return familyName
     */

    public String getPlantAlias() {
        try {
            return buffer.get().getPlant().common_name;
        } catch (InterruptedException | NullPointerException e) {
            if (e instanceof InterruptedException) {
                System.out.println("API down");
            }
            return "Plant not found.";
        }
    }

    public String getScientificName() {
        try {
            return buffer.get().getPlant().scientific_name;
        } catch (InterruptedException | NullPointerException e) {
            if (e instanceof InterruptedException) {
                System.out.println("API down");
            }
            return "Plant not found.";
        }
    }

    public String getImageURL() {
        try {
            return buffer.get().getPlant().image_url;
        } catch (InterruptedException e) {
            return null;
        }
    }
}
