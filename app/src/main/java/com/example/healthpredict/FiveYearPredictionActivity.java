package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class FiveYearPredictionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_year_prediction);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnBackFooter = findViewById(R.id.btnBackFooter);
        MaterialButton btnViewRiskAnalysis = findViewById(R.id.btnViewRiskAnalysis);

        btnBack.setOnClickListener(v -> finish());
        btnBackFooter.setOnClickListener(v -> finish());

        btnViewRiskAnalysis.setOnClickListener(v -> {
            startActivity(new Intent(FiveYearPredictionActivity.this, RiskScoreActivity.class));
        });
    }
}
