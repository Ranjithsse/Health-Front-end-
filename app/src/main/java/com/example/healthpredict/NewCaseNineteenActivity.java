package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class NewCaseNineteenActivity extends AppCompatActivity {

    private MaterialCardView cardYes;
    private MaterialCardView cardNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_nineteen);

        cardYes = findViewById(R.id.cardYes);
        cardNo = findViewById(R.id.cardNo);

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
        if (cardYes != null) {
            cardYes.setOnClickListener(v -> selectCard(cardYes, cardNo));
        }
        if (cardNo != null) {
            cardNo.setOnClickListener(v -> selectCard(cardNo, cardYes));
        }
    }

    private void selectCard(MaterialCardView selected, MaterialCardView unselected) {
        updateCardStyles(selected, true);
        updateCardStyles(unselected, false);
    }

    private void updateCardStyles(MaterialCardView card, boolean isSelected) {
        if (card == null) return;

        if (isSelected) {
            card.setStrokeColor(android.graphics.Color.parseColor("#2563EB"));
            card.setStrokeWidth(4);
            card.setCardBackgroundColor(android.graphics.Color.parseColor("#F0F7FF"));
            
            View innerLayout = card.getChildAt(0);
            if (innerLayout instanceof LinearLayout) {
                LinearLayout layout = (LinearLayout) innerLayout;
                if (layout.getChildCount() >= 2) {
                    ((TextView) layout.getChildAt(0)).setTextColor(android.graphics.Color.parseColor("#1E3A8A"));
                    ((TextView) layout.getChildAt(1)).setTextColor(android.graphics.Color.parseColor("#3B82F6"));
                }
            }
        } else {
            card.setStrokeColor(android.graphics.Color.parseColor("#E2E8F0"));
            card.setStrokeWidth(2);
            card.setCardBackgroundColor(android.graphics.Color.parseColor("#FFFFFF"));
            
            View innerLayout = card.getChildAt(0);
            if (innerLayout instanceof LinearLayout) {
                LinearLayout layout = (LinearLayout) innerLayout;
                if (layout.getChildCount() >= 2) {
                    ((TextView) layout.getChildAt(0)).setTextColor(android.graphics.Color.parseColor("#1E293B"));
                    ((TextView) layout.getChildAt(1)).setTextColor(android.graphics.Color.parseColor("#94A3B8"));
                }
            }
        }
    }

    private void setupButtons() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        View btnNext = findViewById(R.id.btnNext);
        if (btnNext != null) {
            btnNext.setOnClickListener(v -> {
                Intent intent = new Intent(NewCaseNineteenActivity.this, NewCaseTwentyActivity.class);
                startActivity(intent);
            });
        }
    }
}
