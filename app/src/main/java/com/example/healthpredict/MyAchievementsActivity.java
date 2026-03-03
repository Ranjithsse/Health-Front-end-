package com.example.healthpredict;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class MyAchievementsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_achievements);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnBackToProfile = findViewById(R.id.btnBackToProfile);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (btnBackToProfile != null) {
            btnBackToProfile.setOnClickListener(v -> finish());
        }

        setupBadges();

        findViewById(R.id.navHome).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorHomeActivity.class));
            finish();
        });

        findViewById(R.id.navCases).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorCasesActivity.class));
            finish();
        });

        findViewById(R.id.navReports).setOnClickListener(v -> {
            startActivity(new Intent(this, ReportsActivity.class));
            finish();
        });
    }

    private void setupBadges() {
        // First Analysis
        View badge1 = findViewById(R.id.badgeFirstAnalysis);
        if (badge1 != null) {
            setupBadgeItem(badge1, R.drawable.ic_star_badge, "#FEF9C3", "First Analysis", 
                    "Completed your first patient risk assessment", "JAN 10, 2025");
        }

        // High Accuracy
        View badge2 = findViewById(R.id.badgeHighAccuracy);
        if (badge2 != null) {
            setupBadgeItem(badge2, R.drawable.ic_target_badge, "#FEE2E2", "High Accuracy", 
                    "Achieved >95% prediction accuracy streak", "JAN 15, 2025");
        }

        // Power User
        View badge3 = findViewById(R.id.badgePowerUser);
        if (badge3 != null) {
            setupBadgeItem(badge3, R.drawable.ic_bolt_badge, "#DBEAFE", "Power User", 
                    "Analyzed 50+ patient cases", "JAN 28, 2025");
        }

        // Risk Guardian
        View badge4 = findViewById(R.id.badgeRiskGuardian);
        if (badge4 != null) {
            setupBadgeItem(badge4, R.drawable.ic_shield_check_badge, "#DCFCE7", "Risk Guardian", 
                    "Identified 10 critical high-risk cases", "FEB 02, 2025");
        }
    }

    private void setupBadgeItem(View view, int iconRes, String bgColor, String title, String desc, String date) {
        ImageView ivIcon = view.findViewById(R.id.ivBadgeIcon);
        MaterialCardView container = view.findViewById(R.id.ivBadgeIconContainer);
        TextView tvTitle = view.findViewById(R.id.tvBadgeTitle);
        TextView tvDesc = view.findViewById(R.id.tvBadgeDesc);
        TextView tvDate = view.findViewById(R.id.tvBadgeDate);

        if (ivIcon != null) ivIcon.setImageResource(iconRes);
        if (container != null) container.setCardBackgroundColor(Color.parseColor(bgColor));
        if (tvTitle != null) tvTitle.setText(title);
        if (tvDesc != null) tvDesc.setText(desc);
        if (tvDate != null) tvDate.setText(date);
    }
}
