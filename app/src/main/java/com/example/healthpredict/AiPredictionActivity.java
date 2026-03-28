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

import com.example.healthpredict.network.ApiService;
import com.example.healthpredict.network.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class AiPredictionActivity extends AppCompatActivity {

    private LinearProgressIndicator progressIndicator;
    private TextView tvStatus;
    private int progress = 0;
    private long startTime;
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
        CaseData caseData = CaseData.getInstance();
        int caseId = caseData.id;

        if (caseId == 0) {
            tvStatus.setText("Error: No Case ID found.");
            return;
        }

        startTime = System.currentTimeMillis();

        // Simulate progress for UI feel while API runs
        // We want to reach ~95% in about 6.5 seconds
        // 150ms * 43 steps = ~6.45 seconds
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progress < 95) {
                    progress += 2; // Slower progress
                    progressIndicator.setProgress(progress);
                    updateStatus(progress, "Standard (Balanced)");
                    handler.postDelayed(this, 130);
                }
            }
        }, 500);

        // Actual API Call
        ApiService apiService = RetrofitClient.getRetrofitInstance(this).create(ApiService.class);
        apiService.predictCase(caseId).enqueue(new retrofit2.Callback<CaseData>() {
            @Override
            public void onResponse(retrofit2.Call<CaseData> call, retrofit2.Response<CaseData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Update singleton with real results
                    CaseData.getInstance().copyFrom(response.body());

                    // Complete progress and navigate
                    progressIndicator.setProgress(100);
                    tvStatus.setText("Analysis Complete");

                    long elapsedTime = System.currentTimeMillis() - startTime;
                    long remainingTime = 7000 - elapsedTime;

                    if (remainingTime < 500) remainingTime = 500; // Minimum 500ms at 100%

                    handler.postDelayed(() -> {
                        startActivity(new Intent(AiPredictionActivity.this, OneYearPredictionActivity.class));
                        finish();
                    }, remainingTime);
                } else {
                    tvStatus.setText("Analysis failed: " + response.message());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<CaseData> call, Throwable t) {
                tvStatus.setText("Network error: " + t.getMessage());
            }
        });
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
