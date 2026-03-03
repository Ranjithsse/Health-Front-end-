package com.example.healthpredict;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class HelpSupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnBackFooter = findViewById(R.id.btnBackFooter);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (btnBackFooter != null) {
            btnBackFooter.setOnClickListener(v -> finish());
        }

        setupFaqs();
        setupSupportEmail();

        findViewById(R.id.navHome).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorHomeActivity.class));
            finish();
        });

        findViewById(R.id.navCases).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorCasesActivity.class));
            finish();
        });

        findViewById(R.id.navReports).setOnClickListener(v -> {
            startActivity(new Intent(this, ReportsActivity.class));
            finish();
        });

        findViewById(R.id.navProfile).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorProfileActivity.class));
            finish();
        });
    }

    private void setupSupportEmail() {
        TextView tvSupportEmail = findViewById(R.id.tvSupportEmail);
        if (tvSupportEmail != null) {
            SharedPreferences prefs = getSharedPreferences("HealthPredictPrefs", MODE_PRIVATE);
            String userEmail = prefs.getString("user_email", "support@healthmonitor.ai");
            tvSupportEmail.setText(userEmail);
        }
    }

    private void setupFaqs() {
        setupFaqItem(findViewById(R.id.faq1), 
                "How is the AI confidence score calculated?", 
                "The confidence score represents the model's certainty based on the quality and completeness of the input data. It factors in data density, historical pattern matching, and variance in the probabilistic output.");
        
        setupFaqItem(findViewById(R.id.faq2), 
                "Can I override the AI risk assessment?", 
                "Yes, the AI is designed as a decision-support tool. Clinicians can provide 'Provider Notes' and adjust the clinical plan based on their expert judgment, which the model uses for continuous learning.");
        
        setupFaqItem(findViewById(R.id.faq3), 
                "Is the data encrypted at rest?", 
                "All patient health information (PHI) is encrypted using AES-256 standards both in transit and at rest, ensuring full compliance with HIPAA and relevant data protection regulations.");
    }

    private void setupFaqItem(View faqView, String question, String answer) {
        if (faqView == null) return;

        TextView tvTitle = faqView.findViewById(R.id.tvFaqTitle);
        TextView tvAnswer = faqView.findViewById(R.id.tvFaqAnswer);
        ImageView ivChevron = faqView.findViewById(R.id.ivChevron);
        View divider = faqView.findViewById(R.id.vDivider);

        if (tvTitle != null) tvTitle.setText(question);
        if (tvAnswer != null) tvAnswer.setText(answer);

        faqView.setOnClickListener(v -> {
            boolean isVisible = tvAnswer.getVisibility() == View.VISIBLE;
            tvAnswer.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            if (divider != null) divider.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            if (ivChevron != null) {
                ivChevron.setImageResource(isVisible ? R.drawable.ic_chevron_down : R.drawable.ic_chevron_right);
                // Note: The logic for ic_chevron_right vs down might need rotation instead if using one icon
                ivChevron.setRotation(isVisible ? 0 : 180);
            }
        });
    }
}
