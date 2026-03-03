package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class DeleteAccountDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account_doctor);

        ImageView btnBack = findViewById(R.id.btnBack);
        EditText etConfirmDelete = findViewById(R.id.etConfirmDelete);
        MaterialButton btnPermanentlyDelete = findViewById(R.id.btnPermanentlyDelete);
        MaterialButton btnCancel = findViewById(R.id.btnCancel);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPermanentlyDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirmText = etConfirmDelete.getText().toString().trim();
                if (confirmText.equalsIgnoreCase("DELETE")) {
                    // Logic to delete account
                    Toast.makeText(DeleteAccountDoctorActivity.this, "Account Deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DeleteAccountDoctorActivity.this, GetStartedActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(DeleteAccountDoctorActivity.this, "Please type DELETE to confirm", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
