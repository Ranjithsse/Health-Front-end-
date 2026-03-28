package com.example.healthpredict;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class NewCaseNineteenActivity extends AppCompatActivity {

    private String selectedTherapy = ""; // Initially nothing selected
    private MaterialCardView cardYes, cardNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_nineteen);

        ImageView btnBackHeader = findViewById(R.id.btnBackHeader);
        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);
        cardYes = findViewById(R.id.cardYes);
        cardNo = findViewById(R.id.cardNo);

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

        cardYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTherapy = "Yes";
                updateUI();
            }
        });

        cardNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTherapy = "No";
                updateUI();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTherapy.isEmpty()) {
                    android.widget.Toast.makeText(NewCaseNineteenActivity.this, "Please select whether Adjuvant Therapy is required", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
                CaseData.getInstance().adjuvantTherapyRequired = selectedTherapy.equals("Yes");
                startActivity(new Intent(NewCaseNineteenActivity.this, NewCaseTwentyActivity.class));
            }
        });

        updateUI();
    }

    private void updateUI() {
        if (selectedTherapy.equals("Yes")) {
            setSelected(cardYes);
            setUnselected(cardNo);
        } else if (selectedTherapy.equals("No")) {
            setSelected(cardNo);
            setUnselected(cardYes);
        } else {
            setUnselected(cardYes);
            setUnselected(cardNo);
        }
    }

    private void setSelected(MaterialCardView card) {
        card.setCardBackgroundColor(Color.parseColor("#F0F7FF"));
        card.setStrokeColor(Color.parseColor("#2563EB"));
        card.setStrokeWidth(4);
    }

    private void setUnselected(MaterialCardView card) {
        card.setCardBackgroundColor(Color.WHITE);
        card.setStrokeColor(Color.parseColor("#E2E8F0"));
        card.setStrokeWidth(2);
    }
}
