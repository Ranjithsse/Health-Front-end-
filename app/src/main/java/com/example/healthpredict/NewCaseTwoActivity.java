package com.example.healthpredict;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class NewCaseTwoActivity extends AppCompatActivity {

    private String selectedGender = "";
    private String selectedBloodGroup = "";
    private List<MaterialCardView> bloodGroupCards = new ArrayList<>();
    private CaseData caseData;
    private TextView tvSmokingStatus;
    private EditText etAge;
    private EditText etBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_two);

        caseData = CaseData.getInstance();

        etAge = findViewById(R.id.etAge);
        etBMI = findViewById(R.id.etBMI);
        setupToolbar();
        setupGenderSelection();
        setupBloodGroupSelection();
        setupSmokingStatusSelection();

        View btnNext = findViewById(R.id.btnNext);
        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                String age = etAge != null ? etAge.getText().toString().trim() : "";
                String bmi = etBMI != null ? etBMI.getText().toString().trim() : "";
                String smokingStatus = tvSmokingStatus != null ? tvSmokingStatus.getText().toString().trim() : "";

                if (age.isEmpty()) {
                    android.widget.Toast.makeText(this, "Please enter patient age", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedGender.isEmpty()) {
                    android.widget.Toast.makeText(this, "Please select patient gender", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedBloodGroup.isEmpty()) {
                    android.widget.Toast.makeText(this, "Please select blood group", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
                if (smokingStatus.isEmpty() || smokingStatus.equalsIgnoreCase("Select Smoking Status")) {
                    android.widget.Toast.makeText(this, "Please select smoking status", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bmi.isEmpty()) {
                    android.widget.Toast.makeText(this, "Please enter BMI value", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save data to singleton
                caseData.gender = selectedGender;
                caseData.bloodGroup = selectedBloodGroup;
                caseData.age = age;
                caseData.bmi = bmi;
                caseData.smokingStatus = smokingStatus;

                Intent intent = new Intent(NewCaseTwoActivity.this, NewCaseThreeActivity.class);
                startActivity(intent);
            });
        }

        View btnBackFooter = findViewById(R.id.btnBackFooter);
        if (btnBackFooter != null) {
            btnBackFooter.setOnClickListener(v -> onBackPressed());
        }
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> onBackPressed());
        }
    }

    private void setupGenderSelection() {
        MaterialCardView cardMale = findViewById(R.id.cardMale);
        MaterialCardView cardFemale = findViewById(R.id.cardFemale);

        if (cardMale != null) {
            cardMale.setOnClickListener(v -> {
                selectedGender = "Male";
                updateGenderUI(cardMale, cardFemale);
            });
        }

        if (cardFemale != null) {
            cardFemale.setOnClickListener(v -> {
                selectedGender = "Female";
                updateGenderUI(cardFemale, cardMale);
            });
        }
    }

    private void updateGenderUI(MaterialCardView selected, MaterialCardView unselected) {
        selected.setStrokeColor(Color.parseColor("#2563EB"));
        selected.setCardBackgroundColor(Color.parseColor("#EFF6FF"));
        
        unselected.setStrokeColor(Color.parseColor("#F1F5F9"));
        unselected.setCardBackgroundColor(Color.WHITE);
    }

    private void setupBloodGroupSelection() {
        int[] ids = {
            R.id.cardAPlus, R.id.cardAMinus,
            R.id.cardBPlus, R.id.cardBMinus,
            R.id.cardABPlus, R.id.cardABMinus,
            R.id.cardOPlus, R.id.cardOMinus
        };

        for (int id : ids) {
            MaterialCardView card = findViewById(id);
            if (card != null) {
                bloodGroupCards.add(card);
                card.setOnClickListener(v -> {
                    selectedBloodGroup = ((TextView) card.getChildAt(0)).getText().toString();
                    updateBloodGroupUI(card);
                });
            }
        }
    }

    private void updateBloodGroupUI(MaterialCardView selected) {
        for (MaterialCardView card : bloodGroupCards) {
            if (card == selected) {
                card.setStrokeColor(Color.parseColor("#2563EB"));
                card.setCardBackgroundColor(Color.parseColor("#EFF6FF"));
            } else {
                card.setStrokeColor(Color.parseColor("#F1F5F9"));
                card.setCardBackgroundColor(Color.WHITE);
            }
        }
    }

    private void setupSmokingStatusSelection() {
        View btnSmokingStatus = findViewById(R.id.btnSmokingStatus);
        tvSmokingStatus = findViewById(R.id.tvSmokingStatus);
        
        if (btnSmokingStatus != null) {
            btnSmokingStatus.setOnClickListener(v -> {
                String[] options = {"Non-smoker", "Light smoker (<10/day)", "Heavy smoker (>10/day)", "Former smoker"};
                new MaterialAlertDialogBuilder(this)
                    .setTitle("Select Smoking Status")
                    .setItems(options, (dialog, which) -> {
                        tvSmokingStatus.setText(options[which]);
                    })
                    .show();
            });
        }
    }
}
