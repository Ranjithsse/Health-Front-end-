package com.example.healthpredict;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthpredict.network.ApiService;
import com.example.healthpredict.network.RetrofitClient;

import java.util.Map;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStatsFromServer();
    }

    private void loadStatsFromServer() {
        // Use local dynamic data for accurate representation of the user's statistics
        calculateAndDisplayStats();

        // Optional sync with server for global data
        ApiService apiService = RetrofitClient.getApiService(this);
        apiService.getDashboardStats().enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                // If there was external UI needed, handle here.
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
            }
        });
    }

    private void calculateAndDisplayStats() {
        HistoryManager.getInstance().init(this);
        List<CaseData> history = HistoryManager.getInstance().getCaseHistory();

        int cardiac = 0, diabetes = 0, oncology = 0, renal = 0;
        int respiratory = 0, neurological = 0, musculoskeletal = 0, gastrointestinal = 0;

        if (history != null && !history.isEmpty()) {
            for (CaseData data : history) {
                if (data.primarySystem == null || data.primarySystem.isEmpty()) continue;
                String system = data.primarySystem.toLowerCase();
                if (system.contains("cardio") || system.contains("heart")) cardiac++;
                else if (system.contains("diabet") || system.contains("endo")) diabetes++;
                else if (system.contains("onco") || system.contains("cancer")) oncology++;
                else if (system.contains("renal") || system.contains("kidney")) renal++;
                else if (system.contains("resp")) respiratory++;
                else if (system.contains("neuro")) neurological++;
                else if (system.contains("musculo")) musculoskeletal++;
                else if (system.contains("gastro")) gastrointestinal++;
            }
        }

        // If no cases yet, ensure all percentages are 0%
        if (cardiac == 0 && diabetes == 0 && oncology == 0 && renal == 0 && 
            respiratory == 0 && neurological == 0 && musculoskeletal == 0 && gastrointestinal == 0) {
            // Keep all at zero
        }

        int totalCategories = cardiac + diabetes + oncology + renal + 
                             respiratory + neurological + musculoskeletal + gastrointestinal;
        if (totalCategories == 0) totalCategories = 1; // Prevent division by zero

        // Colors from user reference
        String blue = "#2563EB";
        String green = "#10B981";
        String orange = "#F59E0B";
        String red = "#EF4444";
        String purple = "#8B5CF6";

        updateBarWidth(R.id.viewCardiacFilled, R.id.viewCardiacEmpty, R.id.tvCardiacPercentage, cardiac, totalCategories, blue);
        updateBarWidth(R.id.viewDiabetesFilled, R.id.viewDiabetesEmpty, R.id.tvDiabetesPercentage, diabetes, totalCategories, purple);
        updateBarWidth(R.id.viewOncologyFilled, R.id.viewOncologyEmpty, R.id.tvOncologyPercentage, oncology, totalCategories, red);
        updateBarWidth(R.id.viewRenalFilled, R.id.viewRenalEmpty, R.id.tvRenalPercentage, renal, totalCategories, green);
        updateBarWidth(R.id.viewRespiratoryFilled, R.id.viewRespiratoryEmpty, R.id.tvRespiratoryPercentage, respiratory, totalCategories, green);
        updateBarWidth(R.id.viewNeurologicalFilled, R.id.viewNeurologicalEmpty, R.id.tvNeurologicalPercentage, neurological, totalCategories, orange);
        updateBarWidth(R.id.viewMusculoskeletalFilled, R.id.viewMusculoskeletalEmpty, R.id.tvMusculoskeletalPercentage, musculoskeletal, totalCategories, blue);
        updateBarWidth(R.id.viewGastrointestinalFilled, R.id.viewGastrointestinalEmpty, R.id.tvGastrointestinalPercentage, gastrointestinal, totalCategories, red);
    }

    private void updateBarWidth(int filledId, int emptyId, int percentageId, int count, int total, String colorHex) {
        View filled = findViewById(filledId);
        View empty = findViewById(emptyId);
        android.widget.TextView percentageText = findViewById(percentageId);

        if (filled == null || empty == null || percentageText == null) return;

        float weight = (float) count / total;
        // Adjust to ensure even small bars are minimally visible if they have > 0 count
        if (weight < 0.03f && count > 0) {
            weight = 0.03f; // Reduced from 0.05f for better precision
        } else if (count == 0) {
            weight = 0.0f; // Exactly 0 for zero cases
        }

        android.widget.LinearLayout.LayoutParams filledParams = 
            (android.widget.LinearLayout.LayoutParams) filled.getLayoutParams();
        filledParams.weight = weight;
        filled.setLayoutParams(filledParams);
        
        // Apply color tint
        filled.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor(colorHex)));

        android.widget.LinearLayout.LayoutParams emptyParams = 
            (android.widget.LinearLayout.LayoutParams) empty.getLayoutParams();
        emptyParams.weight = 1.0f - weight;
        empty.setLayoutParams(emptyParams);

        // Update percentage text: ensure integer rounding remains consistent
        int realPercentage = (int) Math.round(((double) count / total) * 100);
        percentageText.setText(realPercentage + "%");
    }
}
