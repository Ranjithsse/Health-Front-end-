package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class NewCaseTwentyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_twenty);

        ImageView btnBackHeader = findViewById(R.id.btnBackHeader);
        MaterialButton btnRunPrediction = findViewById(R.id.btnRunPrediction);
        MaterialButton btnBackEdit = findViewById(R.id.btnBackEdit);

        TextView tvSummaryPatient = findViewById(R.id.tvSummaryPatient);
        TextView tvSummaryVitals = findViewById(R.id.tvSummaryVitals);
        TextView tvSummarySystem = findViewById(R.id.tvSummarySystem);
        TextView tvSummaryFiles = findViewById(R.id.tvSummaryFiles);
        TextView tvSummaryPlan = findViewById(R.id.tvSummaryPlan);

        CaseData data = CaseData.getInstance();

        if (tvSummaryPatient != null) {
            tvSummaryPatient.setText(data.patientId + " (" + data.patientName + ")");
        }
        if (tvSummaryVitals != null) {
            tvSummaryVitals.setText(data.bloodPressure + ", " + data.gender + ", " + data.bloodGroup);
        }
        if (tvSummarySystem != null) {
            tvSummarySystem.setText(data.primarySystem);
        }
        if (tvSummaryFiles != null) {
            tvSummaryFiles.setText(data.fileUri.isEmpty() ? "No files uploaded" : data.fileUri);
        }
        if (tvSummaryPlan != null) {
            String plan = data.treatmentType + ", " + data.interventionType;
            if (data.adjuvantTherapyRequired)
                plan += ", Adjuvant Required";
            tvSummaryPlan.setText(plan);
        }

        btnBackHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRunPrediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewCaseTwentyActivity.this, AiPredictionActivity.class));
            }
        });

        btnBackEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
