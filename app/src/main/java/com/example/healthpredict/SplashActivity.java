package com.example.healthpredict;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthpredict.network.RetrofitClient;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(this::checkSession, 2000);
    }

    private void checkSession() {
        SharedPreferences prefs = getSharedPreferences("HealthPredictPrefs", MODE_PRIVATE);
        String token = prefs.getString("auth_token", null);

        if (token == null) {
            navigateToGetStarted();
            return;
        }

        RetrofitClient.getApiService().checkSession().enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Object> userData = (Map<String, Object>) response.body().get("user");
                    if (userData != null) {
                        String role = (String) userData.get("role");
                        navigateToHome(role);
                    } else {
                        navigateToGetStarted();
                    }
                } else {
                    navigateToGetStarted();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                navigateToGetStarted();
            }
        });
    }

    private void navigateToHome(String role) {
        Intent intent;
        if ("PROVIDER".equals(role)) {
            intent = new Intent(SplashActivity.this, DoctorHomeActivity.class);
        } else {
            // Simplified: default to GetStarted flow for other roles until specific homes
            // are built
            intent = new Intent(SplashActivity.this, GetStartedActivity.class);
        }
        startActivity(intent);
        finish();
    }

    private void navigateToGetStarted() {
        startActivity(new Intent(SplashActivity.this, GetStartedActivity.class));
        finish();
    }
}