package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

import android.widget.EditText;
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

        MaterialButton btnCreateAccount = findViewById(R.id.btnCreateAccount);
        TextView tvSignIn = findViewById(R.id.tvSignIn);
        EditText etFullName = findViewById(R.id.etFullName);
        EditText etHospital = findViewById(R.id.etHospital);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = etFullName.getText().toString().trim();
                String hospital = etHospital.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
                    Toast.makeText(DoctorSignUpActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                btnCreateAccount.setEnabled(false);

                // For username, we'll use email or full name without spaces
                String username = email; 

                RegisterRequest request = new RegisterRequest(username, email, password, "Internal Medicine", hospital);
                RetrofitClient.getApiService().signup(request).enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        btnCreateAccount.setEnabled(true);
                        if (response.isSuccessful()) {
                            Toast.makeText(DoctorSignUpActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                            
                            // Navigate to DoctorHomeActivity (or Login)
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
                finish(); // Go back to login
            }
        });
    }
}