package controller;

import model.Plant;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
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

    public static String splitParagraph(String text, int maxLength) {
        int i = 0;
        String[] parts = text.split(Pattern.quote(" "));
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            i++;
            if (i == maxLength) {
                sb.append(p + "\n");
                i = 0;
            } else {
                sb.append(p + " ");
            }
        }
        return sb.toString();
    }

    public static ArrayList<String[]> loadFileCSV(String fileName) {
        ArrayList<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while (line != null) {
                data.add(line.split("\\t", -1));
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
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
            return ChronoUnit.HOURS.between(LocalDateTime.now(), getNextWateringDate(plant));
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
            return plant.getLastTimeWatered().plusHours(plant.getHoursBetweenWatering());
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
