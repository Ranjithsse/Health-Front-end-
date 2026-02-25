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

        String email = getIntent().getStringExtra("EMAIL_ADDRESS");
        TextView tvSubtitle = findViewById(R.id.tvSubtitle);
        if (email != null && tvSubtitle != null) {
            tvSubtitle.setText("We've sent a password reset link to\n" + email);
        }

        MaterialButton btnBackToLogin = findViewById(R.id.btnBackToLogin);
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simulate clicking the link in the email
                Intent intent = new Intent(DocPasCheckActivity.this, DoctorSetNewPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}