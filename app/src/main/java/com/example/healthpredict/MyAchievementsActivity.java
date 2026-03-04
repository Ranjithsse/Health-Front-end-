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

import com.example.healthpredict.network.RetrofitClient;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        fetchAchievements();

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

    private void fetchAchievements() {
        RetrofitClient.getApiService().getAchievements().enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateBadges(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {
                // Fallback or Toast
            }
        });
    }

    private void updateBadges(List<Map<String, Object>> achievements) {
        // Simple mapping for this demo/session
        for (Map<String, Object> achieve : achievements) {
            String id = (String) achieve.get("achievement_id");
            String title = (String) achieve.get("title");
            String desc = (String) achieve.get("description");
            String color = (String) achieve.get("color_hex");
            String date = (String) achieve.get("date_earned");
            if (date == null)
                date = "Unlocked";

            View badgeView = null;
            if ("first_analysis".equals(id))
                badgeView = findViewById(R.id.badgeFirstAnalysis);
            else if ("high_accuracy".equals(id))
                badgeView = findViewById(R.id.badgeHighAccuracy);
            else if ("power_user".equals(id))
                badgeView = findViewById(R.id.badgePowerUser);
            else if ("risk_guardian".equals(id))
                badgeView = findViewById(R.id.badgeRiskGuardian);

            if (badgeView != null) {
                setupBadgeItem(badgeView, R.drawable.ic_star_badge, color, title, desc, date);
            }
        }
    }

    private void setupBadgeItem(View view, int iconRes, String bgColor, String title, String desc, String date) {
        ImageView ivIcon = view.findViewById(R.id.ivBadgeIcon);
        MaterialCardView container = view.findViewById(R.id.ivBadgeIconContainer);
        TextView tvTitle = view.findViewById(R.id.tvBadgeTitle);
        TextView tvDesc = view.findViewById(R.id.tvBadgeDesc);
        TextView tvDate = view.findViewById(R.id.tvBadgeDate);

        if (ivIcon != null)
            ivIcon.setImageResource(iconRes);
        if (container != null && bgColor != null) {
            try {
                container.setCardBackgroundColor(Color.parseColor(bgColor));
            } catch (Exception e) {
                container.setCardBackgroundColor(Color.parseColor("#FEF9C3"));
            }
        }
        if (tvTitle != null)
            tvTitle.setText(title);
        if (tvDesc != null)
            tvDesc.setText(desc);
        if (tvDate != null)
            tvDate.setText(date);
    }
}
