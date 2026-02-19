package com.example.healthpredict;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class DoctorProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        // Bottom Navigation
        setupBottomNavigation();

        // Logout
        View btnLogout = findViewById(R.id.btnLogout);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DoctorProfileActivity.this, DoctorLoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }

        setupStats();
        setupMenu();
    }

    private void setupBottomNavigation() {
        View navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                startActivity(new Intent(DoctorProfileActivity.this, DoctorHomeActivity.class));
                finish();
            });
        }

        View navCases = findViewById(R.id.navCases);
        if (navCases != null) {
            navCases.setOnClickListener(v -> {
                startActivity(new Intent(DoctorProfileActivity.this, DoctorCasesActivity.class));
                finish();
            });
        }

        View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(v -> {
                startActivity(new Intent(DoctorProfileActivity.this, ReportsActivity.class));
                finish();
            });
        }
    }

    private void setupStats() {
        // Patients Stat - Blue
        View statPatients = findViewById(R.id.statPatients);
        if (statPatients != null) {
            ((ImageView) statPatients.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_person);
            ((ImageView) statPatients.findViewById(R.id.ivStatIcon)).setColorFilter(Color.parseColor("#4F46E5"));
            ((MaterialCardView) statPatients.findViewById(R.id.ivIconContainer)).setCardBackgroundColor(Color.parseColor("#EEF2FF"));
            ((TextView) statPatients.findViewById(R.id.tvStatValue)).setText("128");
            ((TextView) statPatients.findViewById(R.id.tvStatLabel)).setText("Total Patients");
        }

        // Cases Stat - Custom SVG
        View statCases = findViewById(R.id.statCases);
        if (statCases != null) {
            ((MaterialCardView) statCases.findViewById(R.id.ivIconContainer)).setCardBackgroundColor(Color.TRANSPARENT);
            ImageView ivIcon = statCases.findViewById(R.id.ivStatIcon);
            ivIcon.setImageResource(R.drawable.ic_stat_cases_themed);
            ivIcon.setColorFilter(null);
            ivIcon.getLayoutParams().width = (int) (36 * getResources().getDisplayMetrics().density);
            ivIcon.getLayoutParams().height = (int) (36 * getResources().getDisplayMetrics().density);
            
            ((TextView) statCases.findViewById(R.id.tvStatValue)).setText("24");
            ((TextView) statCases.findViewById(R.id.tvStatLabel)).setText("Cases This Month");
        }

        // Accuracy Stat - Green
        View statAccuracy = findViewById(R.id.statAccuracy);
        if (statAccuracy != null) {
            ((ImageView) statAccuracy.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_accuracy);
            ((ImageView) statAccuracy.findViewById(R.id.ivStatIcon)).setColorFilter(Color.parseColor("#16A34A"));
            ((MaterialCardView) statAccuracy.findViewById(R.id.ivIconContainer)).setCardBackgroundColor(Color.parseColor("#F0FDF4"));
            ((TextView) statAccuracy.findViewById(R.id.tvStatValue)).setText("94.2%");
            ((TextView) statAccuracy.findViewById(R.id.tvStatLabel)).setText("Accuracy");
        }

        // Alerts Stat - Custom SVG
        View statAlerts = findViewById(R.id.statAlerts);
        if (statAlerts != null) {
            ((MaterialCardView) statAlerts.findViewById(R.id.ivIconContainer)).setCardBackgroundColor(Color.TRANSPARENT);
            ImageView ivIcon = statAlerts.findViewById(R.id.ivStatIcon);
            ivIcon.setImageResource(R.drawable.ic_stat_alerts_themed);
            ivIcon.setColorFilter(null);
            ivIcon.getLayoutParams().width = (int) (36 * getResources().getDisplayMetrics().density);
            ivIcon.getLayoutParams().height = (int) (36 * getResources().getDisplayMetrics().density);

            ((TextView) statAlerts.findViewById(R.id.tvStatValue)).setText("3");
            ((TextView) statAlerts.findViewById(R.id.tvStatValue)).setTextColor(Color.parseColor("#EF4444"));
            TextView tvLabel = statAlerts.findViewById(R.id.tvStatLabel);
            tvLabel.setText("High Risk Alerts");
            tvLabel.setTextColor(Color.parseColor("#EF4444"));
        }
    }

    private void setupMenu() {
        // Settings
        View menuSettings = findViewById(R.id.menuSettings);
        setupMenuItem(menuSettings, R.drawable.ic_settings, "Settings", true);
        if (menuSettings != null) {
            menuSettings.setOnClickListener(v -> {
                startActivity(new Intent(DoctorProfileActivity.this, ProfileSettingsActivity.class));
            });
        }

        // Notifications
        View menuNotifications = findViewById(R.id.menuNotifications);
        setupMenuItem(menuNotifications, R.drawable.ic_notification, "Notifications", true);
        if (menuNotifications != null) {
            menuNotifications.setOnClickListener(v -> {
                startActivity(new Intent(DoctorProfileActivity.this, DoctorNotificationsActivity.class));
            });
        }
        
        // Achievements
        View menuAchievements = findViewById(R.id.menuAchievements);
        setupMenuItem(menuAchievements, R.drawable.ic_achievements_themed, "Achievements", false);
        if (menuAchievements != null) {
            menuAchievements.setOnClickListener(v -> {
                startActivity(new Intent(DoctorProfileActivity.this, MyAchievementsActivity.class));
            });
        }

        // Legal & Privacy
        View menuLegal = findViewById(R.id.menuLegal);
        setupMenuItem(menuLegal, R.drawable.ic_legal, "Legal & Privacy", true);
        if (menuLegal != null) {
            menuLegal.setOnClickListener(v -> {
                startActivity(new Intent(DoctorProfileActivity.this, LegalPrivacyActivity.class));
            });
        }

        // Help & Support
        View menuHelp = findViewById(R.id.menuHelp);
        setupMenuItem(menuHelp, R.drawable.ic_help, "Help & Support", true);
        if (menuHelp != null) {
            menuHelp.setOnClickListener(v -> {
                startActivity(new Intent(DoctorProfileActivity.this, HelpSupportActivity.class));
            });
        }
    }

    private void setupMenuItem(View view, int iconRes, String title, boolean applyTint) {
        if (view != null) {
            ImageView ivIcon = view.findViewById(R.id.ivMenuIcon);
            ivIcon.setImageResource(iconRes);
            if (applyTint) {
                ivIcon.setColorFilter(Color.parseColor("#64748B"));
            } else {
                ivIcon.setColorFilter(null);
            }
            ((TextView) view.findViewById(R.id.tvMenuTitle)).setText(title);
        }
    }
}
