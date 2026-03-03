package com.example.healthpredict;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class NewCaseEightActivity extends AppCompatActivity {

    private ActivityResultLauncher<String[]> filePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_eight);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnUploadAnalyze = findViewById(R.id.btnUploadAnalyze);
        View uploadArea = findViewById(R.id.uploadArea);

        // Initialize file picker
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if (uri != null) {
                        handleFileSelection(uri);
                    }
                }
        );

        btnBack.setOnClickListener(v -> finish());

        if (uploadArea != null) {
            uploadArea.setOnClickListener(v -> {
                // Open file manager for all required formats
                String[] mimeTypes = {
                        "application/dicom",
                        "image/*",
                        "application/pdf",
                        "application/zip",
                        "application/x-zip-compressed",
                        "application/octet-stream"
                };
                filePickerLauncher.launch(mimeTypes);
            });
        }

        btnUploadAnalyze.setOnClickListener(v -> {
            if (CaseData.getInstance().fileUri != null && !CaseData.getInstance().fileUri.isEmpty()) {
                startActivity(new Intent(NewCaseEightActivity.this, NewCaseNineActivity.class));
            } else {
                Toast.makeText(this, "Please select a file first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleFileSelection(Uri uri) {
        // Save the URI to CaseData
        CaseData.getInstance().fileUri = uri.toString();
        
        Toast.makeText(this, "File selected successfully!", Toast.LENGTH_SHORT).show();
        
        // After "uploading" (selecting), navigate to the next screen as requested
        startActivity(new Intent(NewCaseEightActivity.this, NewCaseNineActivity.class));
    }
}
