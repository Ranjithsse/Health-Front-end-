package com.example.healthpredict;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DoctorSearchActivity extends AppCompatActivity {

    private EditText etSearchInput;
    private RecyclerView rvSearchResults;
    private TextView tvNoResults;
    private SearchAdapter adapter;
    private List<CaseData> fullHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_search);

        etSearchInput = findViewById(R.id.etSearchInput);
        rvSearchResults = findViewById(R.id.rvSearchResults);
        tvNoResults = findViewById(R.id.tvNoResults);

        fullHistory = HistoryManager.getInstance().getCaseHistory();
        
        setupRecyclerView();
        setupSearchLogic();
        setupNavigation();
    }

    private void setupRecyclerView() {
        adapter = new SearchAdapter(new ArrayList<>(fullHistory), caseData -> {
            // Copy selected case to singleton and show report
            CaseData singleton = CaseData.getInstance();
            singleton.reset();
            copyToSingleton(caseData);
            startActivity(new Intent(DoctorSearchActivity.this, FinalReportActivity.class));
        });
        
        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));
        rvSearchResults.setAdapter(adapter);
    }

    private void setupSearchLogic() {
        etSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterResults(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterResults(String query) {
        List<CaseData> filteredList = new ArrayList<>();
        for (CaseData data : fullHistory) {
            boolean matchesName = data.patientName != null && data.patientName.toLowerCase().contains(query.toLowerCase());
            boolean matchesId = data.patientId != null && data.patientId.toLowerCase().contains(query.toLowerCase());
            
            if (matchesName || matchesId) {
                filteredList.add(data);
            }
        }
        
        adapter.updateList(filteredList);
        tvNoResults.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void setupNavigation() {
        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }

        findViewById(R.id.navHome).setOnClickListener(v -> {
            Intent intent = new Intent(this, DoctorHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        findViewById(R.id.navCases).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorCasesActivity.class));
        });

        findViewById(R.id.navReports).setOnClickListener(v -> {
            startActivity(new Intent(this, ReportsActivity.class));
        });

        findViewById(R.id.navProfile).setOnClickListener(v -> {
            startActivity(new Intent(this, DoctorProfileActivity.class));
        });
    }

    private void copyToSingleton(CaseData data) {
        CaseData singleton = CaseData.getInstance();
        singleton.patientId = data.patientId;
        singleton.patientName = data.patientName;
        singleton.date = data.date;
        singleton.gender = data.gender;
        singleton.primarySystem = data.primarySystem;
        singleton.riskScore = data.riskScore;
        singleton.riskLevel = data.riskLevel;
        singleton.accuracy = data.accuracy;
        singleton.providerNotes = data.providerNotes;
        singleton.oneYearPrediction = data.oneYearPrediction;
        singleton.threeYearPrediction = data.threeYearPrediction;
        singleton.fiveYearPrediction = data.fiveYearPrediction;
    }
}
