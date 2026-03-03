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

public class NewCaseEighteenActivity extends AppCompatActivity {

    private String selectedMonitoring = "Standard Monitoring";
    private MaterialCardView cardIntensive, cardStandard;
    private TextView tvIntensiveTitle, tvIntensiveSub, tvStandardTitle, tvStandardSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_eighteen);

        ImageView btnBackHeader = findViewById(R.id.btnBackHeader);
        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnNext = findViewById(R.id.btnNext);
        cardIntensive = findViewById(R.id.cardIntensive);
        cardStandard = findViewById(R.id.cardStandard);
        tvIntensiveTitle = findViewById(R.id.tvIntensiveTitle);
        tvIntensiveSub = findViewById(R.id.tvIntensiveSub);
        tvStandardTitle = findViewById(R.id.tvStandardTitle);
        tvStandardSub = findViewById(R.id.tvStandardSub);

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

        cardIntensive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMonitoring = "Intensive Monitoring";
                updateUI();
            }
        });

        cardStandard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMonitoring = "Standard Monitoring";
                updateUI();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaseData.getInstance().monitoringLevel = selectedMonitoring;
                startActivity(new Intent(NewCaseEighteenActivity.this, NewCaseNineteenActivity.class));
            }
        });

        updateUI();
    }

    private void updateUI() {
        if (selectedMonitoring.equals("Intensive Monitoring")) {
            setSelected(cardIntensive, tvIntensiveTitle, tvIntensiveSub);
            setUnselected(cardStandard, tvStandardTitle, tvStandardSub);
        } else {
            setSelected(cardStandard, tvStandardTitle, tvStandardSub);
            setUnselected(cardIntensive, tvIntensiveTitle, tvIntensiveSub);
        }
    }

    private void setSelected(MaterialCardView card, TextView title, TextView sub) {
        card.setCardBackgroundColor(Color.parseColor("#F0F7FF"));
        card.setStrokeColor(Color.parseColor("#2563EB"));
        card.setStrokeWidth(4);
        title.setTextColor(Color.parseColor("#1E3A8A"));
        sub.setTextColor(Color.parseColor("#3B82F6"));
    }

    private void setUnselected(MaterialCardView card, TextView title, TextView sub) {
        card.setCardBackgroundColor(Color.WHITE);
        card.setStrokeColor(Color.parseColor("#E2E8F0"));
        card.setStrokeWidth(2);
        title.setTextColor(Color.parseColor("#1E293B"));
        sub.setTextColor(Color.parseColor("#94A3B8"));
    }
}
