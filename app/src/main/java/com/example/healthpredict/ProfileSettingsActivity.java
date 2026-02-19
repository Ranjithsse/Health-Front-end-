package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        setupToolbar();
        setupSensitivityDropdown();
        setupDeleteAccount();
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> onBackPressed());
        }
    }

    private void setupDeleteAccount() {
        View btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        if (btnDeleteAccount != null) {
            btnDeleteAccount.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileSettingsActivity.this, DeleteAccountDoctorActivity.class);
                startActivity(intent);
            });
        }
    }

    private void setupSensitivityDropdown() {
        String[] sensitivityOptions = new String[]{
                "Standard (Balanced)",
                "High Sensitivity (Minimize False Negatives)",
                "High Specificity (Minimize False Positives)"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                sensitivityOptions
        );

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteSensitivity);
        if (autoCompleteTextView != null) {
            autoCompleteTextView.setAdapter(adapter);
            // Ensure the dropdown shows on click even if there's text
            autoCompleteTextView.setOnClickListener(v -> autoCompleteTextView.showDropDown());
        }
    }
}
