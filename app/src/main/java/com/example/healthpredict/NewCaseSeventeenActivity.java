package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class NewCaseSeventeenActivity extends AppCompatActivity {

    private MaterialCardView cardNonInvasive;
    private MaterialCardView cardSurgical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_seventeen);

        cardNonInvasive = findViewById(R.id.cardNonInvasive);
        cardSurgical = findViewById(R.id.cardSurgical);

        setupToolbar();
        setupSelectionLogic();
        setupButtons();
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBackHeader);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void setupSelectionLogic() {
        cardNonInvasive.setOnClickListener(v -> selectCard(cardNonInvasive, cardSurgical));
        cardSurgical.setOnClickListener(v -> selectCard(cardSurgical, cardNonInvasive));
    }

    private void selectCard(MaterialCardView selected, MaterialCardView unselected) {
        updateCardStyles(selected, true);
        updateCardStyles(unselected, false);
    }

    private void updateCardStyles(MaterialCardView card, boolean isSelected) {
        if (isSelected) {
            card.setStrokeColor(android.graphics.Color.parseColor("#2563EB"));
            card.setStrokeWidth(4);
            card.setCardBackgroundColor(android.graphics.Color.parseColor("#F0F7FF"));
        } else {
            card.setStrokeColor(android.graphics.Color.parseColor("#E2E8F0"));
            card.setStrokeWidth(2);
            card.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
        }
    }

    private void setupButtons() {
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnNext).setOnClickListener(v -> {
            Intent intent = new Intent(NewCaseSeventeenActivity.this, NewCaseEighteenActivity.class);
            startActivity(intent);
        });
    }
}
