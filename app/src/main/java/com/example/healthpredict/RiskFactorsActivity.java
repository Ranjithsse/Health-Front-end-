package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class RiskFactorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_factors);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnViewExplainability).setOnClickListener(v -> {
            Intent intent = new Intent(RiskFactorsActivity.this, AiExplainabilityActivity.class);
            startActivity(intent);
        });
    }
}
