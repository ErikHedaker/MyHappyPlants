package com.company;

import javax.swing.*;
import java.util.Date;

public class Plant {

    private String name;
    private ImageIcon icon;
    private String description;
    private Date wateringDate;
    private Date reminderDate;
    private int daysLeft;
    private int daysAgo;
    private int daysWithoutWater;

    public Plant(String name, ImageIcon icon, String description) {
        this.description = description;
        this.name = name;
        this.icon = icon;
        if (daysLeft == 0)
            daysLeft = 6;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public int getDays_ago() {
        return daysAgo;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public void setDaysWithoutWater(int daysWithoutWater) {
        this.daysWithoutWater = daysWithoutWater;
    }

    public int getDaysWithoutWater() {
        return daysWithoutWater;
    }

    public int getDaysAgo() {
        return daysAgo;
    }

    public void setWateringDate(Date wateringDate) {
        this.wateringDate = wateringDate;
    }

    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    public Date getWateringDate() {
        return wateringDate;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
