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
                // Save data to singleton
                if (etPatientId != null) {
                    String input = etPatientId.getText().toString();
                    caseData.patientId = input;
                    caseData.patientName = input; // Since the input is Name / ID
                }
                if (tvDate != null) {
                    caseData.date = tvDate.getText().toString();
                }

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
