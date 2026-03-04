package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class PredictionAccuracyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_accuracy);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnClinicalRecommendations = findViewById(R.id.btnClinicalRecommendations);
        TextView tvAccuracyPercentage = findViewById(R.id.tvAccuracyPercentage);

        CaseData data = CaseData.getInstance();
        if (tvAccuracyPercentage != null && data.accuracy != null && !data.accuracy.isEmpty()) {
            tvAccuracyPercentage.setText(data.accuracy);
        }

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (btnClinicalRecommendations != null) {
            btnClinicalRecommendations.setOnClickListener(v -> {
                startActivity(new Intent(PredictionAccuracyActivity.this, ClinicalRecommendationsActivity.class));
            });
        }
    }
}
