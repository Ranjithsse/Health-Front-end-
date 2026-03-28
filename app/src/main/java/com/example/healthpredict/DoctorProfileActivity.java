package com.example.healthpredict;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.healthpredict.network.RetrofitClient;
import com.example.healthpredict.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Map;
import java.util.List;
import java.util.Locale;

public class DoctorProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_doctor_profile);

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

        setupStats();
        setupMenu();
        setupBottomNavigation();
        loadStatsFromServer();
    }

    private void loadStatsFromServer() {
        ApiService apiService = RetrofitClient.getApiService(this);
        apiService.getDashboardStats().enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Object> stats = response.body();
                    updateStatsUI(stats);
                    updateProfileDetails(stats);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Object>> call, @NonNull Throwable t) {
                // Fallback to local stats already handled in setupStats() and onResume()
            }
        });
    }

    private void updateStatsUI(Map<String, Object> stats) {
        if (stats == null) return;

        View statPatients = findViewById(R.id.statPatients);
        if (statPatients != null && stats.containsKey("total_patients")) {
            Object val = stats.get("total_patients");
            ((TextView) statPatients.findViewById(R.id.tvStatValue)).setText(String.valueOf(val));
        }

        View statCases = findViewById(R.id.statCases);
        if (statCases != null && stats.containsKey("monthly_cases")) {
            Object val = stats.get("monthly_cases");
            ((TextView) statCases.findViewById(R.id.tvStatValue)).setText(String.valueOf(val));
        }

        View statAccuracy = findViewById(R.id.statAccuracy);
        if (statAccuracy != null && stats.containsKey("accuracy")) {
            Object val = stats.get("accuracy");
            String acc = String.valueOf(val);
            if (!acc.contains("%")) acc += "%";
            ((TextView) statAccuracy.findViewById(R.id.tvStatValue)).setText(acc);
        }
    }

    private void updateProfileDetails(Map<String, Object> details) {
        TextView tvName = findViewById(R.id.tvProfileName);
        TextView tvDetail = findViewById(R.id.tvProfileDetail);

        if (tvName != null) {
            String name = (String) details.get("doctor_name");
            if (name != null && !name.isEmpty()) {
                tvName.setText(name);
            }
        }

        if (tvDetail != null) {
            String hospital = (String) details.get("hospital_name");
            String special = (String) details.get("specialization");

            StringBuilder sb = new StringBuilder();
            if (special != null && !special.isEmpty()) {
                sb.append(special);
            }
            if (hospital != null && !hospital.isEmpty()) {
                if (sb.length() > 0)
                    sb.append(" • ");
                sb.append(hospital);
            }
            tvDetail.setText(sb.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLocalStats();
    }

    private void refreshLocalStats() {
        SharedPreferences prefs = getSharedPreferences("HealthPredictPrefs", MODE_PRIVATE);
        String userEmail = prefs.getString("user_email", "anonymous");

        HistoryManager.getInstance().init(this);
        List<CaseData> history = HistoryManager.getInstance().getCaseHistory();

        // 1. Accuracy
        double totalAccuracy = 0;
        int accuracyCount = 0;
        if (history != null) {
            for (CaseData data : history) {
                if (data.accuracy != null && !data.accuracy.isEmpty()) {
                    try {
                        String cleanAcc = data.accuracy.replace("%", "").trim();
                        totalAccuracy += Double.parseDouble(cleanAcc);
                        accuracyCount++;
                    } catch (Exception ignored) {}
                }
            }
        }
        String displayAccuracy = (accuracyCount > 0) 
            ? String.format(Locale.getDefault(), "%.1f%%", totalAccuracy / accuracyCount) 
            : "94.2%";

        // 2. Patients
        int totalPatientsCount = history != null ? history.size() : 0;

        // 3. This Month
        int monthlyCasesCount = StatsManager.getInstance().getMonthlyCount(this, userEmail);

        // Update UI
        View statPatients = findViewById(R.id.statPatients);
        if (statPatients != null) {
            ((TextView) statPatients.findViewById(R.id.tvStatValue)).setText(String.valueOf(totalPatientsCount));
        }

        View statCases = findViewById(R.id.statCases);
        if (statCases != null) {
            ((TextView) statCases.findViewById(R.id.tvStatValue)).setText(String.valueOf(monthlyCasesCount));
        }

        View statAccuracy = findViewById(R.id.statAccuracy);
        if (statAccuracy != null) {
            ((TextView) statAccuracy.findViewById(R.id.tvStatValue)).setText(displayAccuracy);
        }
    }

    private void setupStats() {
        View statPatients = findViewById(R.id.statPatients);
        if (statPatients != null) {
            ((ImageView) statPatients.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_stat_patients_themed);
            ((TextView) statPatients.findViewById(R.id.tvStatLabel)).setText(R.string.total_patients);
        }

        View statCases = findViewById(R.id.statCases);
        if (statCases != null) {
            ((ImageView) statCases.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_stat_cases_themed);
            ((TextView) statCases.findViewById(R.id.tvStatLabel)).setText(R.string.this_month);
        }

        View statAccuracy = findViewById(R.id.statAccuracy);
        if (statAccuracy != null) {
            ((ImageView) statAccuracy.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_stat_accuracy_themed);
            ((TextView) statAccuracy.findViewById(R.id.tvStatLabel)).setText(R.string.avg_accuracy);
        }
        
        refreshLocalStats();
    }


    private void setupMenu() {
        View menuSettings = findViewById(R.id.menuSettings);
        if (menuSettings != null) {
            configureMenuItem(menuSettings, "Settings", R.drawable.ic_settings);
            menuSettings.setOnClickListener(v -> startActivity(new Intent(this, ProfileSettingsActivity.class)));
        }

        View menuNotifications = findViewById(R.id.menuNotifications);
        if (menuNotifications != null) {
            configureMenuItem(menuNotifications, "Notifications", R.drawable.ic_notification);
            menuNotifications
                    .setOnClickListener(v -> startActivity(new Intent(this, DoctorNotificationsActivity.class)));
        }

        View menuAchievements = findViewById(R.id.menuAchievements);
        if (menuAchievements != null) {
            configureMenuItem(menuAchievements, "Achievements", R.drawable.ic_achievements);
            menuAchievements.setOnClickListener(v -> startActivity(new Intent(this, MyAchievementsActivity.class)));
        }

        View menuLegal = findViewById(R.id.menuLegal);
        if (menuLegal != null) {
            configureMenuItem(menuLegal, "Legal & Privacy", R.drawable.ic_legal);
            menuLegal.setOnClickListener(v -> startActivity(new Intent(this, LegalPrivacyActivity.class)));
        }

        View menuHelp = findViewById(R.id.menuHelp);
        if (menuHelp != null) {
            configureMenuItem(menuHelp, "Help & Support", R.drawable.ic_help);
            menuHelp.setOnClickListener(v -> startActivity(new Intent(this, HelpSupportActivity.class)));
        }

        View btnLogout = findViewById(R.id.btnLogout);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                SharedPreferences prefs = getSharedPreferences("HealthPredictPrefs", MODE_PRIVATE);
                prefs.edit()
                    .putBoolean("is_logged_in", false)
                    .remove("access_token")
                    .remove("refresh_token")
                    .remove("last_activity")
                    .apply();

                Intent intent = new Intent(this, DoctorLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            });
        }
    }

    private void configureMenuItem(View view, String title, int iconRes) {
        TextView tvTitle = view.findViewById(R.id.tvMenuTitle);
        ImageView ivIcon = view.findViewById(R.id.ivMenuIcon);
        if (tvTitle != null)
            tvTitle.setText(title);
        if (ivIcon != null)
            ivIcon.setImageResource(iconRes);
    }

    private void setupBottomNavigation() {
        View navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(this, DoctorHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }

        View navCases = findViewById(R.id.navCases);
        if (navCases != null) {
            navCases.setOnClickListener(v -> startActivity(new Intent(this, DoctorCasesActivity.class)));
        }

        View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(v -> startActivity(new Intent(this, ReportsActivity.class)));
        }
    }
}
