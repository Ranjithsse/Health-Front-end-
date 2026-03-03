package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class NewCaseNineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_nine);

        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView btnClose = findViewById(R.id.btnClose);
        MaterialButton btnUploadAnalyze = findViewById(R.id.btnUploadAnalyze);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (btnClose != null) {
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate back to NewCaseEightActivity
                    Intent intent = new Intent(NewCaseNineActivity.this, NewCaseEightActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        }

        btnUploadAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the file URI stored in CaseData to the viewer activity
                Intent intent = new Intent(NewCaseNineActivity.this, NewCaseTenActivity.class);
                intent.putExtra("FILE_URI", CaseData.getInstance().fileUri);
                startActivity(intent);
            }
        });
    }
}
