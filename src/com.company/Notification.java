package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * A class that allows you to display messages on the down-right side on windows.
 * @version 1.0
 * @author Viktor Johansson
 */
public class Notification {

    private TrayIcon icon;
    private String title;
    private String message;

    /**
     * Constructs simple notifications.
     * @param title title
     * @param message message to display
     */
    public Notification(String title, String message) {
        icon = new TrayIcon(Toolkit.getDefaultToolkit().createImage("icon.png"));
        icon.setImageAutoSize(true);
        this.message = message;
        this.title = title;
    }

    /**
     * Constructs notifications with images from specified file-location.
     * @param imagePath file-location
     * @param title title
     * @param message message to display
     */
    public Notification(String imagePath, String title, String message) {
        icon = new TrayIcon(new ImageIcon(imagePath).getImage(), "tip");
        icon.setImageAutoSize(true);
        this.message = message;
        this.title = title;
    }

    /**
     * Controls if system notifications is supported and displays notifications.
     */
    public void display() {
        SystemTray tray = SystemTray.getSystemTray();
        if (tray.isSupported()) {
            try {
                tray.add(icon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
            icon.displayMessage(title, message, TrayIcon.MessageType.NONE);
        }
    }
}
