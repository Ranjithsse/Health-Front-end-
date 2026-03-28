package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthpredict.network.RetrofitClient;
import com.example.healthpredict.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.HashMap;
import java.util.Map;
import android.widget.CheckBox;
import android.widget.Toast;
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
        CheckBox cbEmailNotify = findViewById(R.id.cbEmailNotify);
        CheckBox cbConfidenceIntervals = findViewById(R.id.cbConfidenceIntervals);
        CheckBox cbExperimentalModels = findViewById(R.id.cbExperimentalModels);

        loadSettingsFromServer();

        btnBack.setOnClickListener(v -> finish());

        btnDeleteAccount.setOnClickListener(v -> {
            startActivity(new Intent(ProfileSettingsActivity.this, DeleteAccountDoctorActivity.class));
        });

        setupSensitivityDropdown();
        setupCheckboxListeners();

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                sensitivityOptions);

        if (autoCompleteSensitivity != null) {
            autoCompleteSensitivity.setAdapter(adapter);

            autoCompleteSensitivity.setOnItemClickListener((parent, view, position, id) -> {
                String selection = (String) parent.getItemAtPosition(position);
                updateSettingOnServer("prediction_sensitivity", selection);
            });
        }
    }

    private void setupCheckboxListeners() {
        CheckBox cbEmailNotify = findViewById(R.id.cbEmailNotify);
        CheckBox cbConfidenceIntervals = findViewById(R.id.cbConfidenceIntervals);
        CheckBox cbExperimentalModels = findViewById(R.id.cbExperimentalModels);

        if (cbEmailNotify != null) {
            cbEmailNotify.setOnCheckedChangeListener((buttonView, isChecked) -> {
                updateSettingOnServer("email_notifications", isChecked);
            });
        }

        if (cbConfidenceIntervals != null) {
            cbConfidenceIntervals.setOnCheckedChangeListener((buttonView, isChecked) -> {
                updateSettingOnServer("show_confidence_intervals", isChecked);
            });
        }

        if (cbExperimentalModels != null) {
            cbExperimentalModels.setOnCheckedChangeListener((buttonView, isChecked) -> {
                updateSettingOnServer("include_experimental_models", isChecked);
            });
        }
    }

    private void loadSettingsFromServer() {
        ApiService apiService = RetrofitClient.getApiService(this);
        apiService.getSettings().enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Object> settings = response.body();

                    if (settings.containsKey("prediction_sensitivity")) {
                        autoCompleteSensitivity.setText((String) settings.get("prediction_sensitivity"), false);
                    }

                    CheckBox cbEmailNotify = findViewById(R.id.cbEmailNotify);
                    if (settings.containsKey("email_notifications") && cbEmailNotify != null) {
                        cbEmailNotify.setChecked((Boolean) settings.get("email_notifications"));
                    }

                    CheckBox cbConfidenceIntervals = findViewById(R.id.cbConfidenceIntervals);
                    if (settings.containsKey("show_confidence_intervals") && cbConfidenceIntervals != null) {
                        cbConfidenceIntervals.setChecked((Boolean) settings.get("show_confidence_intervals"));
                    }

                    CheckBox cbExperimentalModels = findViewById(R.id.cbExperimentalModels);
                    if (settings.containsKey("include_experimental_models") && cbExperimentalModels != null) {
                        cbExperimentalModels.setChecked((Boolean) settings.get("include_experimental_models"));
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(ProfileSettingsActivity.this, "Failed to load settings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSettingOnServer(String key, Object value) {
        Map<String, Object> updates = new HashMap<>();
        updates.put(key, value);

        ApiService apiService = RetrofitClient.getApiService(this);
        apiService.updateSettings(updates).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful()) {
                    // Success
                } else {
                    Toast.makeText(ProfileSettingsActivity.this, "Failed to update " + key, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(ProfileSettingsActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
