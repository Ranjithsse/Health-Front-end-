package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class AiPredictionActivity extends AppCompatActivity {

    private CaseData caseData;
    private LinearProgressIndicator progressIndicator;
    private TextView tvStatus;
    private LinearLayout resultContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_prediction);

        caseData = CaseData.getInstance();

        progressIndicator = findViewById(R.id.progressIndicator);
        tvStatus = findViewById(R.id.tvStatus);
        resultContainer = findViewById(R.id.resultContainer);

        startPredictionSimulation();
    }

    private void startPredictionSimulation() {
        // Simulation of AI processing
        new Handler().postDelayed(() -> {
            if (tvStatus != null) tvStatus.setText("Analyzing Patient Demographics...");
            if (progressIndicator != null) progressIndicator.setProgress(30, true);
        }, 1000);

        new Handler().postDelayed(() -> {
            if (tvStatus != null) tvStatus.setText("Processing Medical History...");
            if (progressIndicator != null) progressIndicator.setProgress(60, true);
        }, 2000);

        new Handler().postDelayed(() -> {
            if (tvStatus != null) tvStatus.setText("Calculating Risk Scores...");
            if (progressIndicator != null) progressIndicator.setProgress(90, true);
        }, 3000);

        new Handler().postDelayed(() -> {
            if (progressIndicator != null) progressIndicator.setProgress(100, true);
            generateResults();
            navigateToOneYearPrediction();
        }, 4000);
    }

    private void generateResults() {
        // Mocking AI Results
        caseData.riskScore = "98.2%";
        caseData.riskLevel = "Low";
        caseData.accuracy = "99.1%";
        caseData.aiInsight = "Patient shows high stability probability based on current health markers and 1-year outlook.";
    }

    private void navigateToOneYearPrediction() {
        if (tvStatus != null) tvStatus.setText("Analysis Complete");
        
        // Auto-navigate to 1-Year Prediction after a very brief pause to show 'Complete'
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(AiPredictionActivity.this, OneYearPredictionActivity.class);
            startActivity(intent);
            finish(); // Finish this activity so user doesn't go back to the 'analyzing' screen
        }, 500);
    }
}
