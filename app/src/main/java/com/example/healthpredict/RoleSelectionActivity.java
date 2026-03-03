package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class RoleSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        MaterialButton btnContinue = findViewById(R.id.btnContinue);

        if (btnContinue != null) {
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Direct navigation to DoctorLoginActivity as requested
                    Intent intent = new Intent(RoleSelectionActivity.this, DoctorLoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
