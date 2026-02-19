package com.example.healthpredict;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class LegalPrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_privacy);

        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> onBackPressed());
        }

        View btnBackFooter = findViewById(R.id.btnBackFooter);
        if (btnBackFooter != null) {
            btnBackFooter.setOnClickListener(v -> finish());
        }
    }
}
