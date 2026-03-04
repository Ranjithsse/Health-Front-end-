package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthpredict.network.RetrofitClient;
import com.example.healthpredict.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
import android.widget.Toast;
import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity {

    private EditText etSearchInput;
    private TextView tvNoResults;
    private final int[] reportItemIds = { R.id.report1, R.id.report2, R.id.report3, R.id.report4 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        etSearchInput = findViewById(R.id.etSearchInput);
        tvNoResults = findViewById(R.id.tvNoResults);

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }

        // "+ New" Button Navigation
        View btnNewReport = findViewById(R.id.btnNewReport);
        if (btnNewReport != null) {
            btnNewReport.setOnClickListener(v -> {
                Intent intent = new Intent(ReportsActivity.this, NewCaseOneActivity.class);
                startActivity(intent);
            });
        }

        // History Card Navigation
        View cardHistory = findViewById(R.id.cardHistory);
        if (cardHistory != null) {
            cardHistory.setOnClickListener(v -> {
                Intent intent = new Intent(ReportsActivity.this, DoctorCasesActivity.class);
                startActivity(intent);
            });
        }

        // Analytics Card Navigation
        View cardAnalytics = findViewById(R.id.cardAnalytics);
        if (cardAnalytics != null) {
            cardAnalytics.setOnClickListener(v -> {
                Intent intent = new Intent(ReportsActivity.this, StatisticsActivity.class);
                startActivity(intent);
            });
        }

        setupSearch();
        setupBottomNavigation();
        setupRecentReports();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecentReports(); // Refresh when returning
        if (etSearchInput != null) {
            filterReports(etSearchInput.getText().toString());
        }
    }

    private void setupSearch() {
        if (etSearchInput != null) {
            etSearchInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterReports(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    private void filterReports(String query) {
        boolean anyFound = false;
        String lowerQuery = query.toLowerCase().trim();

        for (int id : reportItemIds) {
            View itemView = findViewById(id);
            if (itemView != null) {
                TextView tvName = itemView.findViewById(R.id.tvPatientName);
                TextView tvDetail = itemView.findViewById(R.id.tvReportDetail);

                if (tvName != null && tvDetail != null) {
                    String name = tvName.getText().toString().toLowerCase();
                    String detail = tvDetail.getText().toString().toLowerCase();

                    if (lowerQuery.isEmpty() || name.contains(lowerQuery) || detail.contains(lowerQuery)) {
                        itemView.setVisibility(View.VISIBLE);
                        anyFound = true;
                    } else {
                        itemView.setVisibility(View.GONE);
                    }
                }
            }
        }

        if (tvNoResults != null) {
            tvNoResults.setVisibility(anyFound ? View.GONE : View.VISIBLE);
        }
    }

    private void setupRecentReports() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getCases("Completed").enqueue(new Callback<List<CaseData>>() {
            @Override
            public void onResponse(Call<List<CaseData>> call, Response<List<CaseData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateReportsUI(response.body());
                } else {
                    // Fallback to local history if server fails
                    List<CaseData> history = HistoryManager.getInstance().getCaseHistory();
                    updateReportsUI(history);
                }
            }

            @Override
            public void onFailure(Call<List<CaseData>> call, Throwable t) {
                // Fallback to local history
                List<CaseData> history = HistoryManager.getInstance().getCaseHistory();
                updateReportsUI(history);
            }
        });
    }

    private void setupBottomNavigation() {
        View navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(ReportsActivity.this, DoctorHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }

        View navCases = findViewById(R.id.navCases);
        if (navCases != null) {
            navCases.setOnClickListener(v -> {
                Intent intent = new Intent(ReportsActivity.this, DoctorCasesActivity.class);
                startActivity(intent);
                finish();
            });
        }

        View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                Intent intent = new Intent(ReportsActivity.this, DoctorProfileActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }

    private void updateReportsUI(List<CaseData> history) {
        for (int i = 0; i < reportItemIds.length; i++) {
            View itemView = findViewById(reportItemIds[i]);
            if (itemView == null)
                continue;

            if (i < history.size()) {
                CaseData data = history.get(i);
                itemView.setVisibility(View.VISIBLE);

                TextView tvName = itemView.findViewById(R.id.tvPatientName);
                TextView tvDetail = itemView.findViewById(R.id.tvReportDetail);

                String name = data.patientName != null && !data.patientName.isEmpty() ? data.patientName
                        : data.patientId;
                if (tvName != null)
                    tvName.setText(name);
                if (tvDetail != null)
                    tvDetail.setText(data.primarySystem + " • " + data.date);

                itemView.setOnClickListener(v -> {
                    // Set as current case and view report
                    CaseData singleton = CaseData.getInstance();
                    singleton.reset();
                    copyToSingleton(data, singleton);
                    startActivity(new Intent(ReportsActivity.this, FinalReportActivity.class));
                });
            } else {
                // If there's no real history but the view is visible (mock data),
                // we should still allow clicking it to see activity_final_report.xml
                itemView.setOnClickListener(v -> {
                    startActivity(new Intent(ReportsActivity.this, FinalReportActivity.class));
                });
            }
        }
    }

    private void copyToSingleton(CaseData source, CaseData target) {
        target.id = source.id;
        target.status = source.status;
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
