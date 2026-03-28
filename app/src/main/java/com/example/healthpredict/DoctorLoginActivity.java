package com.example.healthpredict;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
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
        EditText etPassword = findViewById(R.id.etPassword);
        MaterialButton btnSignIn = findViewById(R.id.btnSignIn);
        TextView tvSignUp = findViewById(R.id.tvSignUp);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // 3. Empty Field Check
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(DoctorLoginActivity.this, "Please fill all required fields.", Toast.LENGTH_SHORT).show();
                    if (email.isEmpty()) {
                        etEmail.setError("Please fill this field");
                        etEmail.requestFocus();
                    } else {
                        etPassword.setError("Please fill this field");
                        etPassword.requestFocus();
                    }
                    return;
                }

                // 1. Email Address Validation
                if (!email.endsWith("@gmail.com")) {
                    etEmail.setError("Please enter a valid Gmail address (example@gmail.com).");
                    etEmail.requestFocus();
                    return;
                }

                // 2. Password Validation
                if (password.length() < 8) {
                    etPassword.setError("Password must be at least 8 characters.");
                    etPassword.requestFocus();
                    return;
                }

                // Show loading or disable button
                btnSignIn.setEnabled(false);

                LoginRequest loginRequest = new LoginRequest(email, password);
                RetrofitClient.getApiService(DoctorLoginActivity.this).login(loginRequest)
                        .enqueue(new Callback<AuthResponse>() {
                            @Override
                            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                                btnSignIn.setEnabled(true);
                                if (response.isSuccessful() && response.body() != null) {
                                    AuthResponse authResponse = response.body();

                                    // Clear session details and local managers to prevent data leakage from a previous user
                                    SharedPreferences prefs = getSharedPreferences("HealthPredictPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putBoolean("is_logged_in", true);
                                    editor.putString("user_email", authResponse.user.email);
                                    editor.putString("user_name", authResponse.user.username);
                                    editor.putString("access_token", authResponse.access);
                                    editor.putString("refresh_token", authResponse.refresh);
                                    editor.apply();

                                    // Clear local caches - they will be repopulated from server in HomeActivity
                                    HistoryManager.getInstance().clearHistory(DoctorLoginActivity.this);
                                    LocalNotificationManager.getInstance(DoctorLoginActivity.this).clearNotifications();

                                    // 7. Success
                                    Toast.makeText(DoctorLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT)
                                            .show();

                                    // Navigate to DoctorHomeActivity
                                    Intent intent = new Intent(DoctorLoginActivity.this, DoctorHomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // 4. Wrong Password Handling & 5. Invalid Login
                                    String errorMessage = "Invalid email or password.";
                                    try {
                                        if (response.errorBody() != null) {
                                            String errorJson = response.errorBody().string();
                                            if (errorJson.contains("Incorrect password")) {
                                                errorMessage = "Incorrect password. Please try again.";
                                                etPassword.setError(errorMessage);
                                                etPassword.requestFocus();
                                            } else {
                                                etEmail.setError(errorMessage);
                                                etPassword.setError(errorMessage);
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(DoctorLoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<AuthResponse> call, Throwable t) {
                                btnSignIn.setEnabled(true);
                                Toast.makeText(DoctorLoginActivity.this, "Network Error: " + t.getMessage(),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
            }
        });

        // Hidden feature: Long press Sign In to change Server IP
        btnSignIn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showIpConfigDialog();
                return true;
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorLoginActivity.this, DoctorSignUpActivity.class));
            }
        });


    }

    private void showIpConfigDialog() {
        SharedPreferences prefs = getSharedPreferences("HealthPredictPrefs", MODE_PRIVATE);
        String currentIp = prefs.getString("server_ip", "10.19.162.179");

        EditText etIp = new EditText(this);
        etIp.setText(currentIp);
        etIp.setHint("e.g. 192.168.1.5");
        int padding = (int) (24 * getResources().getDisplayMetrics().density);
        etIp.setPadding(padding, padding, padding, padding);

        new AlertDialog.Builder(this)
                .setTitle("Server Configuration")
                .setMessage("Enter the IPv4 address of your PC (check ipconfig):")
                .setView(etIp)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newIp = etIp.getText().toString().trim();
                    if (!newIp.isEmpty()) {
                        prefs.edit().putString("server_ip", newIp).apply();
                        RetrofitClient.resetRetrofitInstance();
                        Toast.makeText(this, "IP updated to: " + newIp, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
