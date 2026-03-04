package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.example.healthpredict.network.RetrofitClient;
import com.example.healthpredict.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.HashMap;
import java.util.Map;
import android.widget.Toast;

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
                String notes = etProviderNotes.getText().toString().trim();
                CaseData data = CaseData.getInstance();
                data.providerNotes = notes;

                // Save to server
                if (data.id != 0) {
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("providerNotes", notes);
                    updates.put("status", "Completed"); // Mark as completed when report generated

                    ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
                    apiService.updateCase(data.id, updates).enqueue(new Callback<CaseData>() {
                        @Override
                        public void onResponse(Call<CaseData> call, Response<CaseData> response) {
                            if (response.isSuccessful()) {
                                // Add to history locally as well
                                HistoryManager.getInstance().addCase(data);

                                // Navigate to FinalReportActivity
                                Intent intent = new Intent(ProviderNotesActivity.this, FinalReportActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(ProviderNotesActivity.this, "Failed to save notes: " + response.code(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CaseData> call, Throwable t) {
                            Toast.makeText(ProviderNotesActivity.this, "Network error: " + t.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Fallback for local testing if no ID
                    HistoryManager.getInstance().addCase(data);
                    Intent intent = new Intent(ProviderNotesActivity.this, FinalReportActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
