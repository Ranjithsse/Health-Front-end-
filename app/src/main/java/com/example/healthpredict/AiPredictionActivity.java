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
import com.example.healthpredict.network.PredictionResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AiPredictionActivity extends AppCompatActivity {

    private LinearProgressIndicator progressIndicator;
    private TextView tvStatus;
    private float progress = 0;
    private long startTime;
    private Handler handler = new Handler();
    private boolean isAnalysisComplete = false;

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
        isAnalysisComplete = false;

        // Progress simulation: reaches 95% in 7.5 seconds
        // (7500ms / 50ms) = 150 steps. 95 / 150 = 0.633 increment
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long elapsed = System.currentTimeMillis() - startTime;
                if (elapsed < 7500) {
                    progress = (elapsed / 7500f) * 95f;
                    progressIndicator.setProgress((int) progress);
                    updateStatus((int) progress, "Standard (Balanced)");
                    handler.postDelayed(this, 50);
                } else if (!isAnalysisComplete) {
                    // Stay at 95% until API is done
                    progress = 95f;
                    progressIndicator.setProgress(95);
                    tvStatus.setText("Finalizing deep analysis...");
                    handler.postDelayed(this, 100);
                } else {
                    // API is done and we spent at least 7.5s
                    // Smoothly finish the last 5% in 1 second
                    finishAnalysisSmoothly();
                }
            }
        }, 100);

        // Actual API Call
        ApiService apiService = RetrofitClient.getRetrofitInstance(this).create(ApiService.class);
        apiService.predictCase(caseId).enqueue(new Callback<PredictionResponse>() {
            @Override
            public void onResponse(Call<PredictionResponse> call, Response<PredictionResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().caseData != null) {
                    // Update singleton with real results immediately
                    CaseData.getInstance().copyFrom(response.body().caseData);
                    isAnalysisComplete = true; 
                    // The progress loop will eventually call finishAnalysisSmoothly()
                } else {
                    isAnalysisComplete = true;
                    tvStatus.setText("Analysis failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PredictionResponse> call, Throwable t) {
                isAnalysisComplete = true;
                tvStatus.setText("Network error: " + t.getMessage());
            }
        });
    }

    private void finishAnalysisSmoothly() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (progress < 100) {
                    progress += 1.0f;
                    progressIndicator.setProgress((int) progress);
                    handler.postDelayed(this, 100); // Takes another 500ms to reach 100
                } else {
                    tvStatus.setText("Analysis Complete");
                    handler.postDelayed(() -> {
                        startActivity(new Intent(AiPredictionActivity.this, OneYearPredictionActivity.class));
                        finish();
                    }, 1000); // 1s final pause
                }
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
