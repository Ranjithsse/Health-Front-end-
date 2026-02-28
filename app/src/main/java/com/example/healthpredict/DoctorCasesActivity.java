package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

import com.example.healthpredict.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorCasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_cases);

        View navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(DoctorCasesActivity.this, DoctorHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }

        View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(v -> {
                Intent intent = new Intent(DoctorCasesActivity.this, ReportsActivity.class);
                startActivity(intent);
            });
        }

        View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                Intent intent = new Intent(DoctorCasesActivity.this, DoctorProfileActivity.class);
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchCasesFromServer();
    }

    private void fetchCasesFromServer() {
        RetrofitClient.getApiService().getCases().enqueue(new Callback<List<CaseData>>() {
            @Override
            public void onResponse(Call<List<CaseData>> call, Response<List<CaseData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateCasesUI(response.body());
                } else {
                    updateCasesUI(HistoryManager.getInstance().getCaseHistory());
                }
            }

            @Override
            public void onFailure(Call<List<CaseData>> call, Throwable t) {
                updateCasesUI(HistoryManager.getInstance().getCaseHistory());
            }
        });
    }

    private void updateCasesUI(List<CaseData> history) {
        int[] itemIds = {R.id.case1, R.id.case2, R.id.case3, R.id.case4, R.id.case5};

        for (int i = 0; i < itemIds.length; i++) {
            View itemView = findViewById(itemIds[i]);
            if (itemView == null) continue;

            if (i < history.size()) {
                CaseData data = history.get(i);
                itemView.setVisibility(View.VISIBLE);
                
                String name = data.patientName != null && !data.patientName.isEmpty() ? data.patientName : data.patientId;
                setupCaseItem(itemView, name, data.patientId, data.date, data.riskLevel, data);
            } else {
                // If there's no real history but the view is visible (mock data),
                // we should still allow clicking it to see activity_final_report.xml
                itemView.setOnClickListener(v -> {
                    startActivity(new Intent(DoctorCasesActivity.this, FinalReportActivity.class));
                });
            }
        }
    }

    private void setupCaseItem(View itemView, final String name, final String id, String date, String riskLevel, CaseData data) {
        ((TextView) itemView.findViewById(R.id.tvPatientName)).setText(name);
        ((TextView) itemView.findViewById(R.id.tvPatientDetail)).setText(id + " • " + date);
        ((TextView) itemView.findViewById(R.id.tvStatus)).setText(riskLevel);
        
        MaterialCardView cardStatus = itemView.findViewById(R.id.cardStatus);
        TextView tvStatus = itemView.findViewById(R.id.tvStatus);

        if (cardStatus != null && tvStatus != null) {
            updateStatusStyle(cardStatus, tvStatus, riskLevel);
        }

        itemView.setOnClickListener(v -> {
            // View this specific case in the final report
            CaseData singleton = CaseData.getInstance();
            singleton.reset();
            copyToSingleton(data, singleton);
            Intent intent = new Intent(DoctorCasesActivity.this, FinalReportActivity.class);
            startActivity(intent);
        });
    }

    private void updateStatusStyle(MaterialCardView card, TextView tv, String risk) {
        if (risk == null) return;
        switch (risk) {
            case "Low":
                card.setCardBackgroundColor(getResources().getColor(R.color.low_risk_bg));
                tv.setTextColor(getResources().getColor(R.color.low_risk_text));
                break;
            case "Moderate":
                card.setCardBackgroundColor(getResources().getColor(R.color.moderate_bg));
                tv.setTextColor(getResources().getColor(R.color.moderate_text));
                break;
            case "High":
            case "Critical":
                card.setCardBackgroundColor(getResources().getColor(R.color.high_risk_bg));
                tv.setTextColor(getResources().getColor(R.color.high_risk_text));
                break;
            default:
                card.setCardBackgroundColor(getResources().getColor(R.color.pending_bg));
                tv.setTextColor(getResources().getColor(R.color.pending_text));
                break;
        }
    }

    private void copyToSingleton(CaseData source, CaseData target) {
        target.patientId = source.patientId;
        target.patientName = source.patientName;
        target.date = source.date;
        target.gender = source.gender;
        target.primarySystem = source.primarySystem;
        target.riskScore = source.riskScore;
        target.riskLevel = source.riskLevel;
        target.accuracy = source.accuracy;
        target.providerNotes = source.providerNotes;
        target.interventionType = source.interventionType;
        target.monitoringLevel = source.monitoringLevel;
    }
}
