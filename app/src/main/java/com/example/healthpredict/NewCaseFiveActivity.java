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
import java.util.ArrayList;
import java.util.List;

public class NewCaseFiveActivity extends AppCompatActivity {

    private String selectedSystem = "";
    private List<MaterialCardView> systemCards = new ArrayList<>();
    private TextView tvSelectedSystem;
    private CaseData caseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_five);

        caseData = CaseData.getInstance();
        tvSelectedSystem = findViewById(R.id.tvSelectedSystem);

        setupToolbar();
        setupSystemSelection();

        View btnNext = findViewById(R.id.btnNext);
        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                if (selectedSystem.isEmpty()) {
                    android.widget.Toast.makeText(this, "Please select a body system for analysis", android.widget.Toast.LENGTH_SHORT).show();
                    return;
                }
                caseData.primarySystem = selectedSystem;
                startActivity(new Intent(NewCaseFiveActivity.this, NewCaseSixActivity.class));
            });
        }

        View btnBackFooter = findViewById(R.id.btnBackFooter);
        if (btnBackFooter != null) {
            btnBackFooter.setOnClickListener(v -> finish());
        }
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void setupSystemSelection() {
        int[] ids = {
            R.id.cardCardiovascular, R.id.cardRespiratory,
            R.id.cardEndocrine, R.id.cardRenal,
            R.id.cardNeurological, R.id.cardMusculoskeletal,
            R.id.cardGastrointestinal, R.id.cardOncology
        };

        for (int id : ids) {
            MaterialCardView card = findViewById(id);
            if (card != null) {
                systemCards.add(card);
                card.setOnClickListener(v -> {
                    // Extract text from the second child (TextView) of the LinearLayout inside the card
                    View layout = card.getChildAt(0);
                    if (layout instanceof android.widget.LinearLayout) {
                        View text = ((android.widget.LinearLayout) layout).getChildAt(1);
                        if (text instanceof TextView) {
                            selectedSystem = ((TextView) text).getText().toString();
                            tvSelectedSystem.setText(selectedSystem);
                            updateUI(card);
                        }
                    }
                });
            }
        }
    }

    private void updateUI(MaterialCardView selected) {
        for (MaterialCardView card : systemCards) {
            if (card == selected) {
                card.setStrokeColor(Color.parseColor("#2563EB"));
                card.setCardBackgroundColor(Color.parseColor("#EFF6FF"));
            } else {
                card.setStrokeColor(Color.parseColor("#F1F5F9"));
                card.setCardBackgroundColor(Color.WHITE);
            }
        }
    }
}
