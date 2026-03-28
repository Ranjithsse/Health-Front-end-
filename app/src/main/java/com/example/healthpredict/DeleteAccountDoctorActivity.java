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
import com.example.healthpredict.network.ApiService;
import android.content.SharedPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                    ApiService apiService = RetrofitClient.getApiService(DeleteAccountDoctorActivity.this);
                    apiService.deleteAccount().enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                // 1. Clear Managers (Persistent Lists)
                                HistoryManager.getInstance().clearHistory(DeleteAccountDoctorActivity.this);
                                LocalNotificationManager.getInstance(DeleteAccountDoctorActivity.this).clearNotifications();

                                // 2. Clear Main SharedPreferences (Session, Profile, etc.)
                                SharedPreferences prefs = getSharedPreferences("HealthPredictPrefs", MODE_PRIVATE);
                                prefs.edit().clear().apply();

                                Toast.makeText(DeleteAccountDoctorActivity.this, "Account Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DeleteAccountDoctorActivity.this, GetStartedActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(DeleteAccountDoctorActivity.this, "Failed to delete account from server", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(DeleteAccountDoctorActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
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
