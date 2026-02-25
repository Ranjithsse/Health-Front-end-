package com.example.healthpredict;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;

public class AchievementManager {
    private static final String PREF_NAME = "achievements_prefs";
    private static AchievementManager instance;
    private SharedPreferences prefs;

    public static class Achievement {
        public String id;
        public String title;
        public String description;
        public String dateEarned;
        public boolean isUnlocked;

        public Achievement(String id, String title, String description) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.isUnlocked = false;
            this.dateEarned = "";
        }
    }

    private AchievementManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized AchievementManager getInstance(Context context) {
        if (instance == null) {
            instance = new AchievementManager(context);
        }
        return instance;
    }

    public void unlockAchievement(String id) {
        if (!prefs.getBoolean(id + "_unlocked", false)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(id + "_unlocked", true);
            String date = new java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(new java.util.Date());
            editor.putString(id + "_date", date);
            editor.apply();
        }
    }

    public boolean isUnlocked(String id) {
        return prefs.getBoolean(id + "_unlocked", false);
    }

    public String getDateEarned(String id) {
        return prefs.getString(id + "_date", "-- --");
    }

    // Achievement IDs
    public static final String FIRST_ANALYSIS = "first_analysis";
    public static final String HIGH_ACCURACY = "high_accuracy";
    public static final String POWER_USER = "power_user";
    public static final String RISK_GUARDIAN = "risk_guardian";
}
