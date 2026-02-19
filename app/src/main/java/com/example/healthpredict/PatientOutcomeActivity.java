package com.example.healthpredict;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Locale;

public class PatientOutcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_outcome);

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        TextView tvPatientInfoHeader = findViewById(R.id.tvPatientInfoHeader);
        if (getIntent() != null && tvPatientInfoHeader != null) {
            String name = getIntent().getStringExtra("PATIENT_NAME");
            String id = getIntent().getStringExtra("PATIENT_ID");
            if (name != null && id != null) {
                tvPatientInfoHeader.setText("Patient #" + id + " (" + name + ")");
            }
        }

        // Health Status Logic
        final TextView tvHealthStatus = findViewById(R.id.tvHealthStatus);
        View layoutHealthStatus = findViewById(R.id.layoutHealthStatus);

        if (layoutHealthStatus != null && tvHealthStatus != null) {
            layoutHealthStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHealthStatusPopup(v, tvHealthStatus);
                }
            });
        }

        // Follow-up Date Logic
        final TextView tvFollowUpDate = findViewById(R.id.tvFollowUpDate);
        View layoutFollowUpDate = findViewById(R.id.layoutFollowUpDate);

        if (layoutFollowUpDate != null && tvFollowUpDate != null) {
            layoutFollowUpDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePicker(tvFollowUpDate);
                }
            });
        }

        // Save Outcome Logic
        View btnSaveOutcome = findViewById(R.id.btnSaveOutcome);
        if (btnSaveOutcome != null) {
            btnSaveOutcome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Logic to save outcome
                    Toast.makeText(PatientOutcomeActivity.this, "Outcome saved successfully", Toast.LENGTH_SHORT).show();
                    
                    // Return to DoctorHomeActivity
                    Intent intent = new Intent(PatientOutcomeActivity.this, DoctorHomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    private void showHealthStatusPopup(View anchor, final TextView tvStatus) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenu().add("Successful Recovery");
        popup.getMenu().add("Stable Condition");
        popup.getMenu().add("Condition Declined");
        popup.getMenu().add("Complications Arising");

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                tvStatus.setText(item.getTitle());
                tvStatus.setTextColor(getResources().getColor(android.R.color.black));
                return true;
            }
        });
        popup.show();
    }

    private void showDatePicker(final TextView tvDate) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = String.format(Locale.getDefault(), "%02d-%02d-%d", dayOfMonth, monthOfYear + 1, year);
                        tvDate.setText(selectedDate);
                        tvDate.setTextColor(getResources().getColor(android.R.color.black));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}