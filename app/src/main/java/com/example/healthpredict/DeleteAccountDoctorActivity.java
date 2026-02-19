package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DeleteAccountDoctorActivity extends AppCompatActivity {

    private EditText etConfirmDelete;
    private Button btnPermanentlyDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account_doctor);

        etConfirmDelete = findViewById(R.id.etConfirmDelete);
        btnPermanentlyDelete = findViewById(R.id.btnPermanentlyDelete);

        // Initially disable the delete button
        btnPermanentlyDelete.setEnabled(false);
        btnPermanentlyDelete.setAlpha(0.5f);

        setupToolbar();
        setupConfirmationLogic();
        setupActionButtons();
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> onBackPressed());
        }
    }

    private void setupConfirmationLogic() {
        etConfirmDelete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isDeleteTyped = s.toString().trim().equals("DELETE");
                btnPermanentlyDelete.setEnabled(isDeleteTyped);
                btnPermanentlyDelete.setAlpha(isDeleteTyped ? 1.0f : 0.5f);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupActionButtons() {
        btnPermanentlyDelete.setOnClickListener(v -> {
            // Perform Account Deletion Logic here
            Toast.makeText(this, "Account Deleted Permanently", Toast.LENGTH_LONG).show();
            
            // Navigate back to Login or Role Selection
            Intent intent = new Intent(this, RoleSelectionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        View btnCancel = findViewById(R.id.btnCancel);
        if (btnCancel != null) {
            btnCancel.setOnClickListener(v -> finish());
        }
    }
}
