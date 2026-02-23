package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class NewCaseTwentyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_twenty);

        setupToolbar();
        setupButtons();
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBackHeader);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void setupButtons() {
        View btnRunPrediction = findViewById(R.id.btnRunPrediction);
        if (btnRunPrediction != null) {
            btnRunPrediction.setOnClickListener(v -> {
                // Navigate to AiPredictionActivity
                Intent intent = new Intent(NewCaseTwentyActivity.this, AiPredictionActivity.class);
                startActivity(intent);
            });
        }

        View btnBackEdit = findViewById(R.id.btnBackEdit);
        if (btnBackEdit != null) {
            btnBackEdit.setOnClickListener(v -> finish());
        }
    }
}
