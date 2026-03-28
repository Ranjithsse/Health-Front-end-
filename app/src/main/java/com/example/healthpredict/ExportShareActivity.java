package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class ExportShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_share);
        setupBottomNavigation();

        ImageView ivBack = findViewById(R.id.ivBack);
        MaterialCardView cardDownloadPdf = findViewById(R.id.cardDownloadPdf);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cardDownloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExportShareActivity.this, DownloadReportActivity.class));
            }
        });
    }

    private void setupBottomNavigation() {
        android.view.View navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, DoctorHomeActivity.class);
                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP | android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }

        android.view.View navCases = findViewById(R.id.navCases);
        if (navCases != null) {
            navCases.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, DoctorCasesActivity.class);
                startActivity(intent);
                finish();
            });
        }

        android.view.View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, ReportsActivity.class);
                startActivity(intent);
                finish();
            });
        }

        android.view.View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, DoctorProfileActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }

}
