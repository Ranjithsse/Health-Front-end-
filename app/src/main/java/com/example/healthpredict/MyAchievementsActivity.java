package com.example.healthpredict;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class MyAchievementsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_achievements);

        setupToolbar();
        setupBadges();
        setupBottomNavigation();
        
        View btnBackToProfile = findViewById(R.id.btnBackToProfile);
        if (btnBackToProfile != null) {
            btnBackToProfile.setOnClickListener(v -> finish());
        }
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void setupBadges() {
        // Badge 1: First Analysis (Gold)
        setupBadge(findViewById(R.id.badgeFirstAnalysis), 
                R.drawable.ic_achievements, "#CA8A04", "#FEF9C3",
                "First Analysis", "Completed your first patient risk assessment", "JAN 10, 2025");

        // Badge 2: High Accuracy (Red)
        setupBadge(findViewById(R.id.badgeHighAccuracy), 
                R.drawable.ic_accuracy, "#EF4444", "#FEE2E2",
                "High Accuracy", "Achieved >95% prediction accuracy streak", "JAN 15, 2025");

        // Badge 3: Power User (Blue)
        setupBadge(findViewById(R.id.badgePowerUser), 
                R.drawable.ic_analytics, "#3B82F6", "#DBEAFE",
                "Power User", "Analyzed 50+ patient cases", "JAN 28, 2025");

        // Badge 4: Risk Guardian (Green)
        setupBadge(findViewById(R.id.badgeRiskGuardian), 
                R.drawable.ic_check_circle, "#22C55E", "#DCFCE7",
                "Risk Guardian", "Identified 10 critical high-risk cases", "FEB 02, 2025");
    }

    private void setupBadge(View view, int iconRes, String iconColor, String bgColor, String title, String desc, String date) {
        if (view == null) return;
        
        ImageView ivIcon = view.findViewById(R.id.ivBadgeIcon);
        MaterialCardView container = view.findViewById(R.id.ivBadgeIconContainer);
        TextView tvTitle = view.findViewById(R.id.tvBadgeTitle);
        TextView tvDesc = view.findViewById(R.id.tvBadgeDesc);
        TextView tvDate = view.findViewById(R.id.tvBadgeDate);

        ivIcon.setImageResource(iconRes);
        ivIcon.setColorFilter(Color.parseColor(iconColor));
        container.setCardBackgroundColor(Color.parseColor(bgColor));
        tvTitle.setText(title);
        tvDesc.setText(desc);
        tvDate.setText(date);
    }

    private void setupBottomNavigation() {
        View navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(MyAchievementsActivity.this, DoctorHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }

        View navCases = findViewById(R.id.navCases);
        if (navCases != null) {
            navCases.setOnClickListener(v -> {
                Intent intent = new Intent(MyAchievementsActivity.this, DoctorCasesActivity.class);
                startActivity(intent);
            });
        }

        View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(v -> {
                Intent intent = new Intent(MyAchievementsActivity.this, ReportsActivity.class);
                startActivity(intent);
            });
        }

        // navProfile is already the current section (Achievements is under Profile)
        // No logic needed or it can navigate back to the main Profile screen
        View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                Intent intent = new Intent(MyAchievementsActivity.this, DoctorProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }
    }
}
