package com.example.healthpredict;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

import com.example.healthpredict.network.AuthResponse;
import com.example.healthpredict.network.RegisterRequest;
import com.example.healthpredict.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);

        EditText etFullName = findViewById(R.id.etFullName);
        EditText etHospital = findViewById(R.id.etHospital);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        CheckBox cbTerms = findViewById(R.id.cbTerms);
        MaterialButton btnCreateAccount = findViewById(R.id.btnCreateAccount);
        TextView tvSignIn = findViewById(R.id.tvSignIn);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = etFullName.getText().toString().trim();
                String hospital = etHospital.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (fullName.isEmpty()) {
                    etFullName.setError("Full name is required");
                    etFullName.requestFocus();
                    return;
                }

                if (hospital.isEmpty()) {
                    etHospital.setError("Hospital name is required");
                    etHospital.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    etEmail.setError("Email is required");
                    etEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("Please enter a valid email");
                    etEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    etPassword.setError("Password is required");
                    etPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    etPassword.setError("Password must be at least 6 characters");
                    etPassword.requestFocus();
                    return;
                }

                if (!cbTerms.isChecked()) {
                    Toast.makeText(DoctorSignUpActivity.this, "Please agree to the Terms and Conditions", Toast.LENGTH_SHORT).show();
                    return;
                }

                btnCreateAccount.setEnabled(false);

                // Use email as username for now
                String username = email;

                RegisterRequest request = new RegisterRequest(username, email, password, "Internal Medicine", hospital);
                RetrofitClient.getApiService().signup(request).enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        btnCreateAccount.setEnabled(true);
                        if (response.isSuccessful()) {
                            // Save user info to SharedPreferences
                            SharedPreferences prefs = getSharedPreferences("HealthPredictPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("user_email", email);
                            editor.putString("user_name", fullName);
                            editor.putBoolean("is_logged_in", true);
                            editor.apply();

                            Toast.makeText(DoctorSignUpActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();

                            // Navigate to DoctorHomeActivity
                            Intent intent = new Intent(DoctorSignUpActivity.this, DoctorHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(DoctorSignUpActivity.this, "Signup failed: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        btnCreateAccount.setEnabled(true);
                        Toast.makeText(DoctorSignUpActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
