package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FinalReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_report);

        final String patientName = getIntent().getStringExtra("PATIENT_NAME");
        final String patientId = getIntent().getStringExtra("PATIENT_ID");

        TextView tvNameValue = findViewById(R.id.tvValueName);
        TextView tvIdHeader = findViewById(R.id.tvPatientIdHeader);

        if (patientName != null && tvNameValue != null) {
            tvNameValue.setText(patientName);
        }
        
        if (patientId != null && tvIdHeader != null) {
            tvIdHeader.setText("PATIENT #" + patientId);
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
        
        ImageView ivDownload = findViewById(R.id.ivDownload);
        if (ivDownload != null) {
            ivDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FinalReportActivity.this, DownloadReportActivity.class);
                    intent.putExtra("PATIENT_NAME", patientName != null ? patientName : "Robert Wilson");
                    intent.putExtra("PATIENT_ID", patientId != null ? patientId : "1024");
                    startActivity(intent);
                }
            });
        }

        findViewById(R.id.btnExportShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalReportActivity.this, ExportShareActivity.class);
                intent.putExtra("PATIENT_NAME", patientName != null ? patientName : "Robert Wilson");
                intent.putExtra("PATIENT_ID", patientId != null ? patientId : "1024");
                startActivity(intent);
            }
        });
        
        findViewById(R.id.btnBackDashboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}