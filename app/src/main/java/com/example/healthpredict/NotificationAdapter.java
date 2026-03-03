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

        switch (notification.getType()) {
            case SUCCESS:
                holder.ivIcon.setImageResource(R.drawable.ic_check_circle_fill);
                holder.iconContainer.setCardBackgroundColor(0xFFE1F9EB);
                break;
            case ALERT:
                holder.ivIcon.setImageResource(R.drawable.ic_alert_circle_fill);
                holder.iconContainer.setCardBackgroundColor(0xFFFEE2E2);
                break;
            case INFO:
                holder.ivIcon.setImageResource(R.drawable.ic_info_circle_fill);
                holder.iconContainer.setCardBackgroundColor(0xFFE0F2FE);
                break;
        }
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
