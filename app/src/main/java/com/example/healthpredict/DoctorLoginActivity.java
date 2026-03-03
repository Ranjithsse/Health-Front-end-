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

import com.example.healthpredict.network.AuthResponse;
import com.example.healthpredict.network.LoginRequest;
import com.example.healthpredict.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

<<<<<<< HEAD
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
=======
                // Show loading or disable button
                btnSignIn.setEnabled(false);

                LoginRequest loginRequest = new LoginRequest(email, password);
                RetrofitClient.getApiService().login(loginRequest).enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        btnSignIn.setEnabled(true);
                        if (response.isSuccessful() && response.body() != null) {
                            AuthResponse authResponse = response.body();
                            
                            // Save session details
                            SharedPreferences prefs = getSharedPreferences("HealthPredictPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean("is_logged_in", true);
                            editor.putString("user_email", authResponse.user.email);
                            editor.putString("user_name", authResponse.user.username);
                            editor.apply();

                            Toast.makeText(DoctorLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            // Navigate to DoctorHomeActivity
                            Intent intent = new Intent(DoctorLoginActivity.this, DoctorHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(DoctorLoginActivity.this, "Invalid credentials or Server Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        btnSignIn.setEnabled(true);
                        Toast.makeText(DoctorLoginActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
>>>>>>> a41db9c9b76a4cedc18eb27294c386544b564c4b
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
