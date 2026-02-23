package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ClinicalRecommendationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinical_recommendations);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnAddNotes).setOnClickListener(v -> {
            // Navigate to Provider Notes screen
            Intent intent = new Intent(ClinicalRecommendationsActivity.this, ProviderNotesActivity.class);
            startActivity(intent);
        });
    }
}
