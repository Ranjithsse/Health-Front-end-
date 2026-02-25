package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class NewCaseFourActivity extends AppCompatActivity {

    private CaseData caseData;
    private TextView tvPhysicalActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_four);

        caseData = CaseData.getInstance();
        tvPhysicalActivity = findViewById(R.id.tvPhysicalActivity);

        setupToolbar();
        setupPhysicalActivitySelection();

        View btnBackFooter = findViewById(R.id.btnBackFooter);
        if (btnBackFooter != null) {
            btnBackFooter.setOnClickListener(v -> onBackPressed());
        }

        View btnNext = findViewById(R.id.btnNext);
        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                if (tvPhysicalActivity != null) {
                    caseData.physicalActivity = tvPhysicalActivity.getText().toString();
                }
                Intent intent = new Intent(NewCaseFourActivity.this, NewCaseFiveActivity.class);
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

    private void setupPhysicalActivitySelection() {
        View btnPhysicalActivity = findViewById(R.id.btnPhysicalActivity);
        
        if (btnPhysicalActivity != null) {
            btnPhysicalActivity.setOnClickListener(v -> {
                String[] options = {"Active", "Moderate", "Sedentary"};
                new MaterialAlertDialogBuilder(this)
                    .setTitle("Select Physical Activity Level")
                    .setItems(options, (dialog, which) -> {
                        if (tvPhysicalActivity != null) {
                            tvPhysicalActivity.setText(options[which]);
                        }
                    })
                    .show();
            });
        }
    }
}
