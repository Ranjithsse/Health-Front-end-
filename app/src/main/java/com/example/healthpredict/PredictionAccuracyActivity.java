package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class PredictionAccuracyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_accuracy);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnClinicalRecommendations).setOnClickListener(v -> {
            // Navigate to Clinical Recommendations screen
            Intent intent = new Intent(PredictionAccuracyActivity.this, ClinicalRecommendationsActivity.class);
            startActivity(intent);
        });
    }
}
