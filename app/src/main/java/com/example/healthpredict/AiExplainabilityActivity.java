package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AiExplainabilityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_confidence_details);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnModelConfidence).setOnClickListener(v -> {
            Intent intent = new Intent(AiExplainabilityActivity.this, PredictionAccuracyActivity.class);
            startActivity(intent);
        });
    }
}
