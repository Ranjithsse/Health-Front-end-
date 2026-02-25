package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OneYearPredictionActivity extends AppCompatActivity {

    private CaseData caseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_year_prediction);

        caseData = CaseData.getInstance();

        // Save 1-year data to CaseData
        caseData.oneYearPrediction = "98.2%";
        caseData.oneYearRisk = "Low";

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnViewThreeYear).setOnClickListener(v -> {
            Intent intent = new Intent(OneYearPredictionActivity.this, ThreeYearPredictionActivity.class);
            startActivity(intent);
        });
    }
}
