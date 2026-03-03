package com.example.healthpredict;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.util.Calendar;
import java.util.Locale;

public class PatientOutcomeActivity extends AppCompatActivity {

    private String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_outcome);

        ImageView ivBack = findViewById(R.id.ivBack);
        MaterialButton btnSaveOutcome = findViewById(R.id.btnSaveOutcome);
        TextView tvPatientInfoHeader = findViewById(R.id.tvPatientInfoHeader);
        View layoutHealthStatus = findViewById(R.id.layoutHealthStatus);
        TextView tvHealthStatus = findViewById(R.id.tvHealthStatus);
        View layoutFollowUpDate = findViewById(R.id.layoutFollowUpDate);
        TextView tvFollowUpDate = findViewById(R.id.tvFollowUpDate);

        // Get data from intent
        String patientName = getIntent().getStringExtra("PATIENT_NAME");
        patientId = getIntent().getStringExtra("PATIENT_ID");

        if (tvPatientInfoHeader != null && patientName != null && patientId != null) {
            tvPatientInfoHeader.setText(String.format("Patient #%s (%s)", patientId, patientName));
        }

        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        if (layoutHealthStatus != null) {
            layoutHealthStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHealthStatusDialog(tvHealthStatus);
                }
            });
        }

        if (layoutFollowUpDate != null) {
            layoutFollowUpDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog(tvFollowUpDate);
                }
            });
        }

        if (btnSaveOutcome != null) {
            btnSaveOutcome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveOutcome(tvHealthStatus.getText().toString());
                }
            });
        }
    }

    private void saveOutcome(String status) {
        if (patientId == null || patientId.isEmpty()) {
            Toast.makeText(this, "Error: Patient ID not found", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("PatientOutcomes", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(patientId, status);
        editor.apply();

        Toast.makeText(this, "Outcome saved for " + patientId, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showHealthStatusDialog(TextView tvHealthStatus) {
        final String[] options = {"Successful Recovery", "Stable Condition", "Condition Declined", "Complications Arising"};
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Health Status");
        builder.setItems(options, (dialog, which) -> {
            if (tvHealthStatus != null) {
                tvHealthStatus.setText(options[which]);
            }
        });
        builder.show();
    }

    private void showDatePickerDialog(TextView tvDate) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = String.format(Locale.getDefault(), "%02d-%02d-%d", selectedDay, selectedMonth + 1, selectedYear);
                    if (tvDate != null) {
                        tvDate.setText(selectedDate);
                        tvDate.setTextColor(Color.parseColor("#0F172A")); // Set to solid text color
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}
