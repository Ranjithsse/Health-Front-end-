package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class RiskScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_score);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        
        findViewById(R.id.btnAnalyzeRiskFactors).setOnClickListener(v -> {
            Intent intent = new Intent(RiskScoreActivity.this, RiskFactorsActivity.class);
            startActivity(intent);
        });
    }
}
