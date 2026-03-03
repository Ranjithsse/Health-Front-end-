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

    private boolean isAdjuvantTherapyRequired = false;
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
                isAdjuvantTherapyRequired = true;
                updateUI();
            }
        });

        cardNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAdjuvantTherapyRequired = false;
                updateUI();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaseData.getInstance().adjuvantTherapyRequired = isAdjuvantTherapyRequired;
                startActivity(new Intent(NewCaseNineteenActivity.this, NewCaseTwentyActivity.class));
            }
        });

        updateUI();
    }

    private void updateUI() {
        if (isAdjuvantTherapyRequired) {
            setSelected(cardYes);
            setUnselected(cardNo);
        } else {
            setSelected(cardNo);
            setUnselected(cardYes);
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
