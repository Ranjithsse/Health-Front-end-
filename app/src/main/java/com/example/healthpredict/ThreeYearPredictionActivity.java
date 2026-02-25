package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ThreeYearPredictionActivity extends AppCompatActivity {

    private CaseData caseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_year_prediction);

        caseData = CaseData.getInstance();

        // Save 3-year data to CaseData for the Final Report
        caseData.threeYearPrediction = "85.5%";
        caseData.threeYearRisk = "Moderate";

        // Toolbar Back Button
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            // Standard back navigation (returns to OneYearPredictionActivity)
            finish();
        });

        // Footer Back Button
        if (findViewById(R.id.btnBackFooter) != null) {
            findViewById(R.id.btnBackFooter).setOnClickListener(v -> {
                // Standard back navigation (returns to OneYearPredictionActivity)
                finish();
            });
        }

        // Navigate to 5-Year Prognosis
        findViewById(R.id.btnViewFiveYear).setOnClickListener(v -> {
            Intent intent = new Intent(ThreeYearPredictionActivity.this, FiveYearPredictionActivity.class);
            startActivity(intent);
        });
    }
}
