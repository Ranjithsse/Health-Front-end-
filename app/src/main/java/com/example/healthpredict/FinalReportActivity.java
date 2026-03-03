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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_report);

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
<<<<<<< HEAD
    }

    private void displayReportData() {
        CaseData data = CaseData.getInstance();

        // Header
        TextView tvPatientIdHeader = findViewById(R.id.tvPatientIdHeader);
        TextView tvReportDate = findViewById(R.id.tvReportDate);
        if (tvPatientIdHeader != null) tvPatientIdHeader.setText("PATIENT #" + (data.patientId.isEmpty() ? "N/A" : data.patientId));
        if (tvReportDate != null) tvReportDate.setText(data.date.isEmpty() ? "N/A" : data.date);

        // Patient Info
        TextView tvValueName = findViewById(R.id.tvValueName);
        TextView tvValueAge = findViewById(R.id.tvValueAge);
        TextView tvValueGender = findViewById(R.id.tvValueGender);
        if (tvValueName != null) tvValueName.setText(data.patientName.isEmpty() ? "N/A" : data.patientName);
        if (tvValueAge != null) tvValueAge.setText(data.age.isEmpty() ? "N/A" : data.age);
        if (tvValueGender != null) tvValueGender.setText(data.gender.isEmpty() ? "N/A" : data.gender);

        // AI Prediction
        TextView tvPredictionText = findViewById(R.id.tvPredictionText);
        TextView tvRiskLevel = findViewById(R.id.tvRiskLevel);
        if (tvPredictionText != null) tvPredictionText.setText(data.oneYearPrediction + " Stability Probability\n(1-Year)");
        if (tvRiskLevel != null) tvRiskLevel.setText("Risk Level: " + (data.oneYearRisk.isEmpty() ? "Low" : data.oneYearRisk));

        // Clinical Plan
        TextView tvIntervention = findViewById(R.id.tvIntervention);
        TextView tvMonitoring = findViewById(R.id.tvMonitoring);
        if (tvIntervention != null) tvIntervention.setText("Intervention: " + (data.interventionType.isEmpty() ? "N/A" : data.interventionType));
        if (tvMonitoring != null) tvMonitoring.setText("Monitoring: " + (data.monitoringLevel.isEmpty() ? "N/A" : data.monitoringLevel));

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
                Intent intent = new Intent(FinalReportActivity.this, DoctorHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            });
        }
=======
        
        findViewById(R.id.btnBackDashboard).setOnClickListener(v -> {
            // Save to backend
            saveToBackend();
            
            Intent intent = new Intent(FinalReportActivity.this, DoctorHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void saveToBackend() {
        // Show saving toast
        Toast.makeText(this, "Saving to database...", Toast.LENGTH_SHORT).show();

        RetrofitClient.getApiService().createCase(caseData).enqueue(new Callback<CaseData>() {
            @Override
            public void onResponse(Call<CaseData> call, Response<CaseData> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FinalReportActivity.this, "Case saved successfully!", Toast.LENGTH_SHORT).show();
                    // Also update local history for immediate UI feedback
                    HistoryManager.getInstance().addCase(caseData);
                } else {
                    Toast.makeText(FinalReportActivity.this, "Failed to save case to server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CaseData> call, Throwable t) {
                Toast.makeText(FinalReportActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
>>>>>>> a41db9c9b76a4cedc18eb27294c386544b564c4b
    }
}
