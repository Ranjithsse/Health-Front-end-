package com.example.healthpredict;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HelpSupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support);

        setupToolbar();
        setupFAQs();
        setupEmailSupport();
        setupBottomNavigation();

        View btnBackFooter = findViewById(R.id.btnBackFooter);
        if (btnBackFooter != null) {
            btnBackFooter.setOnClickListener(v -> finish());
        }
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void setupEmailSupport() {
        TextView tvSupportEmail = findViewById(R.id.tvSupportEmail);
        if (tvSupportEmail != null) {
            SharedPreferences prefs = getSharedPreferences("health_predict_prefs", MODE_PRIVATE);
            String userEmail = prefs.getString("user_email", "support@healthmonitor.ai");
            tvSupportEmail.setText(userEmail);
        }
    }

    private void setupFAQs() {
        // FAQ 1: AI Confidence Score
        setupFAQItem(findViewById(R.id.faq1),
                "How is the AI confidence score calculated?",
                "The confidence score represents the model's certainty based on the quality and completeness of the input data. It factors in data density, historical pattern matching, and variance in the probabilistic output.");

        // FAQ 2: Override Assessment
        setupFAQItem(findViewById(R.id.faq2),
                "Can I override the AI risk assessment?",
                "Yes. As the clinician, you always have the final say. You can manually adjust the risk category in the \"Clinical Notes\" section of the report. The system will log this as a \"Clinician Override\" for audit purposes.");

        // FAQ 3: Data Encryption
        setupFAQItem(findViewById(R.id.faq3),
                "Is the data encrypted at rest?",
                "Yes, all patient data is encrypted using AES-256 encryption at rest and TLS 1.3 in transit, fully compliant with HIPAA and GDPR standards.");
    }

    private void setupFAQItem(View view, String question, String answer) {
        if (view == null) return;

        TextView tvTitle = view.findViewById(R.id.tvFaqTitle);
        TextView tvAnswer = view.findViewById(R.id.tvFaqAnswer);
        ImageView ivChevron = view.findViewById(R.id.ivChevron);
        View divider = view.findViewById(R.id.divider);

        tvTitle.setText(question);
        tvAnswer.setText(answer);

        view.setOnClickListener(v -> {
            boolean isExpanded = tvAnswer.getVisibility() == View.VISIBLE;
            
            tvAnswer.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
            divider.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
            ivChevron.setRotation(isExpanded ? 0 : 180);
        });
    }

    private void setupBottomNavigation() {
        View navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(HelpSupportActivity.this, DoctorHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }

        View navCases = findViewById(R.id.navCases);
        if (navCases != null) {
            navCases.setOnClickListener(v -> {
                Intent intent = new Intent(HelpSupportActivity.this, DoctorCasesActivity.class);
                startActivity(intent);
            });
        }

        View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(v -> {
                Intent intent = new Intent(HelpSupportActivity.this, ReportsActivity.class);
                startActivity(intent);
            });
        }

        View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                Intent intent = new Intent(HelpSupportActivity.this, DoctorProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }
    }
}
