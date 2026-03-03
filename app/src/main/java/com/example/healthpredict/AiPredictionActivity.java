package com.example.healthpredict;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class AiPredictionActivity extends AppCompatActivity {

    private LinearProgressIndicator progressIndicator;
    private TextView tvStatus;
    private int progress = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_prediction);

        ImageView btnBack = findViewById(R.id.btnBack);
        progressIndicator = findViewById(R.id.progressIndicator);
        tvStatus = findViewById(R.id.tvStatus);

        btnBack.setOnClickListener(v -> finish());

        startAnalysis();
    }

    private void startAnalysis() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String sensitivity = prefs.getString("PredictionSensitivity", "Standard (Balanced)");
        
        // Total analysis time will be around 7.5 seconds (150ms * 50 steps)
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progress < 100) {
                    progress += 2; // Slower increment
                    progressIndicator.setProgress(progress);
                    updateStatus(progress, sensitivity);
                    handler.postDelayed(this, 150); // 150ms interval
                } else {
                    tvStatus.setText("Analysis Complete");
                    // Navigate to the next screen
                    startActivity(new Intent(AiPredictionActivity.this, OneYearPredictionActivity.class));
                    finish(); // Finish this activity
                }
            }
        }, 1000);
    }

    private void updateStatus(int progress, String sensitivity) {
        if (progress < 20) {
            tvStatus.setText("Applying " + sensitivity + " standards...");
        } else if (progress < 40) {
            tvStatus.setText("Gathering clinical data...");
        } else if (progress < 70) {
            tvStatus.setText("Running deep learning models...");
        } else if (progress < 90) {
            tvStatus.setText("Synthesizing risk factors...");
        } else {
            tvStatus.setText("Finalizing results...");
        }
    }
}
