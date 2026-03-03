package com.example.healthpredict;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class ProfileSettingsActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteSensitivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        autoCompleteSensitivity = findViewById(R.id.autoCompleteSensitivity);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileSettingsActivity.this, DeleteAccountDoctorActivity.class));
            }
        });

        setupSensitivityDropdown();

        findViewById(R.id.navHome).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorHomeActivity.class));
            finish();
        });

        findViewById(R.id.navCases).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorCasesActivity.class));
            finish();
        });

        findViewById(R.id.navReports).setOnClickListener(v -> {
            startActivity(new Intent(this, ReportsActivity.class));
            finish();
        });

        findViewById(R.id.navProfile).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorProfileActivity.class));
            finish();
        });
    }

    private void setupSensitivityDropdown() {
        String[] sensitivityOptions = {
            "Standard (Balanced)", 
            "High Sensitivity (Minimize False Negatives)", 
            "High Specificity (Minimize False Positives)"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sensitivityOptions);
        
        if (autoCompleteSensitivity != null) {
            autoCompleteSensitivity.setAdapter(adapter);

            // Load saved preference
            SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
            String savedSensitivity = prefs.getString("PredictionSensitivity", "Standard (Balanced)");
            autoCompleteSensitivity.setText(savedSensitivity, false);

            autoCompleteSensitivity.setOnItemClickListener((parent, view, position, id) -> {
                String selection = (String) parent.getItemAtPosition(position);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("PredictionSensitivity", selection);
                editor.apply();
            });
        }
    }
}
