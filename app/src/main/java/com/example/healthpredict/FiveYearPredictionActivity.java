package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class FiveYearPredictionActivity extends AppCompatActivity {

    private CaseData caseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_year_prediction);

        caseData = CaseData.getInstance();

        // Save 5-year data to CaseData
        caseData.fiveYearPrediction = "72.1%";
        caseData.fiveYearRisk = "Moderate";

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.btnBackFooter).setOnClickListener(v -> finish());

        findViewById(R.id.btnViewRiskAnalysis).setOnClickListener(v -> {
            Intent intent = new Intent(FiveYearPredictionActivity.this, RiskScoreActivity.class);
            startActivity(intent);
        });
    }
}
