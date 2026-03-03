package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class DoctorSetNewPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_set_new_password);

        ImageView ivBack = findViewById(R.id.ivBack);
        EditText etNewPassword = findViewById(R.id.etNewPassword);
        EditText etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword);
        MaterialButton btnResetPassword = findViewById(R.id.btnResetPassword);

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

                // In a real application, you would update the password in your database here.
                
                Toast.makeText(DoctorSetNewPasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();

                // Redirect to login screen
                Intent intent = new Intent(DoctorSetNewPasswordActivity.this, DoctorLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
