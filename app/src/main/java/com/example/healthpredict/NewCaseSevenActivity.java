package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class NewCaseSevenActivity extends AppCompatActivity {

    private TextView tvSummaryName, tvSummaryDemographics, tvSummaryHistory, tvSummarySystem, tvSummaryVitals;
    private CaseData caseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_seven);

        caseData = CaseData.getInstance();

        tvSummaryName = findViewById(R.id.tvSummaryName);
        tvSummaryDemographics = findViewById(R.id.tvSummaryDemographics);
        tvSummaryHistory = findViewById(R.id.tvSummaryHistory);
        tvSummarySystem = findViewById(R.id.tvSummarySystem);
        tvSummaryVitals = findViewById(R.id.tvSummaryVitals);

        displaySummary();
        setupToolbar();

        View btnEdit = findViewById(R.id.btnEdit);
        if (btnEdit != null) {
            btnEdit.setOnClickListener(v -> finish());
        }

        MaterialButton btnSaveContinue = findViewById(R.id.btnSaveContinue);
        if (btnSaveContinue != null) {
            btnSaveContinue.setOnClickListener(v -> {
                Intent intent = new Intent(NewCaseSevenActivity.this, NewCaseEightActivity.class);
                startActivity(intent);
            });
        }
    }

    private void displaySummary() {
        if (tvSummaryName != null) tvSummaryName.setText(caseData.patientId);
        if (tvSummaryDemographics != null) {
            tvSummaryDemographics.setText(caseData.gender + ", " + caseData.bloodGroup + ", " + caseData.smokingStatus);
        }
        if (tvSummaryHistory != null) {
            tvSummaryHistory.setText(caseData.medicalConditions.toString());
        }
        if (tvSummarySystem != null) tvSummarySystem.setText(caseData.primarySystem);
        if (tvSummaryVitals != null) {
            tvSummaryVitals.setText("BP: " + caseData.bloodPressure + ", Glucose: " + caseData.glucoseLevel);
        }
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}
