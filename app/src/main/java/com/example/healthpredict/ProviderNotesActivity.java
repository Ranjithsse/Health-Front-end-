package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class ProviderNotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_notes);

        ImageView btnBack = findViewById(R.id.btnBack);
        EditText etProviderNotes = findViewById(R.id.etProviderNotes);
        MaterialButton btnGenerateReport = findViewById(R.id.btnGenerateReport);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnGenerateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save notes to singleton
                CaseData.getInstance().providerNotes = etProviderNotes.getText().toString().trim();
                
                // Add this completed case to history so it appears in "Recent Patients"
                HistoryManager.getInstance().addCase(CaseData.getInstance());
                
                // Navigate to FinalReportActivity
                Intent intent = new Intent(ProviderNotesActivity.this, FinalReportActivity.class);
                startActivity(intent);
            }
        });
    }
}
