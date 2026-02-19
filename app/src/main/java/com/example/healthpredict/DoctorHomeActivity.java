package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class DoctorHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        // 1. Notification Navigation
        ImageView ivNotification = findViewById(R.id.ivNotification);
        if (ivNotification != null) {
            ivNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DoctorHomeActivity.this, DoctorNotificationsActivity.class));
                }
            });
        }

        // 2. Search Navigation
        View searchBar = findViewById(R.id.searchBar);
        EditText etSearch = findViewById(R.id.etSearch);
        View.OnClickListener searchClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorHomeActivity.this, DoctorSearchActivity.class));
            }
        };
        if (searchBar != null) searchBar.setOnClickListener(searchClickListener);
        if (etSearch != null) etSearch.setOnClickListener(searchClickListener);

        // 3. High Risk Alerts Navigation
        View cardHighRiskAlerts = findViewById(R.id.cardHighRiskAlerts);
        if (cardHighRiskAlerts != null) {
            cardHighRiskAlerts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DoctorHomeActivity.this, HighRiskAlertsActivity.class));
                }
            });
        }

        // 4. New Assessment Navigation
        View cardNewAssessment = findViewById(R.id.cardNewAssessment);
        if (cardNewAssessment != null) {
            cardNewAssessment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DoctorHomeActivity.this, NewCaseOneActivity.class));
                }
            });
        }

        // 5. View All Patients & Cases Navigation
        TextView tvViewAllPatients = findViewById(R.id.tvViewAllPatients);
        View navCases = findViewById(R.id.navCases);
        View.OnClickListener casesClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorHomeActivity.this, DoctorCasesActivity.class));
            }
        };
        if (tvViewAllPatients != null) tvViewAllPatients.setOnClickListener(casesClickListener);
        if (navCases != null) navCases.setOnClickListener(casesClickListener);

        // 6. Reports Navigation
        View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DoctorHomeActivity.this, ReportsActivity.class));
                }
            });
        }

        // 7. Profile Navigation
        View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DoctorHomeActivity.this, DoctorProfileActivity.class));
                }
            });
        }

        setupStats();
        setupRecentPatients();
    }

    private void setupStats() {
        // Accuracy
        View stat1 = findViewById(R.id.stat1);
        if (stat1 != null) {
            ((ImageView) stat1.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_accuracy);
            ((TextView) stat1.findViewById(R.id.tvStatValue)).setText("94.2%");
            ((TextView) stat1.findViewById(R.id.tvStatLabel)).setText("Accuracy");
        }

        // Patients
        View stat2 = findViewById(R.id.stat2);
        if (stat2 != null) {
            ((ImageView) stat2.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_patients);
            ((TextView) stat2.findViewById(R.id.tvStatValue)).setText("128");
            ((TextView) stat2.findViewById(R.id.tvStatLabel)).setText("Patients");
        }

        // This Month
        View stat3 = findViewById(R.id.stat3);
        if (stat3 != null) {
            ((ImageView) stat3.findViewById(R.id.ivStatIcon)).setImageResource(R.drawable.ic_month);
            ((TextView) stat3.findViewById(R.id.tvStatValue)).setText("24");
            ((TextView) stat3.findViewById(R.id.tvStatLabel)).setText("This Month");
        }
    }

    private void setupRecentPatients() {
        // Patient 1: Robert Wilson
        View patient1 = findViewById(R.id.p1);
        if (patient1 != null) {
            ((TextView) patient1.findViewById(R.id.tvInitial)).setText("R");
            ((TextView) patient1.findViewById(R.id.tvPatientName)).setText("Robert Wilson");
            ((TextView) patient1.findViewById(R.id.tvPatientDetail)).setText("P-1024 • Cardiac Risk");
            ((TextView) patient1.findViewById(R.id.tvStatus)).setText("Low Risk");
            ((MaterialCardView) patient1.findViewById(R.id.cardStatus)).setCardBackgroundColor(getResources().getColor(R.color.low_risk_bg));
            ((TextView) patient1.findViewById(R.id.tvStatus)).setTextColor(getResources().getColor(R.color.low_risk_text));
            
            patient1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DoctorHomeActivity.this, FinalReportActivity.class);
                    intent.putExtra("PATIENT_NAME", "Robert Wilson");
                    intent.putExtra("PATIENT_ID", "1024");
                    startActivity(intent);
                }
            });
        }

        // Patient 2: Sarah Johnson
        View patient2 = findViewById(R.id.p2);
        if (patient2 != null) {
            ((TextView) patient2.findViewById(R.id.tvInitial)).setText("S");
            ((TextView) patient2.findViewById(R.id.tvPatientName)).setText("Sarah Johnson");
            ((TextView) patient2.findViewById(R.id.tvPatientDetail)).setText("P-1023 • Pending Labs");
            ((TextView) patient2.findViewById(R.id.tvStatus)).setText("Pending");
            ((MaterialCardView) patient2.findViewById(R.id.cardStatus)).setCardBackgroundColor(getResources().getColor(R.color.pending_bg));
            ((TextView) patient2.findViewById(R.id.tvStatus)).setTextColor(getResources().getColor(R.color.pending_text));
            
            patient2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DoctorHomeActivity.this, FinalReportActivity.class);
                    intent.putExtra("PATIENT_NAME", "Sarah Johnson");
                    intent.putExtra("PATIENT_ID", "1023");
                    startActivity(intent);
                }
            });
        }

        // Patient 3: Michael Brown
        View patient3 = findViewById(R.id.p3);
        if (patient3 != null) {
            ((TextView) patient3.findViewById(R.id.tvInitial)).setText("M");
            ((TextView) patient3.findViewById(R.id.tvPatientName)).setText("Michael Brown");
            ((TextView) patient3.findViewById(R.id.tvPatientDetail)).setText("P-1022 • Diabetes Screen");
            ((TextView) patient3.findViewById(R.id.tvStatus)).setText("Moderate");
            ((MaterialCardView) patient3.findViewById(R.id.cardStatus)).setCardBackgroundColor(getResources().getColor(R.color.moderate_bg));
            ((TextView) patient3.findViewById(R.id.tvStatus)).setTextColor(getResources().getColor(R.color.moderate_text));
            
            patient3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DoctorHomeActivity.this, FinalReportActivity.class);
                    intent.putExtra("PATIENT_NAME", "Michael Brown");
                    intent.putExtra("PATIENT_ID", "1022");
                    startActivity(intent);
                }
            });
        }
    }
}