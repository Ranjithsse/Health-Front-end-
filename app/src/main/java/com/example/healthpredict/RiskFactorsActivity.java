package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.example.healthpredict.network.RetrofitClient;
import com.example.healthpredict.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
import java.util.Map;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.card.MaterialCardView;

public class RiskFactorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_factors);

        ImageView btnBack = findViewById(R.id.btnBack);
        MaterialButton btnViewExplainability = findViewById(R.id.btnViewExplainability);

        btnBack.setOnClickListener(v -> finish());

        btnViewExplainability.setOnClickListener(v -> {
            startActivity(new Intent(RiskFactorsActivity.this, AiExplainabilityActivity.class));
        });

        loadRiskFactorsFromServer();
    }

    private void loadRiskFactorsFromServer() {
        int caseId = CaseData.getInstance().id;
        if (caseId == 0)
            return;

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.getExplainability(caseId).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, Object> data = response.body();
                    if (data.containsKey("risk_factors")) {
                        List<Map<String, Object>> factors = (List<Map<String, Object>>) data.get("risk_factors");
                        updateRiskFactorsUI(factors);
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(RiskFactorsActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRiskFactorsUI(List<Map<String, Object>> factors) {
        // IDs of the static cards in activity_risk_factors.xml
        int[] cardIds = { R.id.cardSmoking, R.id.cardBmi, R.id.cardBp };

        for (int i = 0; i < cardIds.length; i++) {
            MaterialCardView card = findViewById(cardIds[i]);
            if (card == null)
                continue;

            if (i < factors.size()) {
                Map<String, Object> factor = factors.get(i);
                card.setVisibility(View.VISIBLE);

                // Assuming IDs inside the card layout based on the XML structure
                // Use tag or indices if child IDs aren't unique across cards
                // For simplicity, let's find views within the card container
                TextView tvTitle = (TextView) ((android.view.ViewGroup) ((android.view.ViewGroup) card.getChildAt(0))
                        .getChildAt(1)).getChildAt(0);
                TextView tvDesc = (TextView) ((android.view.ViewGroup) ((android.view.ViewGroup) card.getChildAt(0))
                        .getChildAt(1)).getChildAt(1);
                ImageView ivIcon = (ImageView) ((android.view.ViewGroup) card.getChildAt(0)).getChildAt(0);

                if (tvTitle != null)
                    tvTitle.setText((String) factor.get("title"));
                if (tvDesc != null)
                    tvDesc.setText((String) factor.get("description"));

                String type = (String) factor.get("type");
                if ("warning".equals(type) && ivIcon != null) {
                    ivIcon.setImageResource(R.drawable.ic_warning_outline);
                } else if ("check".equals(type) && ivIcon != null) {
                    ivIcon.setImageResource(R.drawable.ic_check_simple);
                }
            } else {
                card.setVisibility(View.GONE);
            }
        }
    }
}
