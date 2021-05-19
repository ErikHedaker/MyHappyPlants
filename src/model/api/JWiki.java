package model.api;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * A class that allows you to collect online information from wikipedia using JWiki API.
 * @version 1.0
 * @author Viktor Johansson
 */
public class JWiki {
    private final String BASE_URL = "https://en.wikipedia.org/api/rest_v1/page/summary/";
    private String displayTitle;
    private String imageURL;
    private String text;

    /**
     * Sends information requests to wikipedia with the specified subject
     * and registers some article parts into variables.
     *
     * @param subject The subject you want information about.
     */
    public JWiki(String subject) {
        this.displayTitle = "";
        this.imageURL = "";
        this.text = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + subject)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            String data = response.body().string();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(data);

            displayTitle = (String)jsonObject.get("displaytitle");

            if (jsonObject.get("originalimage") != null) {
                JSONObject jsonObjectOriginalImage = (JSONObject) jsonObject.get("originalimage");
                imageURL = (String) jsonObjectOriginalImage.get("source");
            }

            text = (String) jsonObject.get("extract");
            displayTitle = displayTitle != null ? displayTitle.replaceAll("<i>", "").replaceAll("</i>", "") : "";
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display title of the article.
     *
     * @return returns the wikipedia title from chosen article.
     */
    public String getDisplayTitle() {
        return displayTitle;
    }

    /**
     * Collects image url from wikipage.
     *
     * @return returns an image url.
     */
    public String getImageURL()
    {
        return imageURL;
    }

    /**
     * This gives you the body text from the article.
     *
     * @return text from wikipedia article.
     */
    public String getText() {
        return text;
    }

    public boolean valid() {
        return displayTitle != "" && text != "" && !text.contains("may refer to:");
    }
}
