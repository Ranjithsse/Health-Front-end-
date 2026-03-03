package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class RiskFactorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_factors);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnViewExplainability = findViewById(R.id.btnViewExplainability);

        btnBack.setOnClickListener(v -> finish());

        btnViewExplainability.setOnClickListener(v -> {
            startActivity(new Intent(RiskFactorsActivity.this, AiExplainabilityActivity.class));
        });
    }
}
