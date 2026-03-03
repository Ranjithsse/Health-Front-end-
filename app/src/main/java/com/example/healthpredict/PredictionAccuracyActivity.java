package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class PredictionAccuracyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_accuracy);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnClinicalRecommendations = findViewById(R.id.btnClinicalRecommendations);

        btnBack.setOnClickListener(v -> finish());

        btnClinicalRecommendations.setOnClickListener(v -> {
            startActivity(new Intent(PredictionAccuracyActivity.this, ClinicalRecommendationsActivity.class));
        });
    }
}
