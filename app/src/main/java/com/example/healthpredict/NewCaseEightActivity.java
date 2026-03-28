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
    private View uploadArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_eight);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnUploadAnalyze = findViewById(R.id.btnUploadAnalyze);
        uploadArea = findViewById(R.id.uploadArea);

        // Initialize file picker
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if (uri != null) {
                        handleFileSelection(uri);
                    }
                });

        btnBack.setOnClickListener(v -> finish());

        if (uploadArea != null) {
            uploadArea.setOnClickListener(v -> {
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
                // If button is clicked here, go to Step 9 (Confirmation)
                startActivity(new Intent(NewCaseEightActivity.this, NewCaseNineActivity.class));
            } else {
                Toast.makeText(this, "Please select a file first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleFileSelection(Uri uri) {
        CaseData.getInstance().fileUri = uri.toString();

        // Immediately navigate to Step 9 (Confirmation) after selection
        startActivity(new Intent(NewCaseEightActivity.this, NewCaseNineActivity.class));
    }
}
