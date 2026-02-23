package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class AiPredictionActivity extends AppCompatActivity {

    private LinearProgressIndicator progressBar;
    private TextView tvDescription;
    private int progressStatus = 0;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_prediction);

        if (findViewById(R.id.btnBack) != null) {
            findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        }

        progressBar = findViewById(R.id.progressBar);
        tvDescription = findViewById(R.id.tvDescription);

        startAnalyzing();
    }

    private void startAnalyzing() {
        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus += 2;
                handler.post(() -> {
                    if (progressBar != null) {
                        progressBar.setProgress(progressStatus);
                    }
                    updateStatusText(progressStatus);
                });
                try {
                    Thread.sleep(60); // Faster progress for better UX
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            handler.post(() -> {
                // Navigate to the next step in the flow
                Intent intent = new Intent(AiPredictionActivity.this, OneYearPredictionActivity.class);
                startActivity(intent);
                finish();
            });
        }).start();
    }

    private void updateStatusText(int status) {
        if (tvDescription == null) return;
        if (status < 25) {
            tvDescription.setText("Gathering clinical data...");
        } else if (status < 50) {
            tvDescription.setText("Extracting health features...");
        } else if (status < 75) {
            tvDescription.setText("Analyzing risk patterns...");
        } else {
            tvDescription.setText("Finalizing health prediction...");
        }
    }
}
