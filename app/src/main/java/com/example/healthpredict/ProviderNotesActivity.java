package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class ProviderNotesActivity extends AppCompatActivity {

    private CaseData caseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_notes);

        caseData = CaseData.getInstance();
        final EditText etNotes = findViewById(R.id.etProviderNotes);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnGenerateReport).setOnClickListener(v -> {
            // 1. Capture the provider notes
            caseData.providerNotes = etNotes.getText().toString();
            
            // 2. Set the current date if not set
            if (caseData.date.isEmpty()) {
                caseData.date = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(new java.util.Date());
            }

            // 3. Save the finalized case to history so it appears in Home, Cases, and Reports
            HistoryManager.getInstance().addCase(caseData);
            
            // 4. Navigate to FinalReportActivity
            Intent intent = new Intent(ProviderNotesActivity.this, FinalReportActivity.class);
            startActivity(intent);
        });
    }
}
