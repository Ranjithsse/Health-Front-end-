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

public class DoctorCasesActivity extends AppCompatActivity {

    private EditText etSearchInput;
    private TextView tvNoResults;
    private final int[] caseIds = {R.id.case1, R.id.case2, R.id.case3, R.id.case4, R.id.case5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        
        setContentView(R.layout.activity_doctor_cases);

        etSearchInput = findViewById(R.id.etSearchInput);
        tvNoResults = findViewById(R.id.tvNoResults);

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
        setupCaseList(); // Refresh list to show updated outcomes
        if (etSearchInput != null) {
            filterCases(etSearchInput.getText().toString());
        }
    }

    private void setupSearch() {
        if (etSearchInput != null) {
            etSearchInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    filterCases(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    private void filterCases(String query) {
        boolean anyFound = false;
        String lowerQuery = query.toLowerCase().trim();

        for (int id : caseIds) {
            View caseView = findViewById(id);
            if (caseView != null) {
                TextView tvName = caseView.findViewById(R.id.tvPatientName);
                TextView tvDetail = caseView.findViewById(R.id.tvPatientDetail);
                
                if (tvName != null && tvDetail != null) {
                    String name = tvName.getText().toString().toLowerCase();
                    String detail = tvDetail.getText().toString().toLowerCase();
                    
                    if (lowerQuery.isEmpty() || name.contains(lowerQuery) || detail.contains(lowerQuery)) {
                        caseView.setVisibility(View.VISIBLE);
                        anyFound = true;
                    } else {
                        caseView.setVisibility(View.GONE);
                    }
                }
            }
        }

        if (tvNoResults != null) {
            tvNoResults.setVisibility(anyFound ? View.GONE : View.VISIBLE);
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
            navReports.setOnClickListener(v -> startActivity(new Intent(DoctorCasesActivity.this, ReportsActivity.class)));
        }

        View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> startActivity(new Intent(DoctorCasesActivity.this, DoctorProfileActivity.class)));
        }
    }

    private void setupCaseList() {
        SharedPreferences prefs = getSharedPreferences("PatientOutcomes", MODE_PRIVATE);
        
        for (int id : caseIds) {
            View caseView = findViewById(id);
            if (caseView != null) {
                TextView tvName = caseView.findViewById(R.id.tvPatientName);
                TextView tvDetail = caseView.findViewById(R.id.tvPatientDetail);
                TextView tvStatus = caseView.findViewById(R.id.tvStatus);
                View cardStatus = caseView.findViewById(R.id.cardStatus);

                if (tvName != null && tvDetail != null) {
                    String name = tvName.getText().toString();
                    String detail = tvDetail.getText().toString();
                    String patientId = detail.contains(" • ") ? detail.split(" • ")[0] : detail;

                    // Check for saved outcome
                    String savedStatus = prefs.getString(patientId, null);
                    if (savedStatus != null && tvStatus != null) {
                        tvStatus.setText(savedStatus);
                        if (savedStatus.equalsIgnoreCase("Successful Recovery")) {
                            if (cardStatus instanceof com.google.android.material.card.MaterialCardView) {
                                ((com.google.android.material.card.MaterialCardView) cardStatus).setCardBackgroundColor(Color.parseColor("#E1F9EB"));
                            }
                            tvStatus.setTextColor(Color.parseColor("#10B981"));
                        } else if (savedStatus.equalsIgnoreCase("Condition Declined") || savedStatus.equalsIgnoreCase("Complications Arising")) {
                            if (cardStatus instanceof com.google.android.material.card.MaterialCardView) {
                                ((com.google.android.material.card.MaterialCardView) cardStatus).setCardBackgroundColor(Color.parseColor("#FEF2F2"));
                            }
                            tvStatus.setTextColor(Color.parseColor("#EF4444"));
                        }
                    }

                    caseView.setOnClickListener(v -> {
                        Intent intent = new Intent(DoctorCasesActivity.this, PatientOutcomeActivity.class);
                        intent.putExtra("PATIENT_NAME", name);
                        intent.putExtra("PATIENT_ID", patientId);
                        startActivity(intent);
                    });
                }
            }
        }
    }
}
