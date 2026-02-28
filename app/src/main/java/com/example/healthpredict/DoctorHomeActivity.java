package com.example.healthpredict;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.card.MaterialCardView;
import java.util.Calendar;
import java.util.List;

import com.example.healthpredict.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        
        setContentView(R.layout.activity_doctor_home);

        updateUI();

        View header = findViewById(R.id.header);
        View statusBarSpacer = findViewById(R.id.status_bar_spacer);
        if (header != null && statusBarSpacer != null) {
            ViewCompat.setOnApplyWindowInsetsListener(header, (v, insets) -> {
                int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top;
                statusBarSpacer.getLayoutParams().height = statusBarHeight;
                statusBarSpacer.requestLayout();
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

        setupNavigation();
        setupStats();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchCasesFromServer(); 
    }

    private void updateUI() {
        SharedPreferences prefs = getSharedPreferences("HealthPredictPrefs", MODE_PRIVATE);
        String doctorName = prefs.getString("user_name", "Dr. Alex Morgan");
        
        TextView tvGreeting = findViewById(R.id.tvGreeting);
        if (tvGreeting != null) {
            Calendar calendar = Calendar.getInstance();
            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
            String timeGreeting;

            if (hourOfDay >= 0 && hourOfDay < 12) {
                timeGreeting = "Good Morning";
            } else if (hourOfDay >= 12 && hourOfDay < 17) {
                timeGreeting = "Good Afternoon";
            } else {
                timeGreeting = "Good Evening";
            }

            tvGreeting.setText(timeGreeting + ", " + doctorName);
        }
    }

    private void setupNavigation() {
        // Notification
        ImageView ivNotification = findViewById(R.id.ivNotification);
        if (ivNotification != null) {
            ivNotification.setOnClickListener(v -> startActivity(new Intent(DoctorHomeActivity.this, DoctorNotificationsActivity.class)));
        }

        // SEARCH BAR - Making the entire box clickable
        View searchBar = findViewById(R.id.searchBar);
        if (searchBar != null) {
            searchBar.setOnClickListener(v -> {
                Intent intent = new Intent(DoctorHomeActivity.this, DoctorSearchActivity.class);
                startActivity(intent);
            });
        }

        // New Assessment
        View cardNewAssessment = findViewById(R.id.cardNewAssessment);
        if (cardNewAssessment != null) {
            cardNewAssessment.setOnClickListener(v -> startActivity(new Intent(DoctorHomeActivity.this, NewCaseOneActivity.class)));
        }

        // View All Patients
        TextView tvViewAllPatients = findViewById(R.id.tvViewAllPatients);
        if (tvViewAllPatients != null) {
            tvViewAllPatients.setOnClickListener(v -> startActivity(new Intent(DoctorHomeActivity.this, DoctorCasesActivity.class)));
        }

        // Bottom Nav - Cases
        View navCases = findViewById(R.id.navCases);
        if (navCases != null) {
            navCases.setOnClickListener(v -> startActivity(new Intent(DoctorHomeActivity.this, DoctorCasesActivity.class)));
        }

        // Bottom Nav - Reports
        View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(v -> startActivity(new Intent(DoctorHomeActivity.this, ReportsActivity.class)));
        }

        // Bottom Nav - Profile
        View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> startActivity(new Intent(DoctorHomeActivity.this, DoctorProfileActivity.class)));
        }
    }

    private void setupStats() {
        View stat1 = findViewById(R.id.stat1);
        if (stat1 != null) {
            ((ImageView) stat1.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_accuracy);
            ((TextView) stat1.findViewById(R.id.tvStatValue)).setText("94.2%");
            ((TextView) stat1.findViewById(R.id.tvStatLabel)).setText("Accuracy");
        }

        View stat2 = findViewById(R.id.stat2);
        if (stat2 != null) {
            ((ImageView) stat2.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_patients);
            ((TextView) stat2.findViewById(R.id.tvStatValue)).setText(String.valueOf(HistoryManager.getInstance().getCaseHistory().size() + 125));
            ((TextView) stat2.findViewById(R.id.tvStatLabel)).setText("Patients");
        }

        View stat3 = findViewById(R.id.stat3);
        if (stat3 != null) {
            ((ImageView) stat3.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_month);
            ((TextView) stat3.findViewById(R.id.tvStatValue)).setText("24");
            ((TextView) stat3.findViewById(R.id.tvStatLabel)).setText("This Month");
        }
    }

    private void fetchCasesFromServer() {
        RetrofitClient.getApiService().getCases().enqueue(new Callback<List<CaseData>>() {
            @Override
            public void onResponse(Call<List<CaseData>> call, Response<List<CaseData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CaseData> serverCases = response.body();
                    updateRecentPatientsUI(serverCases);
                } else {
                    // Fallback to local history if server fails
                    updateRecentPatientsUI(HistoryManager.getInstance().getCaseHistory());
                }
            }

            @Override
            public void onFailure(Call<List<CaseData>> call, Throwable t) {
                // Fallback to local history
                updateRecentPatientsUI(HistoryManager.getInstance().getCaseHistory());
            }
        });
    }

    private void updateRecentPatientsUI(List<CaseData> history) {
        
        int[] itemIds = {R.id.p1, R.id.p2, R.id.p3};
        
        for (int i = 0; i < itemIds.length; i++) {
            View patientView = findViewById(itemIds[i]);
            if (patientView == null) continue;

            if (i < history.size()) {
                CaseData data = history.get(i);
                patientView.setVisibility(View.VISIBLE);
                
                TextView tvInitial = patientView.findViewById(R.id.tvInitial);
                TextView tvName = patientView.findViewById(R.id.tvPatientName);
                TextView tvDetail = patientView.findViewById(R.id.tvPatientDetail);
                TextView tvStatus = patientView.findViewById(R.id.tvStatus);
                MaterialCardView cardStatus = patientView.findViewById(R.id.cardStatus);

                String name = data.patientName != null && !data.patientName.isEmpty() ? data.patientName : data.patientId;
                if (tvInitial != null) tvInitial.setText(name.substring(0, 1).toUpperCase());
                if (tvName != null) tvName.setText(name);
                if (tvDetail != null) tvDetail.setText(data.patientId + " • " + data.primarySystem);
                if (tvStatus != null) tvStatus.setText(data.riskLevel);
                
                if (cardStatus != null && tvStatus != null) {
                    updateStatusStyle(cardStatus, tvStatus, data.riskLevel);
                }

                patientView.setOnClickListener(v -> {
                    CaseData singleton = CaseData.getInstance();
                    singleton.reset();
                    copyToSingleton(data);
                    startActivity(new Intent(DoctorHomeActivity.this, FinalReportActivity.class));
                });
            } else {
                // If there's no real history but the view is visible (mock data), 
                // we should still allow clicking it to see activity_final_report.xml
                patientView.setOnClickListener(v -> {
                    startActivity(new Intent(DoctorHomeActivity.this, FinalReportActivity.class));
                });
            }
        }
    }

    private void copyToSingleton(CaseData data) {
        CaseData singleton = CaseData.getInstance();
        singleton.patientId = data.patientId;
        singleton.patientName = data.patientName;
        singleton.date = data.date;
        singleton.gender = data.gender;
        singleton.primarySystem = data.primarySystem;
        singleton.riskScore = data.riskScore;
        singleton.riskLevel = data.riskLevel;
        singleton.accuracy = data.accuracy;
        singleton.providerNotes = data.providerNotes;
        singleton.oneYearPrediction = data.oneYearPrediction;
        singleton.threeYearPrediction = data.threeYearPrediction;
        singleton.fiveYearPrediction = data.fiveYearPrediction;
    }

    private void updateStatusStyle(MaterialCardView card, TextView tv, String risk) {
        if (risk == null) return;
        switch (risk) {
            case "Low":
                card.setCardBackgroundColor(Color.parseColor("#DCFCE7")); // light green
                tv.setTextColor(Color.parseColor("#166534")); // dark green
                break;
            case "Moderate":
                card.setCardBackgroundColor(Color.parseColor("#FEF9C3")); // light yellow
                tv.setTextColor(Color.parseColor("#854D0E")); // dark yellow/brown
                break;
            case "High":
            case "Critical":
                card.setCardBackgroundColor(Color.parseColor("#FEE2E2")); // light red
                tv.setTextColor(Color.parseColor("#991B1B")); // dark red
                break;
            default:
                card.setCardBackgroundColor(Color.parseColor("#F1F5F9")); // light gray
                tv.setTextColor(Color.parseColor("#475569")); // dark gray
                break;
        }
    }
}
