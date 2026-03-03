package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class NewCaseFourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_four);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnBackFooter = findViewById(R.id.btnBackFooter);
        MaterialButton btnNext = findViewById(R.id.btnNext);
        View btnPhysicalActivity = findViewById(R.id.btnPhysicalActivity);
        TextView tvPhysicalActivity = findViewById(R.id.tvPhysicalActivity);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnBackFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewCaseFourActivity.this, NewCaseFiveActivity.class));
            }
        });

        if (btnPhysicalActivity != null) {
            btnPhysicalActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPhysicalActivityDialog(tvPhysicalActivity);
                }
            });
        }
    }

    private void showPhysicalActivityDialog(TextView tvPhysicalActivity) {
        final String[] options = {"Active", "Moderate", "Sedentary"};
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Physical Activity Level");
        builder.setItems(options, (dialog, which) -> {
            if (tvPhysicalActivity != null) {
                tvPhysicalActivity.setText(options[which]);
            }
        });
        builder.show();
    }
}
