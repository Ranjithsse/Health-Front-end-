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
    }

    private void setupRecyclerView() {
        RecyclerView rvNotifications = findViewById(R.id.rvNotifications);
        rvNotifications.setLayoutManager(new LinearLayoutManager(this));

        List<Notification> notifications = getSampleNotifications();
        NotificationAdapter adapter = new NotificationAdapter(notifications);
        rvNotifications.setAdapter(adapter);
    }

    private List<Notification> getSampleNotifications() {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification("Risk Assessment Complete", "AI analysis for Case #1024 is ready.", "10 min ago", R.drawable.ic_check, R.color.notif_green_bg, R.color.notif_green_icon));
        notifications.add(new Notification("Health Report Ready", "PDF report for Sarah Johnson is available for download.", "1 hour ago", R.drawable.ic_description, R.color.notif_blue_bg, R.color.notif_blue_icon));
        notifications.add(new Notification("Elevated Risk Detected", "Case #1021 shows elevated cardiovascular risk markers.", "Yesterday", R.drawable.ic_warning, R.color.notif_orange_bg, R.color.notif_orange_icon));
        return notifications;
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

    // Notification Data Class
    static class Notification {
        private final String title;
        private final String message;
        private final String time;
        private final int iconResId;
        private final int bgColorResId;
        private final int iconColorResId;

        public Notification(String title, String message, String time, int iconResId, int bgColorResId, int iconColorResId) {
            this.title = title;
            this.message = message;
            this.time = time;
            this.iconResId = iconResId;
            this.bgColorResId = bgColorResId;
            this.iconColorResId = iconColorResId;
        }

        public String getTitle() { return title; }
        public String getMessage() { return message; }
        public String getTime() { return time; }
        public int getIconResId() { return iconResId; }
        public int getBgColorResId() { return bgColorResId; }
        public int getIconColorResId() { return iconColorResId; }
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
                tvNotificationMessage.setText(notification.getMessage());
                tvNotificationTime.setText(notification.getTime());
                ivNotificationIcon.setImageResource(notification.getIconResId());

                int bgColor = ContextCompat.getColor(itemView.getContext(), notification.getBgColorResId());
                int iconColor = ContextCompat.getColor(itemView.getContext(), notification.getIconColorResId());

                iconContainer.setCardBackgroundColor(bgColor);
                ivNotificationIcon.setColorFilter(iconColor);
            }
        }
    }
}
