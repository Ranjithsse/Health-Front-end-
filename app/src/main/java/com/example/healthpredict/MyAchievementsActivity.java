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
        fetchUserStats();

        View navHome = findViewById(R.id.navHome);
        if (navHome != null)
            navHome.setOnClickListener(v -> {
                startActivity(new Intent(this, DoctorHomeActivity.class));
                finish();
            });

        View navCases = findViewById(R.id.navCases);
        if (navCases != null)
            navCases.setOnClickListener(v -> {
                startActivity(new Intent(this, DoctorCasesActivity.class));
                finish();
            });

        View navReports = findViewById(R.id.navReports);
        if (navReports != null)
            navReports.setOnClickListener(v -> {
                startActivity(new Intent(this, ReportsActivity.class));
                finish();
            });
    }

    private void fetchUserStats() {
        RetrofitClient.getApiService(this).getSettings().enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateUserStatsUI(response.body());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
            }
        });
    }

    private void updateUserStatsUI(Map<String, Object> user) {
        TextView tvLevelInfo = findViewById(R.id.tvLevelInfo);
        TextView tvXpProgress = findViewById(R.id.tvXpProgress);
        android.widget.ProgressBar pbLevelProgress = findViewById(R.id.pbLevelProgress);

        if (user.containsKey("level")) {
            Object level = user.get("level");
            String specialization = (String) user.get("specialization");
            if (specialization == null || specialization.isEmpty())
                specialization = "Expert Provider";
            tvLevelInfo.setText("Level " + level + " • " + specialization);
        }

        if (user.containsKey("xp_points") && user.containsKey("level")) {
            int totalXp = ((Number) user.get("xp_points")).intValue();
            int currentLevel = ((Number) user.get("level")).intValue();
            
            // Example Logic: Each level requires 100 XP more
            int nextLevelThreshold = currentLevel * 100;
            
            tvXpProgress.setText(totalXp + "/" + nextLevelThreshold + " XP");
            pbLevelProgress.setMax(nextLevelThreshold);
            pbLevelProgress.setProgress(totalXp);
        }
    }

    private void fetchAchievements() {
        RetrofitClient.getApiService(this).getAchievements().enqueue(new Callback<List<Map<String, Object>>>() {
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

    private void updateBadges(List<Map<String, Object>> userAchievements) {
        for (Map<String, Object> userAchieve : userAchievements) {
            Map<String, Object> details = (Map<String, Object>) userAchieve.get("achievement_details");
            if (details == null)
                continue;

            String id = (String) details.get("achievement_id");
            String title = (String) details.get("title");
            String desc = (String) details.get("description");
            String color = (String) details.get("color_hex");
            String iconSlug = (String) details.get("icon_slug");

            boolean isUnlocked = Boolean.TRUE.equals(userAchieve.get("is_unlocked"));
            String dateStr = (String) userAchieve.get("date_earned");

            if (isUnlocked && dateStr != null) {
                try {
                    // Simple parsing for YYYY-MM-DD
                    java.text.SimpleDateFormat inFormat = new java.text.SimpleDateFormat("yyyy-MM-dd",
                            java.util.Locale.US);
                    java.text.SimpleDateFormat outFormat = new java.text.SimpleDateFormat("MMM dd, yyyy",
                            java.util.Locale.US);
                    java.util.Date d = inFormat.parse(dateStr);
                    dateStr = outFormat.format(d);
                } catch (Exception e) {
                    // fallback to original if parsing fails
                }
            } else {
                dateStr = isUnlocked ? "Unlocked" : "Locked";
                color = "#F1F5F9"; // Light gray for locked
            }

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
                int iconResId = getResources().getIdentifier(iconSlug, "drawable", getPackageName());
                if (iconResId == 0)
                    iconResId = R.drawable.ic_star_badge;

                setupBadgeItem(badgeView, iconResId, color, title, desc, dateStr, isUnlocked);
            }
        }
    }

    private void setupBadgeItem(View view, int iconRes, String bgColor, String title, String desc, String date,
            boolean isUnlocked) {
        ImageView ivIcon = view.findViewById(R.id.ivBadgeIcon);
        MaterialCardView container = view.findViewById(R.id.ivBadgeIconContainer);
        TextView tvTitle = view.findViewById(R.id.tvBadgeTitle);
        TextView tvDesc = view.findViewById(R.id.tvBadgeDesc);
        TextView tvDate = view.findViewById(R.id.tvBadgeDate);

        if (ivIcon != null) {
            ivIcon.setImageResource(iconRes);
            ivIcon.setAlpha(isUnlocked ? 1.0f : 0.4f);
        }

        if (container != null && bgColor != null) {
            try {
                // Ensure we handle transparency or specific shades if needed
                container.setCardBackgroundColor(Color.parseColor(bgColor));
            } catch (Exception e) {
                container.setCardBackgroundColor(Color.parseColor("#F1F5F9"));
            }
        }

        if (tvTitle != null) {
            tvTitle.setText(title);
            tvTitle.setAlpha(isUnlocked ? 1.0f : 0.5f);
        }
        if (tvDesc != null) {
            tvDesc.setText(desc);
            tvDesc.setAlpha(isUnlocked ? 1.0f : 0.5f);
        }
        if (tvDate != null) {
            tvDate.setText(date);
            tvDate.setTextColor(isUnlocked ? Color.parseColor("#94A3B8") : Color.parseColor("#CBD5E1"));
        }

        // Slightly gray out the whole card if locked
        if (view instanceof MaterialCardView) {
            ((MaterialCardView) view).setAlpha(isUnlocked ? 1.0f : 0.8f);
        }
    }
}
