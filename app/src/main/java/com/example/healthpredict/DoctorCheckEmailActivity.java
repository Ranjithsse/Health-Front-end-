package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class DoctorCheckEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_check_email);

        TextView tvEmailAddress = findViewById(R.id.tvEmailAddress);
        MaterialButton btnBackToLogin = findViewById(R.id.btnBackToLogin);

        String email = getIntent().getStringExtra("EMAIL");
        if (email != null && !email.isEmpty()) {
            tvEmailAddress.setText(email);
        }

        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorCheckEmailActivity.this, DoctorLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
