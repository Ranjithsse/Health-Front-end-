package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        // History Card Navigation
        View cardHistory = findViewById(R.id.cardHistory);
        if (cardHistory != null) {
            cardHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ReportsActivity.this, DoctorCasesActivity.class));
                }
            });
        }

        // Analytics Card Navigation
        View cardAnalytics = findViewById(R.id.cardAnalytics);
        if (cardAnalytics != null) {
            cardAnalytics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ReportsActivity.this, StatisticsActivity.class));
                }
            });
        }

        setupBottomNavigation();
        setupRecentReports();
    }

    private void setupBottomNavigation() {
        findViewById(R.id.navHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportsActivity.this, DoctorHomeActivity.class));
                finish();
            }
        });

        findViewById(R.id.navCases).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportsActivity.this, DoctorCasesActivity.class));
                finish();
            }
        });

        findViewById(R.id.navProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportsActivity.this, DoctorProfileActivity.class));
                finish();
            }
        });
    }

    private void setupRecentReports() {
        // Report 1: Robert Wilson
        View report1 = findViewById(R.id.report1);
        if (report1 != null) {
            ((TextView) report1.findViewById(R.id.tvPatientName)).setText("Robert Wilson");
            ((TextView) report1.findViewById(R.id.tvReportDetail)).setText("Full Assessment • Jan 24, 2025");
            
            report1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ReportsActivity.this, FinalReportActivity.class);
                    intent.putExtra("PATIENT_NAME", "Robert Wilson");
                    intent.putExtra("PATIENT_ID", "1024");
                    startActivity(intent);
                }
            });
        }

        // Report 2: Sarah Johnson
        View report2 = findViewById(R.id.report2);
        if (report2 != null) {
            ((TextView) report2.findViewById(R.id.tvPatientName)).setText("Sarah Johnson");
            ((TextView) report2.findViewById(R.id.tvReportDetail)).setText("Risk Analysis • Jan 23, 2025");
            
            report2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ReportsActivity.this, FinalReportActivity.class);
                    intent.putExtra("PATIENT_NAME", "Sarah Johnson");
                    intent.putExtra("PATIENT_ID", "1023");
                    startActivity(intent);
                }
            });
        }

        // Report 3: Michael Brown
        View report3 = findViewById(R.id.report3);
        if (report3 != null) {
            ((TextView) report3.findViewById(R.id.tvPatientName)).setText("Michael Brown");
            ((TextView) report3.findViewById(R.id.tvReportDetail)).setText("Follow-up • Jan 22, 2025");
            
            report3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ReportsActivity.this, FinalReportActivity.class);
                    intent.putExtra("PATIENT_NAME", "Michael Brown");
                    intent.putExtra("PATIENT_ID", "1022");
                    startActivity(intent);
                }
            });
        }

        // Report 4: Emily Davis
        View report4 = findViewById(R.id.report4);
        if (report4 != null) {
            ((TextView) report4.findViewById(R.id.tvPatientName)).setText("Emily Davis");
            ((TextView) report4.findViewById(R.id.tvReportDetail)).setText("Lab Results • Jan 20, 2025");
            
            report4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ReportsActivity.this, FinalReportActivity.class);
                    intent.putExtra("PATIENT_NAME", "Emily Davis");
                    intent.putExtra("PATIENT_ID", "1021");
                    startActivity(intent);
                }
            });
        }
    }
}
