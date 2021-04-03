package model;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * A class that allows you to collect online information from wikipedia using JWiki API.
 * @version 1.0
 * @author Viktor Johansson
 */
public class JWiki {

    private String displayTitle, imageURL, text = "";
    private final String BASE_URL = "https://en.wikipedia.org/api/rest_v1/page/summary/";

    private boolean hasImage;
    /**
     * Sends information requests to wikipedia with the specified subject
     * and registers some article parts into variables.
     *
     * @param subject The subject you want information about.
     */
    public JWiki(String subject) {
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

            displayTitle = (String) jsonObject.get("displaytitle");

            if (jsonObject.get("originalimage") == null) {
                hasImage = false;
            } else {
                hasImage = true;
                JSONObject jsonObjectOriginalImage = (JSONObject) jsonObject.get("originalimage");
                imageURL = (String) jsonObjectOriginalImage.get("source");
            }

            text = (String) jsonObject.get("extract");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display title of the article.
     *
     * @return displayTitle
     */
    public String getDisplayTitle() {
        String title = displayTitle.replaceAll("<i>", "").replaceAll("</i>", "");
        return title;
    }

    /**
     * Constructs image from the selected article.
     *
     * @return icon
     */
    public ImageIcon getImage() throws IOException {

        if (hasImage) {
            URL url = new URL(imageURL);
            BufferedImage c = ImageIO.read(url);
            return new ImageIcon(c);
        } else {
            return new ImageIcon("./images/plant.jpg");
        }
    }


    /**
     * Attempts to get the raw image data from wikipedia (does not work atm)
     * @author          Erik Hedåker
     * @return          an array of bytes that contain the raw data of an image
     */
    public byte[] getImageRaw() throws IOException
    {
        String extension = imageURL.substring( 1 + imageURL.lastIndexOf( "." ) );
        BufferedImage image = ImageIO.read( new URL( imageURL ) );
        ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
        ImageIO.write( image, extension, baoStream );
        baoStream.flush();
        byte[] imageRaw = baoStream.toByteArray();
        baoStream.close();
        return imageRaw;
    }

    /**
     * This gives you the body text from the article.
     *
     * @return text
     */
    public String getText() {
        return text;
    }
}
