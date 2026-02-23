package com.example.healthpredict;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.card.MaterialCardView;

public class DoctorHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 1. Fully cover the screen (immersive mode)
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        
        setContentView(R.layout.activity_doctor_home);

        // 2. Adjust Header Padding dynamically for status bar/notch
        View header = findViewById(R.id.header);
        View statusBarSpacer = findViewById(R.id.status_bar_spacer);
        if (header != null && statusBarSpacer != null) {
            ViewCompat.setOnApplyWindowInsetsListener(header, (v, insets) -> {
                int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top;
                // Set the spacer height to exactly match the system's status bar/notch height
                statusBarSpacer.getLayoutParams().height = statusBarHeight;
                statusBarSpacer.requestLayout();
                return insets;
            });
        }

        // 3. Adjust Bottom Nav for gesture bar
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
        setupRecentPatients();
    }

    private void setupNavigation() {
        // Notification
        ImageView ivNotification = findViewById(R.id.ivNotification);
        if (ivNotification != null) {
            ivNotification.setOnClickListener(v -> startActivity(new Intent(DoctorHomeActivity.this, DoctorNotificationsActivity.class)));
        }

        // Search
        View searchBar = findViewById(R.id.searchBar);
        if (searchBar != null) {
            searchBar.setOnClickListener(v -> startActivity(new Intent(DoctorHomeActivity.this, DoctorSearchActivity.class)));
        }

        // High Risk Alerts
        View cardHighRiskAlerts = findViewById(R.id.cardHighRiskAlerts);
        if (cardHighRiskAlerts != null) {
            cardHighRiskAlerts.setOnClickListener(v -> startActivity(new Intent(DoctorHomeActivity.this, HighRiskAlertsActivity.class)));
        }

        // New Assessment
        View cardNewAssessment = findViewById(R.id.cardNewAssessment);
        if (cardNewAssessment != null) {
            cardNewAssessment.setOnClickListener(v -> startActivity(new Intent(DoctorHomeActivity.this, NewCaseOneActivity.class)));
        }

        // View All (Recent Patients)
        TextView tvViewAllPatients = findViewById(R.id.tvViewAllPatients);
        if (tvViewAllPatients != null) {
            tvViewAllPatients.setOnClickListener(v -> startActivity(new Intent(DoctorHomeActivity.this, DoctorCasesActivity.class)));
        }

        // Cases
        View navCases = findViewById(R.id.navCases);
        if (navCases != null) {
            navCases.setOnClickListener(v -> startActivity(new Intent(DoctorHomeActivity.this, DoctorCasesActivity.class)));
        }

        // Reports
        View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(v -> startActivity(new Intent(DoctorHomeActivity.this, ReportsActivity.class)));
        }

        // Profile
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
            ((TextView) stat2.findViewById(R.id.tvStatValue)).setText("128");
            ((TextView) stat2.findViewById(R.id.tvStatLabel)).setText("Patients");
        }

        View stat3 = findViewById(R.id.stat3);
        if (stat3 != null) {
            ((ImageView) stat3.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_month);
            ((TextView) stat3.findViewById(R.id.tvStatValue)).setText("24");
            ((TextView) stat3.findViewById(R.id.tvStatLabel)).setText("This Month");
        }
    }

    private void setupRecentPatients() {
        View patient1 = findViewById(R.id.p1);
        if (patient1 != null) {
            ((TextView) patient1.findViewById(R.id.tvInitial)).setText("R");
            ((TextView) patient1.findViewById(R.id.tvPatientName)).setText("Robert Wilson");
            ((TextView) patient1.findViewById(R.id.tvPatientDetail)).setText("P-1024 • Cardiac Risk");
            ((TextView) patient1.findViewById(R.id.tvStatus)).setText("Low Risk");
            ((MaterialCardView) patient1.findViewById(R.id.cardStatus)).setCardBackgroundColor(getResources().getColor(R.color.low_risk_bg));
            ((TextView) patient1.findViewById(R.id.tvStatus)).setTextColor(getResources().getColor(R.color.low_risk_text));
            patient1.setOnClickListener(v -> {
                Intent intent = new Intent(DoctorHomeActivity.this, FinalReportActivity.class);
                intent.putExtra("PATIENT_NAME", "Robert Wilson");
                startActivity(intent);
            });
        }

        View patient2 = findViewById(R.id.p2);
        if (patient2 != null) {
            ((TextView) patient2.findViewById(R.id.tvInitial)).setText("S");
            ((TextView) patient2.findViewById(R.id.tvPatientName)).setText("Sarah Johnson");
            ((TextView) patient2.findViewById(R.id.tvPatientDetail)).setText("P-1023 • Pending Labs");
            ((TextView) patient2.findViewById(R.id.tvStatus)).setText("Pending");
            ((MaterialCardView) patient2.findViewById(R.id.cardStatus)).setCardBackgroundColor(getResources().getColor(R.color.pending_bg));
            ((TextView) patient2.findViewById(R.id.tvStatus)).setTextColor(getResources().getColor(R.color.pending_text));
            patient2.setOnClickListener(v -> {
                Intent intent = new Intent(DoctorHomeActivity.this, FinalReportActivity.class);
                intent.putExtra("PATIENT_NAME", "Sarah Johnson");
                startActivity(intent);
            });
        }

        View patient3 = findViewById(R.id.p3);
        if (patient3 != null) {
            ((TextView) patient3.findViewById(R.id.tvInitial)).setText("M");
            ((TextView) patient3.findViewById(R.id.tvPatientName)).setText("Michael Brown");
            ((TextView) patient3.findViewById(R.id.tvPatientDetail)).setText("P-1022 • Diabetes Screen");
            ((TextView) patient3.findViewById(R.id.tvStatus)).setText("Moderate");
            ((MaterialCardView) patient3.findViewById(R.id.cardStatus)).setCardBackgroundColor(getResources().getColor(R.color.moderate_bg));
            ((TextView) patient3.findViewById(R.id.tvStatus)).setTextColor(getResources().getColor(R.color.moderate_text));
            patient3.setOnClickListener(v -> {
                Intent intent = new Intent(DoctorHomeActivity.this, FinalReportActivity.class);
                intent.putExtra("PATIENT_NAME", "Michael Brown");
                startActivity(intent);
            });
        }
    }
}
