package com.example.healthpredict;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.healthpredict.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorCasesActivity extends AppCompatActivity {

    private EditText etSearchInput;
    private TextView tvNoResults;
    private android.widget.LinearLayout caseListContainer;
    private java.util.List<CaseData> allCases = new java.util.ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_doctor_cases);

        etSearchInput = findViewById(R.id.etSearchInput);
        tvNoResults = findViewById(R.id.tvNoResults);
        caseListContainer = findViewById(R.id.caseListContainer);

        View toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            ViewCompat.setOnApplyWindowInsetsListener(toolbar, (v, insets) -> {
                int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top;
                v.setPadding(v.getPaddingLeft(), statusBarHeight, v.getPaddingRight(), v.getPaddingBottom());
                v.getLayoutParams().height = (int) (64 * getResources().getDisplayMetrics().density) + statusBarHeight;
                return insets;
            });
        }

        View bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) {
            ViewCompat.setOnApplyWindowInsetsListener(bottomNav, (v, insets) -> {
                int navBarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom;
                v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), navBarHeight);
                return insets;
            });
        }

        setupSearch();
        setupNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchCasesFromServer();
    }

    private void fetchCasesFromServer() {
        RetrofitClient.getApiService().getCases(null).enqueue(new Callback<List<CaseData>>() {
            @Override
            public void onResponse(Call<List<CaseData>> call, Response<List<CaseData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allCases = response.body();
                    updateCasesUI(allCases);
                }
            }

            @Override
            public void onFailure(Call<List<CaseData>> call, Throwable t) {
                // Fallback to local history
                allCases = HistoryManager.getInstance().getCaseHistory();
                updateCasesUI(allCases);
            }
        });
    }

    private void updateCasesUI(List<CaseData> cases) {
        if (caseListContainer == null)
            return;
        caseListContainer.removeAllViews();

        if (cases.isEmpty()) {
            tvNoResults.setVisibility(View.VISIBLE);
            return;
        }

        tvNoResults.setVisibility(View.GONE);
        for (CaseData data : cases) {
            addCaseItem(data);
        }
    }

    private void addCaseItem(CaseData data) {
        View caseView = getLayoutInflater().inflate(R.layout.item_case, caseListContainer, false);

        TextView tvName = caseView.findViewById(R.id.tvPatientName);
        TextView tvDetail = caseView.findViewById(R.id.tvPatientDetail);
        TextView tvStatus = caseView.findViewById(R.id.tvStatus);
        View cardStatus = caseView.findViewById(R.id.cardStatus);

        String name = data.patientName != null && !data.patientName.isEmpty() ? data.patientName : data.patientId;
        tvName.setText(name);
        tvDetail.setText(data.patientId + " • " + data.primarySystem);
        tvStatus.setText(data.status != null ? data.status : data.riskLevel);

        // Style status
        if (cardStatus instanceof com.google.android.material.card.MaterialCardView) {
            updateStatusStyle((com.google.android.material.card.MaterialCardView) cardStatus, tvStatus,
                    data.status != null ? data.status : data.riskLevel);
        }

        caseView.setOnClickListener(v -> {
            CaseData.getInstance().reset();
            CaseData.getInstance().copyFrom(data);
            startActivity(new Intent(DoctorCasesActivity.this, PatientOutcomeActivity.class));
        });

        caseListContainer.addView(caseView);
    }

    private void updateStatusStyle(com.google.android.material.card.MaterialCardView card, TextView tv, String status) {
        if (status == null)
            return;
        if (status.equalsIgnoreCase("Successful Recovery") || status.equalsIgnoreCase("Low")) {
            card.setCardBackgroundColor(Color.parseColor("#E1F9EB"));
            tv.setTextColor(Color.parseColor("#10B981"));
        } else if (status.equalsIgnoreCase("Condition Declined") || status.equalsIgnoreCase("High")) {
            card.setCardBackgroundColor(Color.parseColor("#FEF2F2"));
            tv.setTextColor(Color.parseColor("#EF4444"));
        } else {
            card.setCardBackgroundColor(Color.parseColor("#F1F5F9"));
            tv.setTextColor(Color.parseColor("#475569"));
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
                    filterCases(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    private void filterCases(String query) {
        String lowerQuery = query.toLowerCase().trim();
        java.util.List<CaseData> filtered = new java.util.ArrayList<>();

        for (CaseData data : allCases) {
            String name = (data.patientName != null ? data.patientName : "").toLowerCase();
            String id = (data.patientId != null ? data.patientId : "").toLowerCase();

            if (lowerQuery.isEmpty() || name.contains(lowerQuery) || id.contains(lowerQuery)) {
                filtered.add(data);
            }
        }

        updateCasesUI(filtered);
    }

    private void setupNavigation() {
        View navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(DoctorCasesActivity.this, DoctorHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }

        View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(
                    v -> startActivity(new Intent(DoctorCasesActivity.this, ReportsActivity.class)));
        }

        View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(
                    v -> startActivity(new Intent(DoctorCasesActivity.this, DoctorProfileActivity.class)));
        }
    }

}
