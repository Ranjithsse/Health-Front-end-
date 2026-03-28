package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class NewCaseSixteenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_sixteen);

        ImageView btnBackHeader = findViewById(R.id.btnBackHeader);
        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);
        EditText etDosage = findViewById(R.id.etDosage);
        EditText etDuration = findViewById(R.id.etDuration);

        btnBackHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dosage = etDosage.getText().toString().trim();
                String duration = etDuration.getText().toString().trim();

                if (dosage.isEmpty() || duration.isEmpty()) {
                    android.widget.Toast.makeText(NewCaseSixteenActivity.this, "Please enter both Dosage and Duration", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }

                CaseData data = CaseData.getInstance();
                data.dosage = dosage;
                data.duration = duration;
                startActivity(new Intent(NewCaseSixteenActivity.this, NewCaseSeventeenActivity.class));
            }
        });
    }
}
