package com.example.healthpredict;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Calendar;

public class StatsManager {
    private static StatsManager instance;
    private static final String PREF_NAME = "HealthPredictStats";
    
    private StatsManager() {}

    public static synchronized StatsManager getInstance() {
        if (instance == null) {
            instance = new StatsManager();
        }
        return instance;
    }

    private String getMonthlyKey(String userEmail) {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        // Normalize email to be safe for keys
        String safeEmail = userEmail != null ? userEmail.replace("@", "_").replace(".", "_") : "anonymous";
        return "cases_" + year + "_" + month + "_" + safeEmail;
    }

    public void incrementMonthlyCount(Context context, String userEmail) {
        if (context == null) return;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String key = getMonthlyKey(userEmail);
        int currentCount = prefs.getInt(key, 0);
        prefs.edit().putInt(key, currentCount + 1).apply();
    }

    public int getMonthlyCount(Context context, String userEmail) {
        if (context == null) return 0;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(getMonthlyKey(userEmail), 0);
    }
    
    public void clearAllStats(Context context) {
        if (context == null) return;
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply();
    }
}
