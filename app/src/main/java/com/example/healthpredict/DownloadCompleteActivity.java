package com.example.healthpredict;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

import com.example.healthpredict.network.ApiService;
import com.example.healthpredict.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_complete);
        setupBottomNavigation();

        ImageView ivBack = findViewById(R.id.ivBack);
        MaterialButton btnViewReport = findViewById(R.id.btnViewReport);
        MaterialButton btnBackHome = findViewById(R.id.btnBackHome);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String pdfUriString = getIntent().getStringExtra("PDF_URI");

        btnViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pdfUriString != null) {
                    try {
                        Uri pdfUri = Uri.parse(pdfUriString);
                        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                        viewIntent.setDataAndType(pdfUri, "application/pdf");
                        viewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(viewIntent);
                    } catch (Exception e) {
                        Toast.makeText(DownloadCompleteActivity.this, "No PDF viewer found on device", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DownloadCompleteActivity.this, "Report file not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ensure the completed report shows up in History properly
                CaseData data = CaseData.getInstance();
                if (data.patientName != null && !data.patientName.isEmpty()) {
                    data.status = "Completed";
                    HistoryManager.getInstance().init(DownloadCompleteActivity.this);
                    HistoryManager.getInstance().addCase(DownloadCompleteActivity.this, data);
                }

                if (data.id > 0) {
                    java.util.Map<String, Object> updates = new java.util.HashMap<>();
                    try {
                        com.google.gson.Gson gson = new com.google.gson.Gson();
                        String json = gson.toJson(data);
                        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<java.util.Map<String, Object>>(){}.getType();
                        updates = gson.fromJson(json, type);
                    } catch (Exception e) {
                        updates.put("status", "Completed");
                    }

                    RetrofitClient.getApiService(DownloadCompleteActivity.this).updateCase(data.id, updates).enqueue(new Callback<CaseData>() {
                        @Override
                        public void onResponse(Call<CaseData> call, Response<CaseData> response) {
                            navigateHome();
                        }

                        @Override
                        public void onFailure(Call<CaseData> call, Throwable t) {
                            navigateHome();
                        }
                    });
                } else {
                    navigateHome();
                }
            }
        });
    }

    private void navigateHome() {
        Intent intent = new Intent(DownloadCompleteActivity.this, DoctorHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void setupBottomNavigation() {
        android.view.View navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, DoctorHomeActivity.class);
                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP | android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }

        android.view.View navCases = findViewById(R.id.navCases);
        if (navCases != null) {
            navCases.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, DoctorCasesActivity.class);
                startActivity(intent);
                finish();
            });
        }

        android.view.View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, ReportsActivity.class);
                startActivity(intent);
                finish();
            });
        }

        android.view.View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, DoctorProfileActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }

}
