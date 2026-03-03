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
                    etEmail.setError("Email is required");
                    etEmail.requestFocus();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("Please enter a valid email");
                    etEmail.requestFocus();
                    return;
                }

                // In a real application, you would trigger a Firebase or backend password reset here.
                // For example:
                // FirebaseAuth.getInstance().sendPasswordResetEmail(email)...

                Toast.makeText(DoctorPassResetActivity.this, "Reset link sent to " + email, Toast.LENGTH_LONG).show();

                // Navigate to DocPasCheckActivity and pass the email
                Intent intent = new Intent(DoctorPassResetActivity.this, DocPasCheckActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
            }
        });
    }
}
