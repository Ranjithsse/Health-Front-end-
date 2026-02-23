package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ThreeYearPredictionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_year_prediction);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnViewFiveYear).setOnClickListener(v -> {
            Intent intent = new Intent(ThreeYearPredictionActivity.this, FiveYearPredictionActivity.class);
            startActivity(intent);
        });
    }
}
