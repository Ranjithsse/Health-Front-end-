package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class NewCaseThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_three);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnBackFooter = findViewById(R.id.btnBackFooter);
        MaterialButton btnNext = findViewById(R.id.btnNext);

        CheckBox cbDiabetes = findViewById(R.id.cbDiabetes);
        CheckBox cbHypertension = findViewById(R.id.cbHypertension);
        CheckBox cbCardiovascular = findViewById(R.id.cbCardiovascular);
        CheckBox cbKidney = findViewById(R.id.cbKidney);
        CheckBox cbCancer = findViewById(R.id.cbCancer);
        CheckBox cbAutoimmune = findViewById(R.id.cbAutoimmune);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnBackFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaseData data = CaseData.getInstance();
                data.medicalConditions.clear();

                if (cbDiabetes != null && cbDiabetes.isChecked())
                    data.medicalConditions.add("Type 2 Diabetes");
                if (cbHypertension != null && cbHypertension.isChecked())
                    data.medicalConditions.add("Hypertension");
                if (cbCardiovascular != null && cbCardiovascular.isChecked())
                    data.medicalConditions.add("Cardiovascular Disease");
                if (cbKidney != null && cbKidney.isChecked())
                    data.medicalConditions.add("Chronic Kidney Disease");
                if (cbCancer != null && cbCancer.isChecked())
                    data.medicalConditions.add("Family History of Cancer");
                if (cbAutoimmune != null && cbAutoimmune.isChecked())
                    data.medicalConditions.add("Autoimmune Disorder");

                startActivity(new Intent(NewCaseThreeActivity.this, NewCaseFourActivity.class));
            }
        });
    }
}
