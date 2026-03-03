package com.example.healthpredict;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class DoctorLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        EditText etEmail = findViewById(R.id.etEmail);
        MaterialButton btnSignIn = findViewById(R.id.btnSignIn);
        TextView tvSignUp = findViewById(R.id.tvSignUp);
        TextView tvForgotPassword = findViewById(R.id.tvForgotPassword);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                
                if (email.isEmpty()) {
                    etEmail.setError("Email is required");
                    etEmail.requestFocus();
                    return;
                }

                // Save email to SharedPreferences
                SharedPreferences prefs = getSharedPreferences("HealthPredictPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("user_email", email);
                // For login simulation, we'll set a default name if not already present
                if (!prefs.contains("user_name")) {
                    editor.putString("user_name", "Doctor");
                }
                editor.apply();

                // Handle login and navigate to DoctorHomeActivity
                startActivity(new Intent(DoctorLoginActivity.this, DoctorHomeActivity.class));
                finish();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorLoginActivity.this, DoctorSignUpActivity.class));
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorLoginActivity.this, DoctorPassResetActivity.class));
            }
        });
    }
}
