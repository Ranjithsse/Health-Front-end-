package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DoctorProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        setupStats();
        setupMenu();
        setupNavigation();
    }

    private void setupStats() {
        // Patient Stat
        View statPatients = findViewById(R.id.statPatients);
        ((TextView) statPatients.findViewById(R.id.tvStatValue)).setText("1,248");
        ((TextView) statPatients.findViewById(R.id.tvStatLabel)).setText("Total Patients");
        ((ImageView) statPatients.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_person);

        // Cases Stat
        View statCases = findViewById(R.id.statCases);
        ((TextView) statCases.findViewById(R.id.tvStatValue)).setText("856");
        ((TextView) statCases.findViewById(R.id.tvStatLabel)).setText("Active Cases");
        ((ImageView) statCases.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_cases);

        // Accuracy Stat
        View statAccuracy = findViewById(R.id.statAccuracy);
        ((TextView) statAccuracy.findViewById(R.id.tvStatValue)).setText("94.2%");
        ((TextView) statAccuracy.findViewById(R.id.tvStatLabel)).setText("Avg. Accuracy");
        ((ImageView) statAccuracy.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_accuracy);
    }

    private void setupMenu() {
        setupMenuItem(findViewById(R.id.menuSettings), R.drawable.ic_settings, "Account Settings", v -> startActivity(new Intent(this, ProfileSettingsActivity.class)));
        setupMenuItem(findViewById(R.id.menuNotifications), R.drawable.ic_notification, "Notifications", v -> startActivity(new Intent(this, DoctorNotificationsActivity.class)));
        setupMenuItem(findViewById(R.id.menuAchievements), R.drawable.ic_achievements, "My Achievements", v -> startActivity(new Intent(this, MyAchievementsActivity.class)));
        setupMenuItem(findViewById(R.id.menuLegal), R.drawable.ic_legal, "Legal & Privacy", v -> startActivity(new Intent(this, LegalPrivacyActivity.class)));
        setupMenuItem(findViewById(R.id.menuHelp), R.drawable.ic_help, "Help & Support", v -> startActivity(new Intent(this, HelpSupportActivity.class)));
        
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            Intent intent = new Intent(this, DoctorLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void setupMenuItem(View view, int iconRes, String title, View.OnClickListener listener) {
        if (view == null) return;
        ((ImageView) view.findViewById(R.id.ivMenuIcon)).setImageResource(iconRes);
        ((TextView) view.findViewById(R.id.tvMenuTitle)).setText(title);
        view.setOnClickListener(listener);
    }

    private void setupNavigation() {
        findViewById(R.id.navHome).setOnClickListener(v -> {
            Intent intent = new Intent(this, DoctorHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
        findViewById(R.id.navCases).setOnClickListener(v -> startActivity(new Intent(this, DoctorCasesActivity.class)));
        findViewById(R.id.navReports).setOnClickListener(v -> startActivity(new Intent(this, ReportsActivity.class)));
    }
}
