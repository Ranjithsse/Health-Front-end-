package com.example.healthpredict;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<Notification> notifications;

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
        holder.tvTitle.setText(notification.getTitle());
        holder.tvDescription.setText(notification.getDescription());
        holder.tvTime.setText(notification.getTime());

        String type = notification.getType();
        int iconRes = R.drawable.ic_notification;
        int bgColor = 0xFFEFF6FF; // notif_blue_bg

        if ("SUCCESS".equalsIgnoreCase(type)) {
            iconRes = R.drawable.ic_check;
            bgColor = 0xFFE1F9EB; // notif_green_bg
        } else if ("ALERT".equalsIgnoreCase(type)) {
            iconRes = R.drawable.ic_warning;
            bgColor = 0xFFFEF2F2; // notif_orange_bg
        } else if ("INFO".equalsIgnoreCase(type)) {
            iconRes = R.drawable.ic_download;
            bgColor = 0xFFEFF6FF; // notif_blue_bg
        }

        holder.ivIcon.setImageResource(iconRes);
        holder.iconContainer.setCardBackgroundColor(bgColor);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvTime;
        ImageView ivIcon;
        MaterialCardView iconContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvNotificationTitle);
            tvDescription = itemView.findViewById(R.id.tvNotificationMessage);
            tvTime = itemView.findViewById(R.id.tvNotificationTime);
            ivIcon = itemView.findViewById(R.id.ivNotificationIcon);
            iconContainer = itemView.findViewById(R.id.iconContainer);
        }
    }
}
