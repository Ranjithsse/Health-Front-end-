package com.example.healthpredict;

import com.google.gson.annotations.SerializedName;

public class Notification {

    private int id;
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("created_at_human")
    private String time;
    private String type; // SUCCESS, INFO, ALERT
    
    @SerializedName("is_read")
    private boolean isRead = false;
    
    @SerializedName("related_id")
    private int relatedId;

    public Notification(int id, String title, String description, String time, String type, int relatedId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.type = type;
        this.relatedId = relatedId;
    }

    public Notification(String title, String description, String time, String type) {
        this.id = (int) (System.currentTimeMillis() % 100000);
        this.title = title;
        this.description = description;
        this.time = time;
        this.type = type;
        this.relatedId = 0;
    }

    public int getId() {
        return id;
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

    public int getRelatedId() {
        return relatedId;
    }
}