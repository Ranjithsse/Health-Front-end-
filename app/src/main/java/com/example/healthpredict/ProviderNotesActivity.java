package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class ProviderNotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_notes);

        final EditText etNotes = findViewById(R.id.etProviderNotes);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnGenerateReport).setOnClickListener(v -> {
            String notes = etNotes.getText().toString();
            
            // In a real app, these would come from previous screens or a database.
            // Passing sample processed data to simulate "details being processed".
            Intent intent = new Intent(ProviderNotesActivity.this, FinalReportActivity.class);
            intent.putExtra("PATIENT_NAME", "Robert Wilson");
            intent.putExtra("PATIENT_ID", "1024");
            intent.putExtra("PATIENT_AGE", "45");
            intent.putExtra("PATIENT_GENDER", "Male");
            intent.putExtra("PREDICTION_TEXT", "98.2% Stability Probability\n(1-Year)");
            intent.putExtra("RISK_LEVEL", "Low");
            intent.putExtra("PROTOCOL", "ACE Inhibitors");
            intent.putExtra("INTERVENTION", "Non-Invasive");
            intent.putExtra("MONITORING", "Standard");
            intent.putExtra("PROVIDER_NOTES", notes.isEmpty() ? "No notes added." : notes);

            startActivity(intent);
        });
    }
}
