package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class NewCaseNineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_nine);

        setupToolbar();

        View btnClose = findViewById(R.id.btnClose);
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> onBackPressed());
        }

        View btnUploadAnalyze = findViewById(R.id.btnUploadAnalyze);
        if (btnUploadAnalyze != null) {
            btnUploadAnalyze.setOnClickListener(v -> {
                // Navigate to Medical Imaging Viewer (Step 10)
                String fileUri = getIntent().getStringExtra("FILE_URI");
                Intent intent = new Intent(NewCaseNineActivity.this, NewCaseTenActivity.class);
                if (fileUri != null) {
                    intent.putExtra("FILE_URI", fileUri);
                }
                startActivity(intent);
            });
        }
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> onBackPressed());
        }
    }
}
