package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.example.healthpredict.network.RetrofitClient;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                    etEmail.setError("Email is required");
                    etEmail.requestFocus();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("Please enter a valid email");
                    etEmail.requestFocus();
                    return;
                }

                // Trigger API call
                btnSendResetLink.setEnabled(false);
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("email", email);
                
                RetrofitClient.getApiService(DoctorPassResetActivity.this)
                        .requestPasswordReset(requestBody)
                        .enqueue(new Callback<Map<String, String>>() {
                            @Override
                            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                                btnSendResetLink.setEnabled(true);
                                if (response.isSuccessful()) {
                                    // Navigate to DoctorCheckEmailActivity and pass the email
                                    Intent intent = new Intent(DoctorPassResetActivity.this, DoctorCheckEmailActivity.class);
                                    intent.putExtra("EMAIL", email);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(DoctorPassResetActivity.this, "Failed to send reset link", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                                btnSendResetLink.setEnabled(true);
                                Toast.makeText(DoctorPassResetActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
