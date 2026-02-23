package com.example.healthpredict;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class NewCaseSevenActivity extends AppCompatActivity {

    private TextView tvSummaryName, tvSummaryDemographics, tvSummaryHistory, tvSummarySystem, tvSummaryVitals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_seven);

        // Initialize TextViews
        tvSummaryName = findViewById(R.id.tvSummaryName);
        tvSummaryDemographics = findViewById(R.id.tvSummaryDemographics);
        tvSummaryHistory = findViewById(R.id.tvSummaryHistory);
        tvSummarySystem = findViewById(R.id.tvSummarySystem);
        tvSummaryVitals = findViewById(R.id.tvSummaryVitals);

        setupToolbar();

        View btnEdit = findViewById(R.id.btnEdit);
        if (btnEdit != null) {
            btnEdit.setOnClickListener(v -> finish());
        }

        MaterialButton btnSaveContinue = findViewById(R.id.btnSaveContinue);
        if (btnSaveContinue != null) {
            btnSaveContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveAssessmentData();
                    Intent intent = new Intent(NewCaseSevenActivity.this, NewCaseEightActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void saveAssessmentData() {
        if (tvSummaryName == null) return;

        // Get data from summary views
        String name = tvSummaryName.getText().toString();
        String demographics = tvSummaryDemographics.getText().toString();
        String history = tvSummaryHistory.getText().toString();
        String system = tvSummarySystem.getText().toString();
        String vitals = tvSummaryVitals.getText().toString();

        // Save to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("AssessmentPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("patient_name", name);
        editor.putString("demographics", demographics);
        editor.putString("medical_history", history);
        editor.putString("primary_system", system);
        editor.putString("vitals", vitals);
        editor.apply();

        Toast.makeText(this, "Assessment data saved successfully", Toast.LENGTH_SHORT).show();
    }
}
