package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class NewCaseSixteenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_sixteen);

        setupToolbar();
        setupButtons();
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBackHeader);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void setupButtons() {
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        
        findViewById(R.id.btnNext).setOnClickListener(v -> {
            Intent intent = new Intent(NewCaseSixteenActivity.this, NewCaseSeventeenActivity.class);
            startActivity(intent);
        });
    }
}
