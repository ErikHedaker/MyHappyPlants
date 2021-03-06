package controller;

import model.Plant;
import org.apache.commons.codec.binary.Base64;

import javax.swing.*;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * A Utility class for all static methods that have no state
 *
 * @author Viktor Johansson, Erik Hedåker
 */
public class Utility {
    public static String centerText(String text, int maxWidth) {
        if (maxWidth == 0) {
            maxWidth = 80;
        }
        int spaces = (int) Math.round((maxWidth - 1.4 * text.length()) / 2);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < spaces; i++) {
            sb.append(" ");
        }
        sb.append(text);
        return sb.toString();
    }

    public static int getStringToInt(String value) {
        int number = 0;
        try {
            number = Integer.valueOf(value);
        } catch (NumberFormatException e) {
        }
        return number;
    }

    public static File OpenFileChooser( )
    {
        JFileChooser fileChooser = new JFileChooser( );
        fileChooser.setCurrentDirectory( new File( System.getProperty( "user.home" ) ) );
        int result = fileChooser.showOpenDialog( new JDialog( ) );
        if( result == JFileChooser.APPROVE_OPTION )
        {
            return fileChooser.getSelectedFile( );
        }
        return null;
    }

    public static String getPlantMoodStr(long dayValue) {
        String plantVibe = "Water me in: " + dayValue + " day(s)";
        if (dayValue <= 0) {
            plantVibe = Utility.centerText("I'm thirsty! :'c", 50);
            if (dayValue <= 5) {
                plantVibe = Utility.centerText("I'm dying, farewell..", 40);
            }
        }
        return plantVibe;
    }

    public static HashMap<String, String> getShortestValue(ArrayList<HashMap<String, String>> values, String key){
        HashMap<String, String> shortest = values.get(0);
        for (HashMap<String, String> value : values) {
            if (value.get(key).length() < shortest.get(key).length()) {
                shortest = value;
            }
        }
        return shortest;
    }

    public static String getMatchingString(ArrayList<String> values, String target){
        int distance = values.size();
        String nearestString = null;
        for (String compareValue : values) {
            int currentDistance = Arrays.compare(compareValue.toCharArray(), target.toCharArray());
            if (currentDistance < distance) {
                distance = currentDistance;
                nearestString = compareValue;
            }
        }
        return nearestString;
    }

    public static HashMap<String, String> getMatchingStringHashMap(ArrayList<HashMap<String, String>> values, HashMap<String, String> target){
        int distance = values.size();
        HashMap<String, String> nearestHashMap = null;
        for (HashMap<String, String> compareValue : values) {
            int currentDistance = Arrays.compare(compareValue.get("common_name").toCharArray(), target.get("common_name").toCharArray());
            if (currentDistance < distance) {
                distance = currentDistance;
                nearestHashMap = compareValue;
            }
        }
        return nearestHashMap;
    }

    public static String getShortStr(String text) {
        int i = 0;
        String[] parts = text.split(Pattern.quote(" "));
        String fixedText = "";
        for (String p : parts) {
            if (i < 2) {
                fixedText += " " + p;
                i++;
            } else {
                fixedText += "...";
            }
        }
        return fixedText;
    }

    /**
     * Calculates the amount of hours left from when the plant has to be watered, using difference between
     * the current date and the method getNextWateringDate
     *
     * @param plant A specific Plant from the list of plants that the active profile has
     * @return The amount of hours left
     */
    public static long getNextWateringCountdown(Plant plant) {
        try {
            return ChronoUnit.DAYS.between(LocalDateTime.now(), getNextWateringDate(plant)) + 1;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    /**
     * Calculates the next date for when the plant has to be watered, using the attributes from the plant
     *
     * @param plant A specific Plant from the list of plants that the active profile has
     * @return A LocalDateTime which is the specific date in the future when the plant has to be watered
     */
    public static LocalDateTime getNextWateringDate(Plant plant) {
        try {
            return plant.getLastTimeWatered().plusDays(plant.getDaysBetweenWatering());
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Generates a random array of bytes called a salt to be used to password hashing
     *
     * @param size An integer for the size of the byte array
     * @return A byte array of random values
     */
    public static byte[] generateRandomSalt(int size) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[size];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Generates a hashed password based on the input string and salt, using the SHA-512 algorithm
     *
     * @param password A String for the password that is being hashed
     * @param salt A byte array of random values that obfuscate the hashed password
     * @return A byte array representing the hashed password, encoded in Base64 format
     */
    public static byte[] generatePasswordHash(String password, byte[] salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);
            return messageDigest.digest(stringToByte(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Encodes a String to a byte array in the Base64 format
     *
     * @param input A String input to be encoded
     * @return A byte array encoded in Base64 format
     */
    public static byte[] stringToByte(String input) {
        if (Base64.isBase64(input)) {
            return Base64.decodeBase64(input);
        } else {
            return Base64.encodeBase64(input.getBytes());
        }
    }

    /**
     * Decodes byte array in the Base64 format to a String
     *
     * @param input A byte array input to be decoded
     * @return A String decoded from Base64 format
     */
    public static String byteToString(byte[] input) {
        return Base64.encodeBase64String(input);
    }
}
