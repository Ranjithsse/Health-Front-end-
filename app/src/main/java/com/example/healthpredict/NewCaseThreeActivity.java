package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class NewCaseThreeActivity extends AppCompatActivity {

    private CaseData caseData;
    private CheckBox cbDiabetes, cbHypertension, cbCardiovascular, cbKidney, cbCancer, cbAutoimmune;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_three);

        caseData = CaseData.getInstance();

        cbDiabetes = findViewById(R.id.cbDiabetes);
        cbHypertension = findViewById(R.id.cbHypertension);
        cbCardiovascular = findViewById(R.id.cbCardiovascular);
        cbKidney = findViewById(R.id.cbKidney);
        cbCancer = findViewById(R.id.cbCancer);
        cbAutoimmune = findViewById(R.id.cbAutoimmune);

        setupToolbar();

        View btnBackFooter = findViewById(R.id.btnBackFooter);
        if (btnBackFooter != null) {
            btnBackFooter.setOnClickListener(v -> onBackPressed());
        }

        View btnNext = findViewById(R.id.btnNext);
        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                saveData();
                Intent intent = new Intent(NewCaseThreeActivity.this, NewCaseFourActivity.class);
                startActivity(intent);
            });
        }
    }

    private void saveData() {
        caseData.medicalConditions.clear();
        if (cbDiabetes.isChecked()) caseData.medicalConditions.add("Diabetes");
        if (cbHypertension.isChecked()) caseData.medicalConditions.add("Hypertension");
        if (cbCardiovascular.isChecked()) caseData.medicalConditions.add("Cardiovascular");
        if (cbKidney.isChecked()) caseData.medicalConditions.add("Kidney Disease");
        if (cbCancer.isChecked()) caseData.medicalConditions.add("Cancer History");
        if (cbAutoimmune.isChecked()) caseData.medicalConditions.add("Autoimmune");
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> onBackPressed());
        }
    }
}
