package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

import android.widget.TextView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import java.util.Locale;

public class NewCaseTwelveActivity extends AppCompatActivity {

    private TextView tvCellularityValue, tvVascularityValue;
    private TextView tvTypeACellsPct, tvTypeBCellsPct, tvTypeCCellsPct;
    private LinearProgressIndicator pbTypeACells, pbTypeBCells, pbTypeCCells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_twelve);

        initViews();
        updateUI();

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnContinueHeatmap = findViewById(R.id.btnContinueHeatmap);
        MaterialButton btnBackOverview = findViewById(R.id.btnBackOverview);

        btnBack.setOnClickListener(v -> finish());
        btnContinueHeatmap.setOnClickListener(
                v -> startActivity(new Intent(NewCaseTwelveActivity.this, NewCaseFourteenActivity.class)));
        btnBackOverview.setOnClickListener(v -> finish());
    }

    private void initViews() {
        tvCellularityValue = findViewById(R.id.tvCellularityValue);
        tvVascularityValue = findViewById(R.id.tvVascularityValue);
        tvTypeACellsPct = findViewById(R.id.tvTypeACellsPct);
        tvTypeBCellsPct = findViewById(R.id.tvTypeBCellsPct);
        tvTypeCCellsPct = findViewById(R.id.tvTypeCCellsPct);
        pbTypeACells = findViewById(R.id.pbTypeACells);
        pbTypeBCells = findViewById(R.id.pbTypeBCells);
        pbTypeCCells = findViewById(R.id.pbTypeCCells);
    }

    private void updateUI() {
        CaseData data = CaseData.getInstance();

        if (tvCellularityValue != null) tvCellularityValue.setText(data.cellularity);
        if (tvVascularityValue != null) tvVascularityValue.setText(data.vascularityPerfusion);

        if (tvTypeACellsPct != null) tvTypeACellsPct.setText(String.format(Locale.US, "%.1f%%", data.typeACellsPct));
        if (tvTypeBCellsPct != null) tvTypeBCellsPct.setText(String.format(Locale.US, "%.1f%%", data.typeBCellsPct));
        if (tvTypeCCellsPct != null) tvTypeCCellsPct.setText(String.format(Locale.US, "%.1f%%", data.typeCCellsPct));

        if (pbTypeACells != null) pbTypeACells.setProgress((int) data.typeACellsPct);
        if (pbTypeBCells != null) pbTypeBCells.setProgress((int) data.typeBCellsPct);
        if (pbTypeCCells != null) pbTypeCCells.setProgress((int) data.typeCCellsPct);
    }
}
