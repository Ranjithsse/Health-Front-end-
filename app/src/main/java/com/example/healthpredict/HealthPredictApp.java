package com.example.healthpredict;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

public class HealthPredictApp extends Application {

    private static final Set<String> EXCLUDED_ACTIVITIES = new HashSet<>();

    static {
        EXCLUDED_ACTIVITIES.add(SplashActivity.class.getName());
        EXCLUDED_ACTIVITIES.add(GetStartedActivity.class.getName());
        EXCLUDED_ACTIVITIES.add(DoctorLoginActivity.class.getName());
        EXCLUDED_ACTIVITIES.add(DoctorSignUpActivity.class.getName());
        EXCLUDED_ACTIVITIES.add(DoctorPassResetActivity.class.getName());
        EXCLUDED_ACTIVITIES.add(DoctorCheckEmailActivity.class.getName());
        EXCLUDED_ACTIVITIES.add(DoctorSetNewPasswordActivity.class.getName());
        EXCLUDED_ACTIVITIES.add(RoleSelectionActivity.class.getName());
        EXCLUDED_ACTIVITIES.add(KeyFeaturesActivity.class.getName());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {}

            @Override
            public void onActivityStarted(@NonNull Activity activity) {}

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                String className = activity.getClass().getName();
                if (!EXCLUDED_ACTIVITIES.contains(className)) {
                    SharedPreferences prefs = getSharedPreferences("HealthPredictPrefs", MODE_PRIVATE);
                    prefs.edit().putString("last_activity", className).apply();
                }
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {}

            @Override
            public void onActivityStopped(@NonNull Activity activity) {}

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {}

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {}
        });
    }
}
