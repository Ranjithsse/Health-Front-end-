package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

import android.widget.TextView;
import com.example.healthpredict.CaseData;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class FiveYearPredictionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_year_prediction);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnBackFooter = findViewById(R.id.btnBackFooter);
        MaterialButton btnViewRiskAnalysis = findViewById(R.id.btnViewRiskAnalysis);

        TextView tvProbability = findViewById(R.id.tvProbability);
        CircularProgressIndicator circularProgress = findViewById(R.id.circularProgress);
        TextView tvAnalysisDescription = findViewById(R.id.tvAnalysisDescription);

        CaseData caseData = CaseData.getInstance();

        if (caseData != null) {
            String prob = caseData.fiveYearPrediction != null ? caseData.fiveYearPrediction : "0%";
            if (tvProbability != null)
                tvProbability.setText(prob);

            try {
                int progress = (int) Double.parseDouble(prob.replace("%", "").trim());
                if (circularProgress != null)
                    circularProgress.setProgress(progress);
            } catch (Exception ignored) {
            }

            if (tvAnalysisDescription != null)
                tvAnalysisDescription.setText(caseData.fiveYearInsight);
        }

        btnBack.setOnClickListener(v -> finish());
        btnBackFooter.setOnClickListener(v -> finish());

        btnViewRiskAnalysis.setOnClickListener(v -> {
            startActivity(new Intent(FiveYearPredictionActivity.this, RiskScoreActivity.class));
        });
    }
}
