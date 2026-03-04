package com.example.healthpredict;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthpredict.network.ApiService;
import com.example.healthpredict.network.RetrofitClient;

import java.util.Map;

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
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getDashboardStats().enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Object> stats = response.body();
                    updateStatsUI(stats);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(StatisticsActivity.this, "Failed to load stats", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateStatsUI(Map<String, Object> stats) {
        // UI implementation for updating charts based on 'accuracy_trend' and
        // 'category_stats'
        // For a simple demo/premium feel, we can toast the summary or update specific
        // labels if present
        Double accuracy = (Double) stats.get("simulated_accuracy_val"); // if we added a numeric one
        String name = (String) stats.get("doctor_name");

        // Dynamic weight adjustment for category bars would happen here
        // For now, acknowledging the data receipt
    }
}
