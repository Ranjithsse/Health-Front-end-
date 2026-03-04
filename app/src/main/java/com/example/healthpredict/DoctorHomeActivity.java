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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.example.healthpredict.network.ApiService;
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
            ivNotification.setOnClickListener(
                    v -> startActivity(new Intent(DoctorHomeActivity.this, DoctorNotificationsActivity.class)));
        }

        // SEARCH BAR
        View searchBar = findViewById(R.id.searchBar);
        if (searchBar != null) {
            searchBar.setOnClickListener(
                    v -> startActivity(new Intent(DoctorHomeActivity.this, DoctorSearchActivity.class)));
        }

        // New Assessment
        View cardNewAssessment = findViewById(R.id.cardNewAssessment);
        if (cardNewAssessment != null) {
            cardNewAssessment.setOnClickListener(v -> {
                CaseData.getInstance().reset();
                startActivity(new Intent(DoctorHomeActivity.this, NewCaseOneActivity.class));
            });
        }

        // View All Patients
        TextView tvViewAllPatients = findViewById(R.id.tvViewAllPatients);
        if (tvViewAllPatients != null) {
            tvViewAllPatients.setOnClickListener(
                    v -> startActivity(new Intent(DoctorHomeActivity.this, DoctorCasesActivity.class)));
        }

        // Bottom Nav - Cases
        View navCases = findViewById(R.id.navCases);
        if (navCases != null) {
            navCases.setOnClickListener(
                    v -> startActivity(new Intent(DoctorHomeActivity.this, DoctorCasesActivity.class)));
        }

        // Bottom Nav - Reports
        View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports
                    .setOnClickListener(v -> startActivity(new Intent(DoctorHomeActivity.this, ReportsActivity.class)));
        }

        // Bottom Nav - Profile
        View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(
                    v -> startActivity(new Intent(DoctorHomeActivity.this, DoctorProfileActivity.class)));
        }
    }

    private void setupStats() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getDashboardStats().enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Object> stats = response.body();
                    updateStatsDisplay(stats);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                // Fallback to static or local stats
            }
        });
    }

    private void updateStatsDisplay(Map<String, Object> stats) {
        View stat1 = findViewById(R.id.stat1);
        if (stat1 != null) {
            String accuracy = (String) stats.get("simulated_accuracy");
            ((TextView) stat1.findViewById(R.id.tvStatValue)).setText(accuracy);
        }

        View stat2 = findViewById(R.id.stat2);
        if (stat2 != null) {
            Object total = stats.get("total_patients");
            ((TextView) stat2.findViewById(R.id.tvStatValue)).setText(String.valueOf(total));
        }

        View stat3 = findViewById(R.id.stat3);
        if (stat3 != null) {
            Object monthly = stats.get("this_month_patients");
            ((TextView) stat3.findViewById(R.id.tvStatValue)).setText(String.valueOf(monthly));
        }
    }

    private void fetchCasesFromServer() {
        RetrofitClient.getApiService().getCases(null).enqueue(new Callback<List<CaseData>>() {
            @Override
            public void onResponse(Call<List<CaseData>> call, Response<List<CaseData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CaseData> serverCases = response.body();
                    updateRecentPatientsUI(serverCases);
                } else {
                    updateRecentPatientsUI(HistoryManager.getInstance().getCaseHistory());
                }
            }

            @Override
            public void onFailure(Call<List<CaseData>> call, Throwable t) {
                updateRecentPatientsUI(HistoryManager.getInstance().getCaseHistory());
            }
        });
    }

    private void updateRecentPatientsUI(List<CaseData> history) {
        RecyclerView rvRecentPatients = findViewById(R.id.rvRecentPatients);
        if (rvRecentPatients == null)
            return;

        // Limit to 3 recent patients for the home screen
        List<CaseData> recentList = history.size() > 3 ? history.subList(0, 3) : history;

        SearchAdapter adapter = new SearchAdapter(recentList, data -> {
            CaseData.getInstance().reset();
            CaseData.getInstance().copyFrom(data);
            startActivity(new Intent(DoctorHomeActivity.this, FinalReportActivity.class));
        });

        rvRecentPatients.setLayoutManager(new LinearLayoutManager(this));
        rvRecentPatients.setAdapter(adapter);
    }

}
