package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class NewCaseSevenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_seven);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnEdit = findViewById(R.id.btnEdit);
        MaterialButton btnSaveContinue = findViewById(R.id.btnSaveContinue);

        displaySummaryData();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSaveContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewCaseSevenActivity.this, NewCaseEightActivity.class));
            }
        });
    }

    private void displaySummaryData() {
        CaseData data = CaseData.getInstance();

        TextView tvName = findViewById(R.id.tvSummaryName);
        TextView tvDemographics = findViewById(R.id.tvSummaryDemographics);
        TextView tvHistory = findViewById(R.id.tvSummaryHistory);
        TextView tvSystem = findViewById(R.id.tvSummarySystem);
        TextView tvVitals = findViewById(R.id.tvSummaryVitals);

        if (tvName != null) {
            tvName.setText(data.patientName.isEmpty() ? "Not Provided" : data.patientName);
        }

        if (tvDemographics != null) {
            String demographics = String.format("%s, %s, %s", 
                data.age.isEmpty() ? "N/A" : data.age,
                data.gender.isEmpty() ? "N/A" : data.gender,
                data.smokingStatus.isEmpty() ? "Non-smoker" : data.smokingStatus);
            tvDemographics.setText(demographics);
        }

        if (tvHistory != null) {
            if (data.medicalConditions.isEmpty()) {
                tvHistory.setText("None Observed");
            } else {
                StringBuilder conditions = new StringBuilder();
                for (int i = 0; i < data.medicalConditions.size(); i++) {
                    conditions.append(data.medicalConditions.get(i));
                    if (i < data.medicalConditions.size() - 1) conditions.append(", ");
                }
                tvHistory.setText(conditions.toString());
            }
        }

        if (tvSystem != null) {
            tvSystem.setText(data.primarySystem.isEmpty() ? "General Assessment" : data.primarySystem);
        }

        if (tvVitals != null) {
            String vitals = String.format("BP: %s, Glucose: %s", 
                data.bloodPressure.isEmpty() ? "N/A" : data.bloodPressure,
                data.glucoseLevel.isEmpty() ? "N/A" : data.glucoseLevel);
            tvVitals.setText(vitals);
        }
    }
}
