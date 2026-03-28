package com.example.healthpredict;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    private static HistoryManager instance;
    private List<CaseData> caseHistory = new ArrayList<>();
    private static final String PREF_NAME = "HealthPredictHistory";
    private static final String KEY_HISTORY = "case_history";

    private HistoryManager() {
    }

    public static synchronized HistoryManager getInstance() {
        if (instance == null) {
            instance = new HistoryManager();
        }
        return instance;
    }

    public void init(Context context) {
        loadHistory(context);
    }

    public void addCase(Context context, CaseData caseData) {
        // Clone the case data using the copy constructor/method to ensure all fields are captured
        CaseData newCase = new CaseData();
        newCase.copyFrom(caseData);

        // Check for duplicates based on patientId and date (or id if available)
        int existingIndex = -1;
        for (int i = 0; i < caseHistory.size(); i++) {
            CaseData existing = caseHistory.get(i);
            if (existing.id != 0 && caseData.id != 0 && existing.id == caseData.id) {
                existingIndex = i;
                break;
            } else {
                String existingKey = existing.patientId + "_" + existing.date;
                String newKey = caseData.patientId + "_" + caseData.date;
                if (existingKey.equals(newKey)) {
                    existingIndex = i;
                    break;
                }
            }
        }

        if (existingIndex != -1) {
            // Remove the old entry so we can replace it at the top
            caseHistory.remove(existingIndex);
        }

        // Add to the beginning of the list for "Recent" effect
        caseHistory.add(0, newCase);
        
        // Save dynamically
        saveHistory(context);

        // Update persistent monthly statistics
        // if (existingIndex == -1) {
        //     SharedPreferences userPrefs = context.getSharedPreferences("HealthPredictPrefs", Context.MODE_PRIVATE);
        //     String userEmail = userPrefs.getString("user_email", "anonymous");
        //     StatsManager.getInstance().incrementMonthlyCount(context, userEmail);
        // }
    }

    public List<CaseData> getCaseHistory() {
        return caseHistory;
    }

    public void clearHistory(Context context) {
        caseHistory.clear();
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            prefs.edit().clear().apply();
        }
    }

    private void saveHistory(Context context) {
        if (context == null) return;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(caseHistory);
        editor.putString(KEY_HISTORY, json);
        editor.apply();
    }

    private void loadHistory(Context context) {
        if (context == null) return;
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(KEY_HISTORY, null);
        Type type = new TypeToken<ArrayList<CaseData>>() {}.getType();
        
        if (json != null) {
            caseHistory = deduplicateCases(gson.fromJson(json, type));
            // Trigger a silent save so the scrubbed history permanently overwrites the old dirty JSON array on disk
            saveHistory(context);
        } else {
            caseHistory = new ArrayList<>();
        }
    }

    private List<CaseData> deduplicateCases(List<CaseData> inputList) {
        if (inputList == null) return new ArrayList<>();
        
        java.util.Map<String, CaseData> map = new java.util.LinkedHashMap<>();
        
        // Reverse iteration to prioritize keeping the LATEST cases if there are duplicates without status difference
        for (int i = inputList.size() - 1; i >= 0; i--) {
            CaseData data = inputList.get(i);
            String key = (data.patientId != null ? data.patientId : String.valueOf(data.id)) + "_" + data.date;
            
            if (map.containsKey(key)) {
                CaseData existing = map.get(key);
                if (!"Completed".equalsIgnoreCase(existing.status) && "Completed".equalsIgnoreCase(data.status)) {
                    map.put(key, data);
                } else if (!"Completed".equalsIgnoreCase(existing.status) && existing.id < data.id) {
                    map.put(key, data);
                }
            } else {
                map.put(key, data);
            }
        }
        
        List<CaseData> result = new ArrayList<>(map.values());
        java.util.Collections.reverse(result);
        return result;
    }
}
