package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class ClinicalRecommendationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinical_recommendations);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnAddNotes = findViewById(R.id.btnAddNotes);

        btnBack.setOnClickListener(v -> finish());

        btnAddNotes.setOnClickListener(v -> {
            startActivity(new Intent(ClinicalRecommendationsActivity.this, ProviderNotesActivity.class));
        });
    }
}
