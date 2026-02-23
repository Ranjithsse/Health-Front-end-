package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ModelConfidenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Use the new Prediction Accuracy layout
        setContentView(R.layout.activity_prediction_accuracy);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnClinicalRecommendations).setOnClickListener(v -> {
            // Navigate to Final Report or Recommendations
            Intent intent = new Intent(ModelConfidenceActivity.this, FinalReportActivity.class);
            startActivity(intent);
        });
    }
}
