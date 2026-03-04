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

public class ThreeYearPredictionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_year_prediction);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnBackFooter = findViewById(R.id.btnBackFooter);
        MaterialButton btnViewFiveYear = findViewById(R.id.btnViewFiveYear);

        TextView tvProbability = findViewById(R.id.tvProbability); // I should check if this ID exists or add it
        CircularProgressIndicator circularProgress = findViewById(R.id.circularProgress);
        TextView tvAnalysisDescription = findViewById(R.id.tvAnalysisDescription); // I should check if this ID exists
                                                                                   // or add it

        CaseData caseData = CaseData.getInstance();

        if (caseData != null) {
            String prob = caseData.threeYearPrediction != null ? caseData.threeYearPrediction : "0%";
            if (tvProbability != null)
                tvProbability.setText(prob);

            try {
                int progress = Integer.parseInt(prob.replace("%", "").trim());
                if (circularProgress != null)
                    circularProgress.setProgress(progress);
            } catch (Exception ignored) {
            }

            if (tvAnalysisDescription != null)
                tvAnalysisDescription.setText(caseData.threeYearInsight);
        }

        btnBack.setOnClickListener(v -> finish());
        btnBackFooter.setOnClickListener(v -> finish());

        btnViewFiveYear.setOnClickListener(v -> {
            startActivity(new Intent(ThreeYearPredictionActivity.this, FiveYearPredictionActivity.class));
        });
    }
}
