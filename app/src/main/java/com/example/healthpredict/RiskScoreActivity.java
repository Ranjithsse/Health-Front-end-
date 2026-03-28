package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class RiskScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_score);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnAnalyzeRiskFactors).setOnClickListener(v -> {
            Intent intent = new Intent(RiskScoreActivity.this, RiskFactorsActivity.class);
            startActivity(intent);
        });

        updateUI();
    }

    private void updateUI() {
        CaseData caseData = CaseData.getInstance();
        android.widget.TextView tvRiskStatus = findViewById(R.id.tvRiskStatus);
        android.widget.TextView tvRiskScoreLabel = findViewById(R.id.tvRiskScoreLabel);
        android.view.View riskCircleBg = findViewById(R.id.riskCircleBg);
        android.widget.ImageView ivRiskIcon = findViewById(R.id.ivRiskIcon);

        // Dynamic Risk Factor Elements
        android.widget.TextView tvBioRisk = findViewById(R.id.tvBioRisk);
        com.google.android.material.progressindicator.LinearProgressIndicator progressBio = findViewById(R.id.progressBio);
        android.widget.TextView tvLifeRisk = findViewById(R.id.tvLifeRisk);
        com.google.android.material.progressindicator.LinearProgressIndicator progressLife = findViewById(R.id.progressLife);
        android.widget.TextView tvGenRisk = findViewById(R.id.tvGenRisk);
        com.google.android.material.progressindicator.LinearProgressIndicator progressGen = findViewById(R.id.progressGen);

        if (caseData.riskScore != null && !caseData.riskScore.isEmpty()) {
            // Fix score display - handle both percentage string and number
            String scoreStr = caseData.riskScore.replace("%", "");
            int scoreValue = 0;
            try { scoreValue = Integer.parseInt(scoreStr); } catch (Exception ignored) {}
            tvRiskScoreLabel.setText("Score: " + scoreStr + "/100");

            // Override backend risk level with local mobile logic as requested
            String levelLabel;
            if (scoreValue <= 45) {
                levelLabel = "Low";
            } else if (scoreValue <= 60) {
                levelLabel = "Moderate";
            } else {
                levelLabel = "High";
            }

            tvRiskStatus.setText(levelLabel + " Risk");

            // Update colors based on the new levelLabel
            int color;
            int iconRes;
            if ("High".equals(levelLabel)) {
                color = android.graphics.Color.parseColor("#991B1B"); // red-800
                iconRes = R.drawable.ic_alert_circle_fill;
                riskCircleBg.setBackgroundTintList(
                        android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#FEE2E2")));
            } else if ("Moderate".equals(levelLabel)) {
                color = android.graphics.Color.parseColor("#92400E"); // amber-800
                iconRes = R.drawable.ic_warning_outline;
                riskCircleBg.setBackgroundTintList(
                        android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#FEF3C7")));
            } else {
                color = android.graphics.Color.parseColor("#166534"); // green-800
                iconRes = R.drawable.ic_shield_check;
                riskCircleBg.setBackgroundTintList(
                        android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#DCFCE7")));
            }
            tvRiskStatus.setTextColor(color);
            ivRiskIcon.setImageResource(iconRes);
            ivRiskIcon.setColorFilter(color);

            // --- Dynamic Risk Factor Calculations ---
            
            // If backend provides detailed factors, use them. Otherwise use clinical fallback.
            if (caseData.riskFactors != null && !caseData.riskFactors.isEmpty()) {
                for (java.util.Map<String, String> factor : caseData.riskFactors) {
                    String label = factor.get("label");
                    int score = 0;
                    try { score = Integer.parseInt(factor.get("score")); } catch (Exception ignored) {}
                    
                    if ("Biological".equalsIgnoreCase(label)) updateFactor(tvBioRisk, progressBio, score);
                    else if ("Lifestyle".equalsIgnoreCase(label)) updateFactor(tvLifeRisk, progressLife, score);
                    else if ("Genetic".equalsIgnoreCase(label)) updateFactor(tvGenRisk, progressGen, score);
                }
            } else {
                // Fallback: Clinical logic if no detailed factors
                // 1. Biological Risk (Age + BP + Glucose)
                int bioScore = 15;
                try {
                    int ageVal = Integer.parseInt(caseData.age);
                    if (ageVal > 60) bioScore += 25;
                    else if (ageVal > 40) bioScore += 15;
                } catch (Exception ignored) {}
                
                if (caseData.bloodPressure != null && !"Normal".equals(caseData.bloodPressure)) bioScore += 20;
                if (caseData.glucoseLevel != null && !"Normal".equals(caseData.glucoseLevel)) bioScore += 20;
                updateFactor(tvBioRisk, progressBio, bioScore);

                // 2. Lifestyle Risk (Smoking + Activity)
                int lifeScore = 10;
                if (caseData.smokingStatus != null && caseData.smokingStatus.contains("Smoker")) lifeScore += 40;
                if ("Sedentary".equals(caseData.physicalActivity)) lifeScore += 30;
                else if ("Moderate".equals(caseData.physicalActivity)) lifeScore += 15;
                updateFactor(tvLifeRisk, progressLife, lifeScore);

                // 3. Genetic Risk (Baseline + age multiplier)
                int genScore = 15;
                try {
                    int ageVal = Integer.parseInt(caseData.age);
                    genScore += (ageVal / 10);
                } catch (Exception ignored) {}
                updateFactor(tvGenRisk, progressGen, genScore);
            }
        }
    }

    private void updateFactor(android.widget.TextView tv, com.google.android.material.progressindicator.LinearProgressIndicator progress, int score) {
        if (tv == null || progress == null) return;
        
        score = Math.min(100, Math.max(5, score));
        progress.setProgress(score);
        
        if (score <= 45) {
            tv.setText("Low");
            tv.setTextColor(android.graphics.Color.parseColor("#166534"));
            progress.setIndicatorColor(android.graphics.Color.parseColor("#22C55E"));
        } else if (score <= 60) {
            tv.setText("Moderate");
            tv.setTextColor(android.graphics.Color.parseColor("#92400E"));
            progress.setIndicatorColor(android.graphics.Color.parseColor("#EAB308"));
        } else {
            tv.setText("High");
            tv.setTextColor(android.graphics.Color.parseColor("#991B1B"));
            progress.setIndicatorColor(android.graphics.Color.parseColor("#EF4444"));
        }
    }
}
