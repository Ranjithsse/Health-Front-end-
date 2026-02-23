package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class OneYearPredictionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_year_prediction);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnViewThreeYear).setOnClickListener(v -> {
            // Navigate to the 3-Year Prediction page
            Intent intent = new Intent(OneYearPredictionActivity.this, ThreeYearPredictionActivity.class);
            startActivity(intent);
        });
    }
}