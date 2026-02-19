package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class RoleSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        MaterialCardView cardProvider = findViewById(R.id.cardProvider);
        MaterialButton btnContinue = findViewById(R.id.btnContinue);

        if (cardProvider != null) {
            cardProvider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToNext();
                }
            });
        }

        if (btnContinue != null) {
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToNext();
                }
            });
        }
    }

    private void navigateToNext() {
        startActivity(new Intent(RoleSelectionActivity.this, DoctorLoginActivity.class));
        finish();
    }
}