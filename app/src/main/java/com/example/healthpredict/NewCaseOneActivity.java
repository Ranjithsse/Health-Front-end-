package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.datepicker.MaterialDatePicker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class NewCaseOneActivity extends AppCompatActivity {

    private TextView tvDate;
    private EditText etPatientId;
    private CaseData caseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_one);

        caseData = CaseData.getInstance();
        caseData.reset(); // Starting a new case

        tvDate = findViewById(R.id.tvDate);
        etPatientId = findViewById(R.id.etPatientId);
        View btnDatePicker = findViewById(R.id.btnDatePicker);

        if (btnDatePicker != null) {
            btnDatePicker.setOnClickListener(v -> showDatePicker());
        }

        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> onBackPressed());
        }

        View btnNext = findViewById(R.id.btnNext);
        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                String patientId = etPatientId != null ? etPatientId.getText().toString().trim() : "";
                String dateStr = tvDate != null ? tvDate.getText().toString().trim() : "";

                if (patientId.isEmpty()) {
                    android.widget.Toast.makeText(this, "Please enter Patient Name or ID", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dateStr.isEmpty() || dateStr.equalsIgnoreCase("YYYY-MM-DD")) {
                    android.widget.Toast.makeText(this, "Please select an assessment date", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save data to singleton
                caseData.patientId = patientId;
                caseData.patientName = patientId; // Since the input is Name / ID
                caseData.date = dateStr;

                // Navigate to activity_new_case_two.xml
                Intent intent = new Intent(NewCaseOneActivity.this, NewCaseTwoActivity.class);
                startActivity(intent);
            });
        }
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            TimeZone timeZoneUTC = TimeZone.getDefault();
            int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date(selection + offsetFromUTC);
            tvDate.setText(simpleFormat.format(date));
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }
}
