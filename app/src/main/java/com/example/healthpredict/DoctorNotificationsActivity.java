package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.List;
import com.example.healthpredict.network.RetrofitClient;
import com.example.healthpredict.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.Toast;

public class DoctorNotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_notifications);

        // Fetch notifications (Individual clearing is handled on click)
        // LocalNotificationManager.getInstance(this).markAllAsRead();

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }

        setupRecyclerView();
        setupBottomNavigation();
        fetchNotifications();
    }

    private void fetchNotifications() {
        ApiService apiService = RetrofitClient.getApiService(this);
        apiService.getNotifications().enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Notification> serverNotifications = response.body();
                    
                    // Sync with local manager
                    LocalNotificationManager lnm = LocalNotificationManager.getInstance(DoctorNotificationsActivity.this);
                    lnm.replaceNotifications(serverNotifications);
                    
                    updateNotifications(lnm.getNotifications());
                } else {
                    // Fallback to local
                    updateNotifications(LocalNotificationManager.getInstance(DoctorNotificationsActivity.this).getNotifications());
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                // Fallback to local
                updateNotifications(LocalNotificationManager.getInstance(DoctorNotificationsActivity.this).getNotifications());
            }
        });
    }

    private void updateNotifications(List<Notification> notifications) {
        RecyclerView rvNotifications = findViewById(R.id.rvNotifications);
        NotificationAdapter adapter = new NotificationAdapter(notifications, this::handleNotificationClick);
        rvNotifications.setAdapter(adapter);
    }

    private void handleNotificationClick(Notification notification) {
        // 1. Mark as read on server
        ApiService apiService = RetrofitClient.getApiService(this);
        apiService.markNotificationRead(notification.getId()).enqueue(new Callback<java.util.Map<String, String>>() {
            @Override
            public void onResponse(Call<java.util.Map<String, String>> call, Response<java.util.Map<String, String>> response) {
                // Success - the notification will disappear on next fetch or local update
            }

            @Override
            public void onFailure(Call<java.util.Map<String, String>> call, Throwable t) {
            }
        });

        // 2. Clear locally and update UI
        List<Notification> currentList = LocalNotificationManager.getInstance(this).getNotifications();
        for (int i = 0; i < currentList.size(); i++) {
            if (currentList.get(i).getId() == notification.getId()) {
                currentList.remove(i);
                break;
            }
        }
        LocalNotificationManager.getInstance(this).replaceNotifications(currentList);
        updateNotifications(currentList);

        // 3. Navigate if it is a Final Report
        if (notification.getTitle().contains("Final Report") && notification.getRelatedId() != 0) {
            fetchCaseAndNavigate(notification.getRelatedId());
        } else if (notification.getTitle().contains("Tissue Analysis") && notification.getRelatedId() != 0) {
            // Optional: navigate to case details for tissue analysis too
            fetchCaseAndNavigate(notification.getRelatedId());
        }
    }

    private void fetchCaseAndNavigate(int caseId) {
        ApiService apiService = RetrofitClient.getApiService(this);
        apiService.getCase(caseId).enqueue(new Callback<CaseData>() {
            @Override
            public void onResponse(Call<CaseData> call, Response<CaseData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CaseData.getInstance().reset();
                    CaseData.getInstance().copyFrom(response.body());
                    
                    Intent intent;
                    if (CaseData.getInstance().status.equals("Completed")) {
                        intent = new Intent(DoctorNotificationsActivity.this, FinalReportActivity.class);
                    } else {
                        // If not completed, maybe go to case details?
                        intent = new Intent(DoctorNotificationsActivity.this, DoctorCasesActivity.class);
                    }
                    startActivity(intent);
                } else {
                    Toast.makeText(DoctorNotificationsActivity.this, "Could not load report details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CaseData> call, Throwable t) {
                Toast.makeText(DoctorNotificationsActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        RecyclerView rvNotifications = findViewById(R.id.rvNotifications);
        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupBottomNavigation() {
        View navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(this, DoctorHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }

        View navCases = findViewById(R.id.navCases);
        if (navCases != null) {
            navCases.setOnClickListener(v -> {
                Intent intent = new Intent(this, DoctorCasesActivity.class);
                startActivity(intent);
            });
        }

        View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(v -> {
                Intent intent = new Intent(this, ReportsActivity.class);
                startActivity(intent);
            });
        }

        View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                Intent intent = new Intent(this, DoctorProfileActivity.class);
                startActivity(intent);
            });
        }
    }
}
