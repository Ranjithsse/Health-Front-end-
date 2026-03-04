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

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }

        setupRecyclerView();
        setupBottomNavigation();
        fetchNotifications();
    }

    private void fetchNotifications() {
        RetrofitClient.getApiService().getNotifications().enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateNotifications(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                // Fallback to empty
            }
        });
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

    // Adapter for the RecyclerView
    static class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
        private final List<Notification> notifications;

        public NotificationAdapter(List<Notification> notifications) {
            this.notifications = notifications;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Notification notification = notifications.get(position);
            holder.bind(notification);
        }

        @Override
        public int getItemCount() {
            return notifications.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            private final MaterialCardView iconContainer;
            private final ImageView ivNotificationIcon;
            private final TextView tvNotificationTitle;
            private final TextView tvNotificationMessage;
            private final TextView tvNotificationTime;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                iconContainer = itemView.findViewById(R.id.iconContainer);
                ivNotificationIcon = itemView.findViewById(R.id.ivNotificationIcon);
                tvNotificationTitle = itemView.findViewById(R.id.tvNotificationTitle);
                tvNotificationMessage = itemView.findViewById(R.id.tvNotificationMessage);
                tvNotificationTime = itemView.findViewById(R.id.tvNotificationTime);
            }

            public void bind(final Notification notification) {
                tvNotificationTitle.setText(notification.getTitle());
                tvNotificationMessage.setText(notification.getDescription());
                tvNotificationTime.setText(notification.getTime());

                String type = notification.getType() != null ? notification.getType() : "INFO";
                int iconRes = R.drawable.ic_notification;
                int bgColorRes = R.color.notif_blue_bg;
                int iconColorRes = R.color.notif_blue_icon;

                if (type.equalsIgnoreCase("SUCCESS")) {
                    iconRes = R.drawable.ic_check;
                    bgColorRes = R.color.notif_green_bg;
                    iconColorRes = R.color.notif_green_icon;
                } else if (type.equalsIgnoreCase("ALERT")) {
                    iconRes = R.drawable.ic_warning;
                    bgColorRes = R.color.notif_orange_bg;
                    iconColorRes = R.color.notif_orange_icon;
                }

                ivNotificationIcon.setImageResource(iconRes);
                iconContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), bgColorRes));
                ivNotificationIcon.setColorFilter(ContextCompat.getColor(itemView.getContext(), iconColorRes));
            }
        }
    }
}
