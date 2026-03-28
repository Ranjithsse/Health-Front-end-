package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import java.util.Locale;

public class NewCaseElevenActivity extends AppCompatActivity {

    private TextView tvTissueDensityValue, tvCalcificationValue;
    private TextView badgeTissueDensity, badgeCalcification;
    private TextView tvHealthyPct, tvFibrousPct, tvInflamedPct;
    private LinearProgressIndicator pbHealthy, pbFibrous, pbInflamed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_eleven);

        initViews();
        updateUI();

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnViewMicroStructure = findViewById(R.id.btnViewMicroStructure);

        btnBack.setOnClickListener(v -> finish());
        btnViewMicroStructure.setOnClickListener(
                v -> startActivity(new Intent(NewCaseElevenActivity.this, NewCaseTwelveActivity.class)));
    }

    private void initViews() {
        tvTissueDensityValue = findViewById(R.id.tvTissueDensityValue);
        tvCalcificationValue = findViewById(R.id.tvCalcificationValue);
        badgeTissueDensity = findViewById(R.id.badgeTissueDensity);
        badgeCalcification = findViewById(R.id.badgeCalcification);

        tvHealthyPct = findViewById(R.id.tvHealthyTissuePct);
        tvFibrousPct = findViewById(R.id.tvFibrousTissuePct);
        tvInflamedPct = findViewById(R.id.tvInflamedTissuePct);

        pbHealthy = findViewById(R.id.pbHealthyTissue);
        pbFibrous = findViewById(R.id.pbFibrousTissue);
        pbInflamed = findViewById(R.id.pbInflamedTissue);
    }

    private void updateUI() {
        CaseData data = CaseData.getInstance();

        if (tvTissueDensityValue != null)
            tvTissueDensityValue.setText(data.tissueDensity);
        if (tvCalcificationValue != null)
            tvCalcificationValue.setText(data.calcification);

        if (badgeTissueDensity != null) {
            badgeTissueDensity.setText(data.tissueDensity.equals("High") ? "Normal" : data.tissueDensity);
        }
        if (badgeCalcification != null) {
            badgeCalcification.setText(data.calcification.equals("Minimal") ? "Optimal" : "Standard");
        }

        if (tvHealthyPct != null)
            tvHealthyPct.setText(String.format(Locale.US, "%.1f%%", data.healthyTissuePct));
        if (tvFibrousPct != null)
            tvFibrousPct.setText(String.format(Locale.US, "%.1f%%", data.fibrousTissuePct));
        if (tvInflamedPct != null)
            tvInflamedPct.setText(String.format(Locale.US, "%.1f%%", data.inflamedTissuePct));

        if (pbHealthy != null)
            pbHealthy.setProgress((int) data.healthyTissuePct);
        if (pbFibrous != null)
            pbFibrous.setProgress((int) data.fibrousTissuePct);
        if (pbInflamed != null)
            pbInflamed.setProgress((int) data.inflamedTissuePct);
    }
}
