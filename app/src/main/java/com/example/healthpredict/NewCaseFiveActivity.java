package com.example.healthpredict;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.List;

public class NewCaseFiveActivity extends AppCompatActivity {

    private String selectedSystem = "Cardiovascular";
    private TextView tvSelectedSystem;
    private List<MaterialCardView> systemCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_five);

        tvSelectedSystem = findViewById(R.id.tvSelectedSystem);
        setupToolbar();
        setupSystemSelection();

        View btnBackFooter = findViewById(R.id.btnBackFooter);
        if (btnBackFooter != null) {
            btnBackFooter.setOnClickListener(v -> onBackPressed());
        }

        View btnNext = findViewById(R.id.btnNext);
        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                Intent intent = new Intent(NewCaseFiveActivity.this, NewCaseSixActivity.class);
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
                    // Extract text from the card's child LinearLayout's TextView
                    View layout = card.getChildAt(0);
                    if (layout instanceof android.widget.LinearLayout) {
                        View textView = ((android.widget.LinearLayout) layout).getChildAt(1);
                        if (textView instanceof TextView) {
                            selectedSystem = ((TextView) textView).getText().toString();
                            tvSelectedSystem.setText(selectedSystem);
                            updateSelectionUI(card);
                        }
                    }
                });
            }
        }
    }

    private void updateSelectionUI(MaterialCardView selected) {
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
