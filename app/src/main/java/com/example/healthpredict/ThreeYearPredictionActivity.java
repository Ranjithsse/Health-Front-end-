package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class ThreeYearPredictionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_year_prediction);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnBackFooter = findViewById(R.id.btnBackFooter);
        MaterialButton btnViewFiveYear = findViewById(R.id.btnViewFiveYear);

        btnBack.setOnClickListener(v -> finish());
        btnBackFooter.setOnClickListener(v -> finish());

        btnViewFiveYear.setOnClickListener(v -> {
            startActivity(new Intent(ThreeYearPredictionActivity.this, FiveYearPredictionActivity.class));
        });
    }
}
