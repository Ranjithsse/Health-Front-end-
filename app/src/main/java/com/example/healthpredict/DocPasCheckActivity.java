package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class DocPasCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_pas_check);

        MaterialButton btnBackToLogin = findViewById(R.id.btnBackToLogin);
        TextView tvUserEmail = findViewById(R.id.tvUserEmail);

        // You can set the email dynamically if passed via Intent
        String email = getIntent().getStringExtra("EMAIL");
        if (email != null && !email.isEmpty()) {
            tvUserEmail.setText(email);
        }

        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // In a real scenario, the user would check their email and click a link.
                // For this demo, let's assume they've done that and we go to Set New Password screen.
                Intent intent = new Intent(DocPasCheckActivity.this, DoctorSetNewPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
