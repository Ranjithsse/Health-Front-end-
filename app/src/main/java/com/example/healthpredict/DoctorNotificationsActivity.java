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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.Toast;

public class DoctorNotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_notifications);

        // Mark all as read when opening this activity
        LocalNotificationManager.getInstance(this).markAllAsRead();

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }

        setupRecyclerView();
        setupBottomNavigation();
        fetchNotifications();
    }

    private void fetchNotifications() {
        List<Notification> notifications = LocalNotificationManager.getInstance(this).getNotifications();
        updateNotifications(notifications);
    }

    private void updateNotifications(List<Notification> notifications) {
        RecyclerView rvNotifications = findViewById(R.id.rvNotifications);
        NotificationAdapter adapter = new NotificationAdapter(notifications);
        rvNotifications.setAdapter(adapter);
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
