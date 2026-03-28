package com.example.healthpredict;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LocalNotificationManager {
    private static final String PREF_NAME = "HealthPredictNotifications";
    private static final String KEY_NOTIFICATIONS = "notifications_list";
    private static LocalNotificationManager instance;
    private final SharedPreferences prefs;
    private final Gson gson;

    private LocalNotificationManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static synchronized LocalNotificationManager getInstance(Context context) {
        if (instance == null) {
            instance = new LocalNotificationManager(context);
        }
        return instance;
    }

    public void addNotification(Notification notification) {
        List<Notification> notifications = getNotifications();
        notification.setRead(false); // Mark as unread
        notifications.add(0, notification);
        
        if (notifications.size() > 50) {
            notifications = notifications.subList(0, 50);
        }
        
        saveNotifications(notifications);
    }

    public int getUnreadCount() {
        List<Notification> notifications = getNotifications();
        int count = 0;
        for (Notification n : notifications) {
            if (!n.isRead()) count++;
        }
        return count;
    }

    public void markAllAsRead() {
        List<Notification> notifications = getNotifications();
        for (Notification n : notifications) {
            n.setRead(true);
        }
        saveNotifications(notifications);
    }

    public List<Notification> getNotifications() {
        String json = prefs.getString(KEY_NOTIFICATIONS, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<Notification>>() {}.getType();
        List<Notification> list = gson.fromJson(json, type);
        return list != null ? list : new ArrayList<>();
    }

    private void saveNotifications(List<Notification> notifications) {
        String json = gson.toJson(notifications);
        prefs.edit().putString(KEY_NOTIFICATIONS, json).apply();
    }
}
