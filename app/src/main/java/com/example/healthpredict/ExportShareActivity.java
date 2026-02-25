package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ExportShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_share);

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        findViewById(R.id.cardDownloadPdf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to DownloadReportActivity
                Intent intent = new Intent(ExportShareActivity.this, DownloadReportActivity.class);
                // Passing some default values or could be passed from FinalReportActivity to here
                intent.putExtra("PATIENT_NAME", "Robert Wilson");
                intent.putExtra("PATIENT_ID", "1024");
                startActivity(intent);
            }
        });
    }
}