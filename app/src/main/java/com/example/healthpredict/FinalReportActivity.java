package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FinalReportActivity extends AppCompatActivity {

    private CaseData caseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_report);

        caseData = CaseData.getInstance();

        // Bind data to UI using CaseData singleton
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

        if (tvNameValue != null) tvNameValue.setText(caseData.patientName.isEmpty() ? caseData.patientId : caseData.patientName);
        if (tvIdHeader != null) tvIdHeader.setText("PATIENT #" + caseData.patientId);
        if (tvGenderValue != null) tvGenderValue.setText(caseData.gender);
        if (tvPrediction != null) tvPrediction.setText(caseData.riskScore + " Stability Probability");
        if (tvRisk != null) tvRisk.setText("Risk Level: " + caseData.riskLevel);
        if (tvProtocolText != null) tvProtocolText.setText("Accuracy: " + caseData.accuracy);
        if (tvInterventionText != null) tvInterventionText.setText("Intervention: " + caseData.interventionType);
        if (tvMonitoringText != null) tvMonitoringText.setText("Monitoring: " + caseData.monitoringLevel);
        if (tvNotesText != null) tvNotesText.setText(caseData.providerNotes);

        // Toolbar Back
        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }

        // Export & Share Navigation
        View btnExportShare = findViewById(R.id.btnExportShare);
        if (btnExportShare != null) {
            btnExportShare.setOnClickListener(v -> {
                Intent intent = new Intent(FinalReportActivity.this, ExportShareActivity.class);
                startActivity(intent);
            });
        }

        // Download Symbol Navigation
        View ivDownload = findViewById(R.id.ivDownload);
        if (ivDownload != null) {
            ivDownload.setOnClickListener(v -> {
                Intent intent = new Intent(FinalReportActivity.this, ExportShareActivity.class);
                startActivity(intent);
            });
        }
        
        findViewById(R.id.btnBackDashboard).setOnClickListener(v -> {
            // Save to recent cases simulation
            saveToHistory();
            
            Intent intent = new Intent(FinalReportActivity.this, DoctorHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void saveToHistory() {
        HistoryManager.getInstance().addCase(caseData);
    }
}
