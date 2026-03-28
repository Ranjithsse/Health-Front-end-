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
        updateNotificationBadge();
    }

    private void updateNotificationBadge() {
        View notificationBadge = findViewById(R.id.notificationBadge);
        if (notificationBadge != null) {
            int unreadCount = LocalNotificationManager.getInstance(this).getUnreadCount();
            notificationBadge.setVisibility(unreadCount > 0 ? View.VISIBLE : View.GONE);
        }
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
        HistoryManager.getInstance().init(this);
        List<CaseData> history = HistoryManager.getInstance().getCaseHistory();

        int totalPatients = history.size();
        int monthlyPatients = 0;
        
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);

        for (CaseData data : history) {
            // Very simple date check assumption format "yyyy-MM-dd" or similar
            if (data.date != null && data.date.length() >= 7) {
                try {
                    String[] parts = data.date.split("-");
                    if (parts.length >= 2) {
                        int year = Integer.parseInt(parts[0]);
                        int month = Integer.parseInt(parts[1]) - 1; // 0-indexed
                        if (year == currentYear && month == currentMonth) {
                            monthlyPatients++;
                        }
                    }
                } catch (Exception e) { 
                    // Fallback loosely
                    monthlyPatients++;
                }
            } else {
                monthlyPatients++;
            }
        }

        updateStatsDisplay(totalPatients, monthlyPatients);
        
        // Also fetch from server to get accurate doctor name
        ApiService apiService = RetrofitClient.getApiService(this);
        apiService.getDashboardStats().enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Object> stats = response.body();
                    if (stats.containsKey("doctor_name")) {
                        String name = (String) stats.get("doctor_name");
                        TextView tvGreeting = findViewById(R.id.tvGreeting);
                        if (tvGreeting != null && name != null) {
                            Calendar calendar = Calendar.getInstance();
                            int hour = calendar.get(Calendar.HOUR_OF_DAY);
                            String timeGreeting = (hour < 12) ? "Good Morning"
                                    : (hour < 17) ? "Good Afternoon" : "Good Evening";
                            tvGreeting.setText(timeGreeting + ", " + name);
                            getSharedPreferences("HealthPredictPrefs", MODE_PRIVATE)
                                    .edit().putString("user_name", name).apply();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {}
        });
    }

    private void updateStatsDisplay(int totalPatients, int monthlyPatients) {
        View stat1 = findViewById(R.id.stat1);
        if (stat1 != null) {
            ((TextView) stat1.findViewById(R.id.tvStatValue)).setText("94.2%");
            ((TextView) stat1.findViewById(R.id.tvStatLabel)).setText("Accuracy");
            ((ImageView) stat1.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_accuracy);
        }

        View stat2 = findViewById(R.id.stat2);
        if (stat2 != null) {
            ((TextView) stat2.findViewById(R.id.tvStatValue)).setText(String.valueOf(totalPatients));
            ((TextView) stat2.findViewById(R.id.tvStatLabel)).setText("Patients");
            ((ImageView) stat2.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_patients);
            ((ImageView) stat2.findViewById(R.id.ivStatIcon)).setColorFilter(Color.parseColor("#6366F1"));
        }

        View stat3 = findViewById(R.id.stat3);
        if (stat3 != null) {
            ((TextView) stat3.findViewById(R.id.tvStatValue)).setText(String.valueOf(monthlyPatients));
            ((TextView) stat3.findViewById(R.id.tvStatLabel)).setText("This Month");
            ((ImageView) stat3.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_trend_up);
            ((ImageView) stat3.findViewById(R.id.ivStatIcon)).setColorFilter(Color.parseColor("#16A34A"));
        }
    }

    private void fetchCasesFromServer() {
        RetrofitClient.getApiService(this).getCases(null).enqueue(new Callback<List<CaseData>>() {
            @Override
            public void onResponse(Call<List<CaseData>> call, Response<List<CaseData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CaseData> serverCases = deduplicateCases(response.body());
                    updateRecentPatientsUI(serverCases);
                } else {
                    HistoryManager.getInstance().init(DoctorHomeActivity.this);
                    updateRecentPatientsUI(deduplicateCases(HistoryManager.getInstance().getCaseHistory()));
                }
            }

            @Override
            public void onFailure(Call<List<CaseData>> call, Throwable t) {
                HistoryManager.getInstance().init(DoctorHomeActivity.this);
                updateRecentPatientsUI(deduplicateCases(HistoryManager.getInstance().getCaseHistory()));
            }
        });
    }

    private List<CaseData> deduplicateCases(List<CaseData> inputList) {
        java.util.Map<String, CaseData> map = new java.util.LinkedHashMap<>();
        for (CaseData data : inputList) {
            String key = (data.patientId != null ? data.patientId : String.valueOf(data.id)) + "_" + data.date;
            if (map.containsKey(key)) {
                CaseData existing = map.get(key);
                if (!"Completed".equalsIgnoreCase(existing.status) && "Completed".equalsIgnoreCase(data.status)) {
                    map.put(key, data);
                } else if (!"Completed".equalsIgnoreCase(existing.status) && existing.id < data.id) {
                    map.put(key, data);
                }
            } else {
                map.put(key, data);
            }
        }
        
        List<CaseData> result = new java.util.ArrayList<>(map.values());
        java.util.Collections.reverse(result);
        return result;
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
