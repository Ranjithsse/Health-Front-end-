package com.example.healthpredict;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class NewCaseEightActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> filePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_eight);

        setupToolbar();

        // Initialize File Picker Launcher
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        // Using a small delay or ensuring activity is in correct state before transition
                        navigateToNine(uri);
                    }
                }
        );

        View uploadArea = findViewById(R.id.uploadArea);
        if (uploadArea != null) {
            uploadArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open file picker for all types of files (*/*)
                    filePickerLauncher.launch("*/*");
                }
            });
        }

        View btnUploadAnalyze = findViewById(R.id.btnUploadAnalyze);
        if (btnUploadAnalyze != null) {
            btnUploadAnalyze.setOnClickListener(v -> {
                // This button is usually used after a file is selected
            });
        }
    }

    private void navigateToNine(Uri uri) {
        Intent intent = new Intent(this, NewCaseNineActivity.class);
        intent.putExtra("FILE_URI", uri.toString());
        startActivity(intent);
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}
