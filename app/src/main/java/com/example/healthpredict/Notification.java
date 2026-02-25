package com.example.healthpredict;

public class Notification {
    public enum Type {
        SUCCESS, INFO, ALERT
    }

    private String title;
    private String description;
    private String time;
    private Type type;

    public Notification(String title, String description, String time, Type type) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.type = type;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getTime() { return time; }
    public Type getType() { return type; }
}