package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DownloadReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_report);

        final String patientName = getIntent().getStringExtra("PATIENT_NAME");
        final String patientId = getIntent().getStringExtra("PATIENT_ID");

        TextView tvDetail = findViewById(R.id.tvPatientDetailHeader);
        if (tvDetail != null && patientName != null && patientId != null) {
            tvDetail.setText("Patient #" + patientId + " - " + patientName);
        }

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        findViewById(R.id.btnDownloadPdf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DownloadReportActivity.this, DownloadCompleteActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}