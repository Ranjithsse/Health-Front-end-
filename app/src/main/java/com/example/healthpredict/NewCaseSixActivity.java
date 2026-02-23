package com.example.healthpredict;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.List;

public class NewCaseSixActivity extends AppCompatActivity {

    private String selectedBP = "";
    private List<MaterialCardView> bpCards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_six);

        setupToolbar();
        setupBPSelection();
        setupGlucoseSelection();

        View btnBackFooter = findViewById(R.id.btnBackFooter);
        if (btnBackFooter != null) {
            btnBackFooter.setOnClickListener(v -> onBackPressed());
        }

        View btnReview = findViewById(R.id.btnReview);
        if (btnReview != null) {
            btnReview.setOnClickListener(v -> {
                Intent intent = new Intent(NewCaseSixActivity.this, NewCaseSevenActivity.class);
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

    private void setupBPSelection() {
        int[] ids = {
            R.id.cardBPNormal, R.id.cardBPElevated,
            R.id.cardBPStage1, R.id.cardBPStage2
        };

        for (int id : ids) {
            MaterialCardView card = findViewById(id);
            if (card != null) {
                bpCards.add(card);
                card.setOnClickListener(v -> {
                    selectedBP = ((TextView) card.getChildAt(0)).getText().toString();
                    updateBPUI(card);
                });
            }
        }
    }

    private void updateBPUI(MaterialCardView selected) {
        for (MaterialCardView card : bpCards) {
            if (card == selected) {
                card.setStrokeColor(Color.parseColor("#2563EB"));
                card.setCardBackgroundColor(Color.parseColor("#EFF6FF"));
            } else {
                card.setStrokeColor(Color.parseColor("#F1F5F9"));
                card.setCardBackgroundColor(Color.WHITE);
            }
        }
    }

    private void setupGlucoseSelection() {
        View btnGlucose = findViewById(R.id.btnGlucose);
        TextView tvGlucose = findViewById(R.id.tvGlucose);
        
        if (btnGlucose != null) {
            btnGlucose.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(this, btnGlucose);
                popup.getMenu().add("Normal (<100)");
                popup.getMenu().add("Pre-diabetic (100-125)");
                popup.getMenu().add("Diabetic (126+)");

                popup.setOnMenuItemClickListener(item -> {
                    tvGlucose.setText(item.getTitle());
                    return true;
                });
                popup.show();
            });
        }
    }
}
