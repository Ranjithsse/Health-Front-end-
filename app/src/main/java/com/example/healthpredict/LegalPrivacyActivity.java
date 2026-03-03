package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class LegalPrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_privacy);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnBackFooter = findViewById(R.id.btnBackFooter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnBackFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.navHome).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorHomeActivity.class));
            finish();
        });

        findViewById(R.id.navCases).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorCasesActivity.class));
            finish();
        });

        findViewById(R.id.navReports).setOnClickListener(v -> {
            startActivity(new Intent(this, ReportsActivity.class));
            finish();
        });

        findViewById(R.id.navProfile).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorProfileActivity.class));
            finish();
        });
    }
}
