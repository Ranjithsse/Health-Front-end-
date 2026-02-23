package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class NewCaseElevenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_eleven);

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
        View btnViewMicroStructure = findViewById(R.id.btnViewMicroStructure);
        if (btnViewMicroStructure != null) {
            btnViewMicroStructure.setOnClickListener(v -> {
                Intent intent = new Intent(this, NewCaseTwelveActivity.class);
                startActivity(intent);
            });
        }
    }
}
