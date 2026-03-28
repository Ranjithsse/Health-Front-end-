package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthpredict.network.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorSetNewPasswordActivity extends AppCompatActivity {
    private String token;
    private String uidb64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_set_new_password);

        ImageView ivBack = findViewById(R.id.ivBack);
        EditText etNewPassword = findViewById(R.id.etNewPassword);
        EditText etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword);
        MaterialButton btnResetPassword = findViewById(R.id.btnResetPassword);

        // Parse Deep Link
        Uri data = getIntent().getData();
        if (data != null && data.isHierarchical()) {
            token = data.getQueryParameter("token");
            uidb64 = data.getQueryParameter("uidb64");
            String email = data.getQueryParameter("email");
            
            // Optionally could display the email address somewhere.
            if (token == null || uidb64 == null) {
                Toast.makeText(this, "Invalid password reset link", Toast.LENGTH_LONG).show();
            }
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etNewPassword.getText().toString().trim();
                String confirmPassword = etConfirmNewPassword.getText().toString().trim();

                if (password.isEmpty()) {
                    etNewPassword.setError("Password is required");
                    etNewPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    etNewPassword.setError("Password must be at least 6 characters");
                    etNewPassword.requestFocus();
                    return;
                }

                if (confirmPassword.isEmpty()) {
                    etConfirmNewPassword.setError("Please confirm your password");
                    etConfirmNewPassword.requestFocus();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    etConfirmNewPassword.setError("Passwords do not match");
                    etConfirmNewPassword.requestFocus();
                    return;
                }

                if (token == null || uidb64 == null) {
                    Toast.makeText(DoctorSetNewPasswordActivity.this, "Invalid reset link parameters. Try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call backend API
                btnResetPassword.setEnabled(false);
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("new_password", password);
                requestBody.put("token", token);
                requestBody.put("uidb64", uidb64);

                RetrofitClient.getApiService(DoctorSetNewPasswordActivity.this)
                        .confirmPasswordReset(requestBody)
                        .enqueue(new Callback<Map<String, String>>() {
                            @Override
                            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                                btnResetPassword.setEnabled(true);
                                if (response.isSuccessful()) {
                                    Toast.makeText(DoctorSetNewPasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();

                                    // Redirect to login screen
                                    Intent intent = new Intent(DoctorSetNewPasswordActivity.this, DoctorLoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(DoctorSetNewPasswordActivity.this, "Failed to update password. Link may have expired.", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                                btnResetPassword.setEnabled(true);
                                Toast.makeText(DoctorSetNewPasswordActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
