package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class NewCaseFifteenActivity extends AppCompatActivity {

    private TextView tvSelectedMedication;
    private TextView tvSelectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_fifteen);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNextDosage = findViewById(R.id.btnNextDosage);
        View spinnerMedication = findViewById(R.id.spinnerMedication);
        View spinnerType = findViewById(R.id.spinnerType);
        tvSelectedMedication = findViewById(R.id.tvSelectedMedication);
        tvSelectedType = findViewById(R.id.tvSelectedType);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spinnerMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMedicationDialog();
            }
        });

        spinnerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeDialog();
            }
        });

        btnNextDosage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String med = tvSelectedMedication.getText().toString();
                String type = tvSelectedType.getText().toString();

                if (med.equals("Select Medication") || type.equals("Select Type")) {
                    android.widget.Toast.makeText(NewCaseFifteenActivity.this, "Please select both Medication and Treatment Type", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }

                CaseData data = CaseData.getInstance();
                data.primaryMedication = med;
                data.treatmentType = type;
                startActivity(new Intent(NewCaseFifteenActivity.this, NewCaseSixteenActivity.class));
            }
        });
    }

    private void showMedicationDialog() {
        String[] options = {"ACE Inhibitors", "Beta Blockers", "Statins", "Insulin Therapy", "Chemotherapy"};
        new MaterialAlertDialogBuilder(this)
                .setTitle("Select Medication / Therapy")
                .setItems(options, (dialog, which) -> {
                    tvSelectedMedication.setText(options[which]);
                })
                .show();
    }

    private void showTypeDialog() {
        String[] options = {"Preventative", "Curative", "Palliative"};
        new MaterialAlertDialogBuilder(this)
                .setTitle("Select Treatment Type")
                .setItems(options, (dialog, which) -> {
                    tvSelectedType.setText(options[which]);
                })
                .show();
    }
}
