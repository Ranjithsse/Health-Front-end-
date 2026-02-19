package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.datepicker.MaterialDatePicker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class NewCaseOneActivity extends AppCompatActivity {

    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_one);

        tvDate = findViewById(R.id.tvDate);
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
            // Get the offset from our timezone and UTC.
            TimeZone timeZoneUTC = TimeZone.getDefault();
            // It will be negative, so that's the -1
            int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
            // Create a date format, then a date object with our offset
            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date(selection + offsetFromUTC);
            tvDate.setText(simpleFormat.format(date));
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }
}
