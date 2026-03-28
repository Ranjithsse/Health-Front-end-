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

        for (CaseData data : history) {
            if (data.primarySystem == null) continue;
            String system = data.primarySystem.toLowerCase();
            if (system.contains("cardio") || system.contains("heart")) cardiac++;
            else if (system.contains("diabet") || system.contains("endo")) diabetes++;
            else if (system.contains("onco") || system.contains("cancer")) oncology++;
            else if (system.contains("renal") || system.contains("kidney")) renal++;
        }

        int totalCategories = cardiac + diabetes + oncology + renal;
        if (totalCategories == 0) totalCategories = 1; // Prevent division by zero

        updateBarWidth(R.id.viewCardiacFilled, R.id.viewCardiacEmpty, cardiac, totalCategories);
        updateBarWidth(R.id.viewDiabetesFilled, R.id.viewDiabetesEmpty, diabetes, totalCategories);
        updateBarWidth(R.id.viewOncologyFilled, R.id.viewOncologyEmpty, oncology, totalCategories);
        updateBarWidth(R.id.viewRenalFilled, R.id.viewRenalEmpty, renal, totalCategories);
    }

    private void updateBarWidth(int filledId, int emptyId, int count, int total) {
        View filled = findViewById(filledId);
        View empty = findViewById(emptyId);
        
        if (filled != null && empty != null) {
            float weight = (float) count / total;
            
            // Adjust to ensure even small bars are minimally visible if they have > 0 count
            if (weight < 0.05f && count > 0) {
                weight = 0.05f;
            } else if (count == 0) {
                weight = 0.0f;
            }

            android.widget.LinearLayout.LayoutParams filledParams = 
                (android.widget.LinearLayout.LayoutParams) filled.getLayoutParams();
            filledParams.weight = weight;
            filled.setLayoutParams(filledParams);

            android.widget.LinearLayout.LayoutParams emptyParams = 
                (android.widget.LinearLayout.LayoutParams) empty.getLayoutParams();
            emptyParams.weight = 1.0f - weight;
            empty.setLayoutParams(emptyParams);
        }
    }
}
