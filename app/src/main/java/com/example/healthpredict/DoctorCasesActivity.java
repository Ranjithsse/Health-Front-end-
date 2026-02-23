package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class DoctorCasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_cases);

        View navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DoctorCasesActivity.this, DoctorHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        }

        View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DoctorCasesActivity.this, ReportsActivity.class);
                    startActivity(intent);
                }
            });
        }

        setupCases();
    }

    private void setupCases() {
        // Case 1: Robert Wilson
        View case1 = findViewById(R.id.case1);
        if (case1 != null) {
            setupCaseItem(case1, "Robert Wilson", "P-1024", "Jan 24", "Completed", R.color.low_risk_bg, R.color.low_risk_text);
        }

        // Case 2: Sarah Johnson
        View case2 = findViewById(R.id.case2);
        if (case2 != null) {
            setupCaseItem(case2, "Sarah Johnson", "P-1023", "Jan 23", "Pending", R.color.pending_bg, R.color.pending_text);
        }

        // Case 3: Michael Brown
        View case3 = findViewById(R.id.case3);
        if (case3 != null) {
            setupCaseItem(case3, "Michael Brown", "P-1022", "Jan 22", "Completed", R.color.low_risk_bg, R.color.low_risk_text);
        }

        // Case 4: Emily Davis
        View case4 = findViewById(R.id.case4);
        if (case4 != null) {
            setupCaseItem(case4, "Emily Davis", "P-1021", "Jan 20", "Follow-up", R.color.followup_bg, R.color.followup_text);
        }

        // Case 5: James Miller
        View case5 = findViewById(R.id.case5);
        if (case5 != null) {
            setupCaseItem(case5, "James Miller", "P-1020", "Jan 18", "Completed", R.color.low_risk_bg, R.color.low_risk_text);
        }
    }

    private void setupCaseItem(View itemView, final String name, final String id, String date, String status, int bgRes, int textRes) {
        ((TextView) itemView.findViewById(R.id.tvPatientName)).setText(name);
        ((TextView) itemView.findViewById(R.id.tvPatientDetail)).setText(id + " • " + date);
        ((TextView) itemView.findViewById(R.id.tvStatus)).setText(status);
        ((MaterialCardView) itemView.findViewById(R.id.cardStatus)).setCardBackgroundColor(getResources().getColor(bgRes));
        ((TextView) itemView.findViewById(R.id.tvStatus)).setTextColor(getResources().getColor(textRes));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorCasesActivity.this, PatientOutcomeActivity.class);
                intent.putExtra("PATIENT_NAME", name);
                intent.putExtra("PATIENT_ID", id);
                startActivity(intent);
            }
        });
    }
}