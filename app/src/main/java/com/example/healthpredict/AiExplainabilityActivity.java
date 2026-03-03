package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class AiExplainabilityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_confidence_details);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnModelConfidence = findViewById(R.id.btnModelConfidence);

        btnBack.setOnClickListener(v -> finish());

        btnModelConfidence.setOnClickListener(v -> {
            startActivity(new Intent(AiExplainabilityActivity.this, PredictionAccuracyActivity.class));
        });
    }
}
