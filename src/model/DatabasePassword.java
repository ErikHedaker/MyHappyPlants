package model;

import controller.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DatabasePassword {
    private String fileName;

    public DatabasePassword(String fileName) {
        this.fileName = fileName;
    }
    public DatabasePassword() {
        this("DatabasePassword.txt");
    }

    public void writePassword(String password) {
        try {
            byte[] salt = Utility.stringToByte(fileName);
            byte[] passwordHash = Utility.generatePasswordHash(password, salt);
            File file = new File(fileName);
            file.createNewFile();
            FileOutputStream fileOS = new FileOutputStream(file, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readPassword(String password) {
        try {
            byte[] salt = Utility.stringToByte(fileName);
            byte[] passwordHash = Utility.generatePasswordHash(password, salt);
            File file = new File(fileName);
            file.createNewFile();
            FileOutputStream fileOS = new FileOutputStream(file, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DatabasePassword().writePassword("Test");
    }
}
