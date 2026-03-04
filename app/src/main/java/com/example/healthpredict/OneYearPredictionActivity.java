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

public class OneYearPredictionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_year_prediction);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnViewThreeYear = findViewById(R.id.btnViewThreeYear);

        TextView tvProbability = findViewById(R.id.tvProbability);
        CircularProgressIndicator circularProgress = findViewById(R.id.circularProgress);
        TextView tvAnalysisDescription = findViewById(R.id.tvAnalysisDescription);

        CaseData caseData = CaseData.getInstance();

        if (caseData != null) {
            String prob = caseData.oneYearPrediction != null ? caseData.oneYearPrediction : "0%";
            if (tvProbability != null)
                tvProbability.setText(prob);

            try {
                int progress = (int) Double.parseDouble(prob.replace("%", "").trim());
                if (circularProgress != null)
                    circularProgress.setProgress(progress);
            } catch (Exception ignored) {
            }

            if (tvAnalysisDescription != null)
                tvAnalysisDescription.setText(caseData.oneYearInsight);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnViewThreeYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OneYearPredictionActivity.this, ThreeYearPredictionActivity.class));
            }
        });
    }
}
