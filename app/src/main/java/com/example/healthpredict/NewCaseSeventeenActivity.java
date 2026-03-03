package com.example.healthpredict;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class NewCaseSeventeenActivity extends AppCompatActivity {

    private String selectedIntervention = "Non-Invasive";
    private MaterialCardView cardNonInvasive, cardSurgical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_seventeen);

        ImageView btnBackHeader = findViewById(R.id.btnBackHeader);
        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);
        cardNonInvasive = findViewById(R.id.cardNonInvasive);
        cardSurgical = findViewById(R.id.cardSurgical);

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

        cardNonInvasive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedIntervention = "Non-Invasive";
                updateUI();
            }
        });

        cardSurgical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedIntervention = "Surgical Intervention";
                updateUI();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaseData.getInstance().interventionType = selectedIntervention;
                startActivity(new Intent(NewCaseSeventeenActivity.this, NewCaseEighteenActivity.class));
            }
        });
        
        updateUI();
    }

    private void updateUI() {
        if (selectedIntervention.equals("Non-Invasive")) {
            setSelected(cardNonInvasive);
            setUnselected(cardSurgical);
        } else {
            setSelected(cardSurgical);
            setUnselected(cardNonInvasive);
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
