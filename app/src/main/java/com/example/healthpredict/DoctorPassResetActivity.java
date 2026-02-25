package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class DoctorPassResetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_pass_reset);

        ImageView ivBack = findViewById(R.id.ivBack);
        EditText etEmail = findViewById(R.id.etEmail);
        MaterialButton btnSendResetLink = findViewById(R.id.btnSendResetLink);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSendResetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(DoctorPassResetActivity.this, "Please enter your email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                // Navigate to Check Email screen
                Intent intent = new Intent(DoctorPassResetActivity.this, DocPasCheckActivity.class);
                intent.putExtra("EMAIL_ADDRESS", email);
                startActivity(intent);
            }
        });
    }
}