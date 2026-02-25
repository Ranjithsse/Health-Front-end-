package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NewCaseTwentyActivity extends AppCompatActivity {

    private CaseData caseData;
    private TextView tvSummaryPatient;
    private TextView tvSummaryVitals;
    private TextView tvSummarySystem;
    private TextView tvSummaryFiles;
    private TextView tvSummaryPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_twenty);

        caseData = CaseData.getInstance();

        tvSummaryPatient = findViewById(R.id.tvSummaryPatient);
        tvSummaryVitals = findViewById(R.id.tvSummaryVitals);
        tvSummarySystem = findViewById(R.id.tvSummarySystem);
        tvSummaryFiles = findViewById(R.id.tvSummaryFiles);
        tvSummaryPlan = findViewById(R.id.tvSummaryPlan);

        displaySummary();
        setupToolbar();
        setupButtons();
    }

    private void displaySummary() {
        if (tvSummaryPatient != null) {
            String patientInfo = (caseData.patientName.isEmpty() ? "" : caseData.patientName + " ") +
                                (caseData.patientId.isEmpty() ? "Not Set" : "(" + caseData.patientId + ")");
            tvSummaryPatient.setText(patientInfo);
        }
        
        if (tvSummaryVitals != null) {
            StringBuilder vitals = new StringBuilder();
            if (!caseData.bloodPressure.isEmpty()) vitals.append(caseData.bloodPressure).append(", ");
            if (!caseData.gender.isEmpty()) vitals.append(caseData.gender).append(", ");
            if (!caseData.bloodGroup.isEmpty()) vitals.append(caseData.bloodGroup);
            
            String vitalsText = vitals.toString();
            if (vitalsText.endsWith(", ")) vitalsText = vitalsText.substring(0, vitalsText.length() - 2);
            tvSummaryVitals.setText(vitalsText.isEmpty() ? "Not recorded" : vitalsText);
        }

        if (tvSummarySystem != null) {
            tvSummarySystem.setText(caseData.primarySystem.isEmpty() ? "General" : caseData.primarySystem);
        }

        if (tvSummaryFiles != null) {
            tvSummaryFiles.setText(caseData.fileUri.isEmpty() ? "No files uploaded" : "File Attached");
        }

        if (tvSummaryPlan != null) {
            StringBuilder plan = new StringBuilder();
            plan.append(caseData.interventionType.isEmpty() ? "Standard" : caseData.interventionType);
            if (!caseData.monitoringLevel.isEmpty()) plan.append(", ").append(caseData.monitoringLevel);
            if (caseData.adjuvantTherapyRequired) plan.append(", Adjuvant Required");
            
            tvSummaryPlan.setText(plan.toString());
        }
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
                // Pass all collected data to AiPredictionActivity
                Intent intent = new Intent(NewCaseTwentyActivity.this, AiPredictionActivity.class);
                intent.putExtra("case_data", caseData);
                startActivity(intent);
            });
        }

        View btnBackEdit = findViewById(R.id.btnBackEdit);
        if (btnBackEdit != null) {
            btnBackEdit.setOnClickListener(v -> finish());
        }
    }
}
