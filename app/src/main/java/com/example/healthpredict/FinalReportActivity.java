package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

import android.widget.Toast;
import com.example.healthpredict.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_report);
        setupBottomNavigation();

        setupToolbar();
        displayReportData();
        setupButtons();
    }

    private void setupToolbar() {
        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }

        ImageView ivDownload = findViewById(R.id.ivDownload);
        if (ivDownload != null) {
            ivDownload.setOnClickListener(v -> {
                CaseData data = CaseData.getInstance();
                Intent intent = new Intent(FinalReportActivity.this, DownloadReportActivity.class);
                intent.putExtra("PATIENT_NAME", data.patientName);
                intent.putExtra("PATIENT_ID", data.patientId);
                startActivity(intent);
            });
        }
    }

    private void displayReportData() {
        CaseData data = CaseData.getInstance();

        // Header
        TextView tvPatientIdHeader = findViewById(R.id.tvPatientIdHeader);
        TextView tvReportDate = findViewById(R.id.tvReportDate);
        if (tvPatientIdHeader != null)
            tvPatientIdHeader.setText("PATIENT #" + (data.patientId.isEmpty() ? "N/A" : data.patientId));
        if (tvReportDate != null)
            tvReportDate.setText(data.date.isEmpty() ? "N/A" : data.date);

        // Patient Info
        TextView tvValueName = findViewById(R.id.tvValueName);
        TextView tvValueAge = findViewById(R.id.tvValueAge);
        TextView tvValueGender = findViewById(R.id.tvValueGender);
        if (tvValueName != null)
            tvValueName.setText(data.patientName.isEmpty() ? "N/A" : data.patientName);
        if (tvValueAge != null)
            tvValueAge.setText(data.age.isEmpty() ? "N/A" : data.age);
        if (tvValueGender != null)
            tvValueGender.setText(data.gender.isEmpty() ? "N/A" : data.gender);

        // AI Prediction
        TextView tvPredictionText = findViewById(R.id.tvPredictionText);
        TextView tvRiskLevel = findViewById(R.id.tvRiskLevel);
        if (tvPredictionText != null)
            tvPredictionText.setText(data.oneYearPrediction + " Stability Probability\n(1-Year)");
        if (tvRiskLevel != null)
            tvRiskLevel.setText("Risk Level: " + (data.oneYearRisk.isEmpty() ? "Low" : data.oneYearRisk));

        // Clinical Plan
        TextView tvIntervention = findViewById(R.id.tvIntervention);
        TextView tvMonitoring = findViewById(R.id.tvMonitoring);
        if (tvIntervention != null)
            tvIntervention
                    .setText("Intervention: " + (data.interventionType.isEmpty() ? "N/A" : data.interventionType));
        if (tvMonitoring != null)
            tvMonitoring.setText("Monitoring: " + (data.monitoringLevel.isEmpty() ? "N/A" : data.monitoringLevel));

        // Provider Notes
        TextView tvProviderNotes = findViewById(R.id.tvProviderNotes);
        if (tvProviderNotes != null) {
            tvProviderNotes.setText(data.providerNotes.isEmpty() ? "No notes added." : data.providerNotes);
        }
    }

    private void setupButtons() {
        MaterialButton btnExportShare = findViewById(R.id.btnExportShare);
        MaterialButton btnBackDashboard = findViewById(R.id.btnBackDashboard);

        if (btnExportShare != null) {
            btnExportShare.setOnClickListener(v -> {
                startActivity(new Intent(FinalReportActivity.this, ExportShareActivity.class));
            });
        }

        if (btnBackDashboard != null) {
            btnBackDashboard.setOnClickListener(v -> {
                // Save to backend
                saveToBackend();

                Intent intent = new Intent(FinalReportActivity.this, DoctorHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            });
        }
    }

    private void saveToBackend() {
        CaseData caseData = CaseData.getInstance();
        caseData.status = "Completed"; // Ensure status is definitively marked completed
        
        Toast.makeText(this, "Saving to database...", Toast.LENGTH_SHORT).show();

        if (caseData.id > 0) {
            java.util.Map<String, Object> updates = new java.util.HashMap<>();
            try {
                // Easiest robust way to serialize the whole object to a Map so DRF receives all fields
                com.google.gson.Gson gson = new com.google.gson.Gson();
                String json = gson.toJson(caseData);
                java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<java.util.Map<String, Object>>(){}.getType();
                updates = gson.fromJson(json, type);
            } catch (Exception e) {
                // Fallback targeted keys if gson fails
                updates.put("status", "Completed");
                updates.put("intervention_type", caseData.interventionType);
                updates.put("monitoring_level", caseData.monitoringLevel);
            }

            RetrofitClient.getApiService(this).updateCase(caseData.id, updates).enqueue(new Callback<CaseData>() {
                @Override
                public void onResponse(Call<CaseData> call, Response<CaseData> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(FinalReportActivity.this, "Case saved successfully!", Toast.LENGTH_SHORT).show();
                        if (!caseData.patientName.isEmpty() && !caseData.primarySystem.isEmpty()) {
                            HistoryManager.getInstance().addCase(FinalReportActivity.this, caseData);
                        }
                    } else {
                        Toast.makeText(FinalReportActivity.this, "Failed to save case to server", Toast.LENGTH_SHORT)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<CaseData> call, Throwable t) {
                    Toast.makeText(FinalReportActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Local fallback test
            if (!caseData.patientName.isEmpty() && !caseData.primarySystem.isEmpty()) {
                HistoryManager.getInstance().addCase(FinalReportActivity.this, caseData);
            }
        }
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
