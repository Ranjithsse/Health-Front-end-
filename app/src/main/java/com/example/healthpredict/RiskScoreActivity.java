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

        if (caseData.riskLevel != null && !caseData.riskLevel.isEmpty()) {
            tvRiskStatus.setText(caseData.riskLevel + " Risk");
            tvRiskScoreLabel.setText("Score: " + caseData.riskScore.replace("%", "") + "/100");

            // Update colors based on risk level
            int color;
            int iconRes;
            switch (caseData.riskLevel) {
                case "Critical":
                case "High":
                    color = android.graphics.Color.parseColor("#991B1B"); // red-800
                    iconRes = R.drawable.ic_alert_circle_fill;
                    riskCircleBg.setBackgroundTintList(
                            android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#FEE2E2")));
                    break;
                case "Moderate":
                    color = android.graphics.Color.parseColor("#92400E"); // amber-800
                    iconRes = R.drawable.ic_warning_outline;
                    riskCircleBg.setBackgroundTintList(
                            android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#FEF3C7")));
                    break;
                default:
                    color = android.graphics.Color.parseColor("#166534"); // green-800
                    iconRes = R.drawable.ic_shield_check;
                    riskCircleBg.setBackgroundTintList(
                            android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#DCFCE7")));
                    break;
            }
            tvRiskStatus.setTextColor(color);
            ivRiskIcon.setImageResource(iconRes);
            ivRiskIcon.setColorFilter(color);
        }
    }
}
