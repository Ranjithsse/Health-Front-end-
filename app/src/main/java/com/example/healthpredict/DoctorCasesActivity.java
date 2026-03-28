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
        HistoryManager.getInstance().init(this);
        allCases = HistoryManager.getInstance().getCaseHistory();
        updateCasesUI(allCases);
        fetchCasesFromServer();
    }

    private void fetchCasesFromServer() {
        RetrofitClient.getApiService(this).getCases(null).enqueue(new Callback<List<CaseData>>() {
            @Override
            public void onResponse(Call<List<CaseData>> call, Response<List<CaseData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allCases = deduplicateCases(response.body());
                    updateCasesUI(allCases);
                }
            }

            @Override
            public void onFailure(Call<List<CaseData>> call, Throwable t) {
                // Fallback to local history
                HistoryManager.getInstance().init(DoctorCasesActivity.this);
                allCases = HistoryManager.getInstance().getCaseHistory();
                updateCasesUI(allCases);
            }
        });
    }

    private List<CaseData> deduplicateCases(List<CaseData> inputList) {
        // For Cases list, show every unique assessment by its ID
        java.util.Map<Integer, CaseData> map = new java.util.LinkedHashMap<>();
        for (CaseData data : inputList) {
            map.put(data.id, data);
        }
        
        List<CaseData> result = new java.util.ArrayList<>(map.values());
        // Sort by ID descending (newest first)
        java.util.Collections.sort(result, (a, b) -> Integer.compare(b.id, a.id));
        return result;
    }

    private void updateCasesUI(List<CaseData> cases) {
        if (caseListContainer == null) return;
        
        caseListContainer.removeAllViews();
        android.view.LayoutInflater inflater = android.view.LayoutInflater.from(this);

        for (CaseData data : cases) {
            View itemView = inflater.inflate(R.layout.item_case, caseListContainer, false);
            
            TextView tvName = itemView.findViewById(R.id.tvPatientName);
            TextView tvDetail = itemView.findViewById(R.id.tvPatientDetail);
            TextView tvStatus = itemView.findViewById(R.id.tvStatus);
            View cardStatus = itemView.findViewById(R.id.cardStatus);

            String name = data.patientName != null && !data.patientName.isEmpty() ? data.patientName : data.patientId;
            if (tvName != null) tvName.setText(name);
            if (tvDetail != null) tvDetail.setText(data.patientId + " • " + data.primarySystem);
            
            // Recalculate risk level based on score for mobile consistency
            String scoreStr = data.riskScore != null ? data.riskScore.replace("%", "") : "0";
            int scoreValue = 0;
            try { scoreValue = Integer.parseInt(scoreStr); } catch (Exception ignored) {}
            
            String displayRisk;
            if (scoreValue > 0) {
                if (scoreValue <= 45) displayRisk = "Low";
                else if (scoreValue <= 60) displayRisk = "Moderate";
                else displayRisk = "High";
            } else {
                displayRisk = (data.riskLevel != null && !data.riskLevel.isEmpty()) ? data.riskLevel : "Low";
            }

            String statusText = data.status != null ? data.status : displayRisk;
            if (tvStatus != null) tvStatus.setText(statusText);

            if (cardStatus instanceof com.google.android.material.card.MaterialCardView) {
                updateStatusStyle((com.google.android.material.card.MaterialCardView) cardStatus, tvStatus, statusText);
            }

            itemView.setOnClickListener(v -> {
                try {
                    CaseData.getInstance().reset();
                    CaseData.getInstance().copyFrom(data);
                    Intent intent = new Intent(DoctorCasesActivity.this, FinalReportActivity.class);
                    intent.putExtra("PATIENT_NAME", name);
                    intent.putExtra("PATIENT_ID", String.valueOf(data.id));
                    startActivity(intent);
                } catch (Exception e) {
                    android.widget.Toast.makeText(DoctorCasesActivity.this, "Error: " + e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
                }
            });

            itemView.setOnLongClickListener(v -> {
                showDeleteConfirmationDialog(data);
                return true;
            });

            caseListContainer.addView(itemView);
        }
    }

    private void updateStatusStyle(com.google.android.material.card.MaterialCardView card, TextView tv, String status) {
        if (status == null)
            return;
        if (status.equalsIgnoreCase("Successful Recovery") || status.equalsIgnoreCase("Low") || status.equalsIgnoreCase("Completed")) {
            card.setCardBackgroundColor(Color.parseColor("#E1F9EB"));
            if (tv != null) tv.setTextColor(Color.parseColor("#10B981"));
        } else if (status.equalsIgnoreCase("Condition Declined") || status.equalsIgnoreCase("High")) {
            card.setCardBackgroundColor(Color.parseColor("#FEF2F2"));
            if (tv != null) tv.setTextColor(Color.parseColor("#EF4444"));
        } else {
            card.setCardBackgroundColor(Color.parseColor("#F1F5F9"));
            if (tv != null) tv.setTextColor(Color.parseColor("#475569"));
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
        List<CaseData> filteredList = new java.util.ArrayList<>();

        if (lowerQuery.isEmpty()) {
            filteredList.addAll(allCases);
        } else {
            for (CaseData caseData : allCases) {
                String name = caseData.patientName != null ? caseData.patientName.toLowerCase() : "";
                String id = caseData.patientId != null ? caseData.patientId.toLowerCase() : "";
                if (name.contains(lowerQuery) || id.contains(lowerQuery)) {
                    filteredList.add(caseData);
                }
            }
        }

        updateCasesUI(filteredList);

        if (tvNoResults != null) {
            tvNoResults.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
        }
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

    private void showDeleteConfirmationDialog(CaseData data) {
        new android.app.AlertDialog.Builder(this)
            .setTitle("Delete Case")
            .setMessage("Are you sure you want to delete the case for " + (data.patientName != null && !data.patientName.isEmpty() ? data.patientName : data.patientId) + "?")
            .setPositiveButton("Delete", (dialog, which) -> deleteCaseFromServer(data.id))
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void deleteCaseFromServer(int caseId) {
        android.app.ProgressDialog progressDialog = new android.app.ProgressDialog(this);
        progressDialog.setMessage("Deleting case...");
        progressDialog.show();

        com.example.healthpredict.network.RetrofitClient.getApiService(this).deleteCase(caseId).enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    android.widget.Toast.makeText(DoctorCasesActivity.this, "Case deleted successfully", android.widget.Toast.LENGTH_SHORT).show();
                    fetchCasesFromServer(); // Refresh list
                } else {
                    android.widget.Toast.makeText(DoctorCasesActivity.this, "Failed to delete case", android.widget.Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                progressDialog.dismiss();
                android.widget.Toast.makeText(DoctorCasesActivity.this, "Error: " + t.getMessage(), android.widget.Toast.LENGTH_LONG).show();
            }
        });
    }
}
