package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NewCaseFifteenActivity extends AppCompatActivity {

    private TextView tvSelectedMedication;
    private TextView tvSelectedType;
    private final String[] medications = {"ACE Inhibitors", "Beta Blockers", "Statins", "Insulin Therapy", "Chemotherapy"};
    private final String[] treatmentTypes = {"Preventative", "Curative", "Palliative"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_fifteen);

        tvSelectedMedication = findViewById(R.id.tvSelectedMedication);
        tvSelectedType = findViewById(R.id.tvSelectedType);

        setupToolbar();
        setupSpinners();
        setupButtons();
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void setupSpinners() {
        findViewById(R.id.spinnerMedication).setOnClickListener(v -> showMedicationDialog());
        findViewById(R.id.spinnerType).setOnClickListener(v -> showTreatmentTypeDialog());
    }

    private void showMedicationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Medication");
        builder.setItems(medications, (dialog, which) -> {
            tvSelectedMedication.setText(medications[which]);
        });
        builder.show();
    }

    private void showTreatmentTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Treatment Type");
        builder.setItems(treatmentTypes, (dialog, which) -> {
            tvSelectedType.setText(treatmentTypes[which]);
        });
        builder.show();
    }

    private void setupButtons() {
        findViewById(R.id.btnNextDosage).setOnClickListener(v -> {
            Intent intent = new Intent(NewCaseFifteenActivity.this, NewCaseSixteenActivity.class);
            startActivity(intent);
        });
    }
}
