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

        // Retrieve processed details from Intent
        final String patientName = getIntent().getStringExtra("PATIENT_NAME");
        final String patientId = getIntent().getStringExtra("PATIENT_ID");
        final String patientAge = getIntent().getStringExtra("PATIENT_AGE");
        final String patientGender = getIntent().getStringExtra("PATIENT_GENDER");
        final String predictionText = getIntent().getStringExtra("PREDICTION_TEXT");
        final String riskLevel = getIntent().getStringExtra("RISK_LEVEL");
        final String protocol = getIntent().getStringExtra("PROTOCOL");
        final String intervention = getIntent().getStringExtra("INTERVENTION");
        final String monitoring = getIntent().getStringExtra("MONITORING");
        final String providerNotes = getIntent().getStringExtra("PROVIDER_NOTES");

        // Bind data to UI
        TextView tvNameValue = findViewById(R.id.tvValueName);
        TextView tvIdHeader = findViewById(R.id.tvPatientIdHeader);
        TextView tvAgeValue = findViewById(R.id.tvValueAge);
        TextView tvGenderValue = findViewById(R.id.tvValueGender);
        TextView tvPrediction = findViewById(R.id.tvPredictionText);
        TextView tvRisk = findViewById(R.id.tvRiskLevel);
        TextView tvProtocolText = findViewById(R.id.tvProtocol);
        TextView tvInterventionText = findViewById(R.id.tvIntervention);
        TextView tvMonitoringText = findViewById(R.id.tvMonitoring);
        TextView tvNotesText = findViewById(R.id.tvProviderNotes);

        if (patientName != null && tvNameValue != null) tvNameValue.setText(patientName);
        if (patientId != null && tvIdHeader != null) tvIdHeader.setText("PATIENT #" + patientId);
        if (patientAge != null && tvAgeValue != null) tvAgeValue.setText(patientAge);
        if (patientGender != null && tvGenderValue != null) tvGenderValue.setText(patientGender);
        if (predictionText != null && tvPrediction != null) tvPrediction.setText(predictionText);
        if (riskLevel != null && tvRisk != null) tvRisk.setText("Risk Level: " + riskLevel);
        if (protocol != null && tvProtocolText != null) tvProtocolText.setText("Protocol: " + protocol);
        if (intervention != null && tvInterventionText != null) tvInterventionText.setText("Intervention: " + intervention);
        if (monitoring != null && tvMonitoringText != null) tvMonitoringText.setText("Monitoring: " + monitoring);
        if (providerNotes != null && tvNotesText != null) tvNotesText.setText(providerNotes);

        // Toolbar Back
        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }
        
        // Download logic
        ImageView ivDownload = findViewById(R.id.ivDownload);
        if (ivDownload != null) {
            ivDownload.setOnClickListener(v -> {
                Intent intent = new Intent(FinalReportActivity.this, DownloadReportActivity.class);
                intent.putExtra("PATIENT_NAME", patientName != null ? patientName : "Robert Wilson");
                intent.putExtra("PATIENT_ID", patientId != null ? patientId : "1024");
                startActivity(intent);
            });
        }

        findViewById(R.id.btnExportShare).setOnClickListener(v -> {
            Intent intent = new Intent(FinalReportActivity.this, ExportShareActivity.class);
            intent.putExtra("PATIENT_NAME", patientName != null ? patientName : "Robert Wilson");
            intent.putExtra("PATIENT_ID", patientId != null ? patientId : "1024");
            startActivity(intent);
        });
        
        findViewById(R.id.btnBackDashboard).setOnClickListener(v -> {
            Intent intent = new Intent(FinalReportActivity.this, DoctorHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}
