package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class NewCaseThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_three);

        setupToolbar();

        View btnBackFooter = findViewById(R.id.btnBackFooter);
        if (btnBackFooter != null) {
            btnBackFooter.setOnClickListener(v -> onBackPressed());
        }

        View btnNext = findViewById(R.id.btnNext);
        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                Intent intent = new Intent(NewCaseThreeActivity.this, NewCaseFourActivity.class);
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
