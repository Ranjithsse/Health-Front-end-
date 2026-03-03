package com.example.healthpredict;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

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
    }

    private void setupStats() {
        View statPatients = findViewById(R.id.statPatients);
        if (statPatients != null) {
            ((ImageView) statPatients.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_stat_patients_themed);
            ((TextView) statPatients.findViewById(R.id.tvStatValue)).setText("128");
            ((TextView) statPatients.findViewById(R.id.tvStatLabel)).setText("Patients");
        }

        View statCases = findViewById(R.id.statCases);
        if (statCases != null) {
            ((ImageView) statCases.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_stat_cases_themed);
            ((TextView) statCases.findViewById(R.id.tvStatValue)).setText("42");
            ((TextView) statCases.findViewById(R.id.tvStatLabel)).setText("Active Cases");
        }

        View statAccuracy = findViewById(R.id.statAccuracy);
        if (statAccuracy != null) {
            ((ImageView) statAccuracy.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_stat_accuracy_themed);
            ((TextView) statAccuracy.findViewById(R.id.tvStatValue)).setText("94.2%");
            ((TextView) statAccuracy.findViewById(R.id.tvStatLabel)).setText("Avg. Prediction Accuracy");
        }
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
            menuNotifications.setOnClickListener(v -> startActivity(new Intent(this, DoctorNotificationsActivity.class)));
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
                Intent intent = new Intent(this, DoctorLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            });
        }
    }

    private void configureMenuItem(View view, String title, int iconRes) {
        TextView tvTitle = view.findViewById(R.id.tvMenuTitle);
        ImageView ivIcon = view.findViewById(R.id.ivMenuIcon);
        if (tvTitle != null) tvTitle.setText(title);
        if (ivIcon != null) ivIcon.setImageResource(iconRes);
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
