package com.example.healthpredict;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<CaseData> results;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CaseData caseData);
    }

    public SearchAdapter(List<CaseData> results, OnItemClickListener listener) {
        this.results = results;
        this.listener = listener;
    }

    public void updateList(List<CaseData> newList) {
        this.results = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_patient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CaseData data = results.get(position);
        String name = (data.patientName != null && !data.patientName.isEmpty()) ? data.patientName : "Patient " + data.patientId;
        
        holder.tvName.setText(name);
        holder.tvDetail.setText(data.patientId + " • " + data.primarySystem);
        holder.tvStatus.setText(data.riskLevel);
        holder.tvInitial.setText(name.substring(0, 1).toUpperCase());

        // Status Styling
        updateStatusStyle(holder.cardStatus, holder.tvStatus, data.riskLevel);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(data));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    private void updateStatusStyle(MaterialCardView card, TextView tv, String risk) {
        if (risk == null) risk = "Low";
        switch (risk) {
            case "Low":
                card.setCardBackgroundColor(Color.parseColor("#DCFCE7"));
                tv.setTextColor(Color.parseColor("#166534"));
                break;
            case "Moderate":
                card.setCardBackgroundColor(Color.parseColor("#FEF9C3"));
                tv.setTextColor(Color.parseColor("#854D0E"));
                break;
            case "High":
            case "Critical":
                card.setCardBackgroundColor(Color.parseColor("#FEE2E2"));
                tv.setTextColor(Color.parseColor("#991B1B"));
                break;
            default:
                card.setCardBackgroundColor(Color.parseColor("#F1F5F9"));
                tv.setTextColor(Color.parseColor("#475569"));
                break;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDetail, tvStatus, tvInitial;
        MaterialCardView cardStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvPatientName);
            tvDetail = itemView.findViewById(R.id.tvPatientDetail);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvInitial = itemView.findViewById(R.id.tvInitial);
            cardStatus = itemView.findViewById(R.id.cardStatus);
        }
    }
}
