package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * Class is made for encryption.
 *
 * @author Erik Hedåker
 */

public class SimpleEncryption {
    private String fileName;
    private Cipher cipher;
    private PBEParameterSpec pbeParamSpec;
    private SecretKey pbeKey;

    public SimpleEncryption(String fileName) {
        try {
            this.fileName = fileName;
            String algorithm = "PBEWithMD5AndDES";
            byte[] salt = {
                    (byte)0x0d,
                    (byte)0x2d,
                    (byte)0xc5,
                    (byte)0x82,
                    (byte)0xb3,
                    (byte)0xda,
                    (byte)0x73,
                    (byte)0xfa
            };
            this.pbeParamSpec = new PBEParameterSpec(salt, 20);
            PBEKeySpec pbeKeySpec = new PBEKeySpec(fileName.toCharArray());
            SecretKeyFactory keyFac = SecretKeyFactory.getInstance(algorithm);
            this.pbeKey = keyFac.generateSecret(pbeKeySpec);
            this.cipher = Cipher.getInstance(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public SimpleEncryption() {
        this("SimpleEncryption.txt");
    }

    public String encrypt(String plainText) throws Exception {
        byte[] plainTextByte = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }

    public String decrypt(String encryptedText) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        cipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }

    public void writeFile() {
        try {
            File file = new File(fileName);
            file.createNewFile();
            System.out.print("Enter string: ");
            String password = new Scanner(System.in).nextLine();
            FileOutputStream fileOS = new FileOutputStream(file, false);
            fileOS.write(encrypt(password).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFile() {
        try {
            File file = new File(fileName);
            FileInputStream fileIS = new FileInputStream(file);
            return decrypt(new String(fileIS.readAllBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        new SimpleEncryption().writeFile();
    }
}