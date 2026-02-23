package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class NewCaseTwelveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_twelve);

        setupToolbar();
        setupButtons();
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void setupButtons() {
        View btnContinueHeatmap = findViewById(R.id.btnContinueHeatmap);
        if (btnContinueHeatmap != null) {
            btnContinueHeatmap.setOnClickListener(v -> {
                Intent intent = new Intent(NewCaseTwelveActivity.this, NewCaseThirteenActivity.class);
                startActivity(intent);
            });
        }

        View btnBackOverview = findViewById(R.id.btnBackOverview);
        if (btnBackOverview != null) {
            btnBackOverview.setOnClickListener(v -> {
                finish();
            });
        }
    }
}
