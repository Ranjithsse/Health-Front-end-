package com.example.healthpredict;

public class Notification {

    private String title;
    @com.google.gson.annotations.SerializedName("message")
    private String description;
    @com.google.gson.annotations.SerializedName("created_at_human")
    private String time;
    private String type; // SUCCESS, INFO, ALERT
    private boolean isRead = false;

    public Notification(String title, String description, String time, String type) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}