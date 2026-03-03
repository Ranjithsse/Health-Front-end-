package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class NewCaseTwentyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_twenty);

        ImageView btnBackHeader = findViewById(R.id.btnBackHeader);
        MaterialButton btnRunPrediction = findViewById(R.id.btnRunPrediction);
        MaterialButton btnBackEdit = findViewById(R.id.btnBackEdit);

        btnBackHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRunPrediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewCaseTwentyActivity.this, AiPredictionActivity.class));
            }
        });

        btnBackEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
