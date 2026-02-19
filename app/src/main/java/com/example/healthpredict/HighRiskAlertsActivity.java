package com.example.healthpredict;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HighRiskAlertsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_risk_alerts);

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupAlerts();
    }

    private void setupAlerts() {
        // Robert Wilson
        View alert1 = findViewById(R.id.alert1);
        if (alert1 != null) {
            ((TextView) alert1.findViewById(R.id.tvPatientName)).setText("Robert Wilson");
            ((TextView) alert1.findViewById(R.id.tvPatientId)).setText("P-1020");
            ((TextView) alert1.findViewById(R.id.tvSeverity)).setText("High Risk");
            ((TextView) alert1.findViewById(R.id.tvAlertDetail)).setText("Elevated HbA1c (9.2%)");
            ((TextView) alert1.findViewById(R.id.tvAction)).setText("Review Diabetes Management");
        }

        // Emily Davis
        View alert2 = findViewById(R.id.alert2);
        if (alert2 != null) {
            ((TextView) alert2.findViewById(R.id.tvPatientName)).setText("Emily Davis");
            ((TextView) alert2.findViewById(R.id.tvPatientId)).setText("P-1021");
            ((TextView) alert2.findViewById(R.id.tvSeverity)).setText("Critical");
            ((TextView) alert2.findViewById(R.id.tvSeverity)).setTextColor(0xFFFFFFFF);
            (alert2.findViewById(R.id.cardSeverity)).setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFDC2626));
            ((TextView) alert2.findViewById(R.id.tvAlertDetail)).setText("Abnormal ECG Pattern");
            ((TextView) alert2.findViewById(R.id.tvAction)).setText("Urgent Cardiac Evaluation");
        }

        // James Miller
        View alert3 = findViewById(R.id.alert3);
        if (alert3 != null) {
            ((TextView) alert3.findViewById(R.id.tvPatientName)).setText("James Miller");
            ((TextView) alert3.findViewById(R.id.tvPatientId)).setText("P-1019");
            ((TextView) alert3.findViewById(R.id.tvSeverity)).setText("High Risk");
            ((TextView) alert3.findViewById(R.id.tvAlertDetail)).setText("Kidney Function Decline (eGFR 38)");
            ((TextView) alert3.findViewById(R.id.tvAction)).setText("Nephrology Referral");
        }
    }
}