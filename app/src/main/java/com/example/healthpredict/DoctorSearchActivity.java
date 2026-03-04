package com.example.healthpredict;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import com.example.healthpredict.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorSearchActivity extends AppCompatActivity {

    private EditText etSearchInput;
    private RecyclerView rvSearchResults;
    private TextView tvNoResults;
    private CaseSearchAdapter adapter;
    private List<CaseData> fullHistory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_doctor_search);

        View toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            ViewCompat.setOnApplyWindowInsetsListener(toolbar, (v, insets) -> {
                int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top;
                v.setPadding(v.getPaddingLeft(), statusBarHeight, v.getPaddingRight(), v.getPaddingBottom());
                v.getLayoutParams().height = (int) (64 * getResources().getDisplayMetrics().density) + statusBarHeight;
                return insets;
            });
        }

        View bottomNav = findViewById(R.id.bottomNav);
        if (bottomNav != null) {
            ViewCompat.setOnApplyWindowInsetsListener(bottomNav, (v, insets) -> {
                int navBarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom;
                v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), navBarHeight);
                return insets;
            });
        }

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }

        etSearchInput = findViewById(R.id.etSearchInput);
        rvSearchResults = findViewById(R.id.rvSearchResults);
        tvNoResults = findViewById(R.id.tvNoResults);

        fetchFullHistoryFromServer();
        setupRecyclerView();
        setupSearch();
        setupNavigation();
    }

    private void fetchFullHistoryFromServer() {
        RetrofitClient.getApiService().getCases(null).enqueue(new Callback<List<CaseData>>() {
            @Override
            public void onResponse(Call<List<CaseData>> call, Response<List<CaseData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fullHistory = response.body();
                } else {
                    fullHistory = HistoryManager.getInstance().getCaseHistory();
                }
            }

            @Override
            public void onFailure(Call<List<CaseData>> call, Throwable t) {
                fullHistory = HistoryManager.getInstance().getCaseHistory();
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new CaseSearchAdapter(new ArrayList<>(), this::onCaseClicked);
        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));
        rvSearchResults.setAdapter(adapter);
    }

    private void setupSearch() {
        etSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPatients(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void filterPatients(String query) {
        List<CaseData> filteredList = new ArrayList<>();
        if (!query.isEmpty()) {
            String lowerQuery = query.toLowerCase().trim();
            for (CaseData data : fullHistory) {
                String name = (data.patientName != null ? data.patientName : "").toLowerCase();
                String id = (data.patientId != null ? data.patientId : "").toLowerCase();
                if (name.contains(lowerQuery) || id.contains(lowerQuery)) {
                    filteredList.add(data);
                }
            }
        }

        adapter.updateList(filteredList);

        if (filteredList.isEmpty() && !query.isEmpty()) {
            tvNoResults.setVisibility(View.VISIBLE);
            rvSearchResults.setVisibility(View.GONE);
        } else {
            tvNoResults.setVisibility(View.GONE);
            rvSearchResults.setVisibility(View.VISIBLE);
        }
    }

    private void onCaseClicked(CaseData data) {
        CaseData.getInstance().reset();
        CaseData.getInstance().copyFrom(data);
        Intent intent = new Intent(this, PatientDetailActivity.class);
        intent.putExtra("PATIENT_NAME", data.patientName);
        intent.putExtra("PATIENT_ID", data.patientId);
        startActivity(intent);
    }

    private void setupNavigation() {
        View navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(DoctorSearchActivity.this, DoctorHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            });
        }

        View navCases = findViewById(R.id.navCases);
        if (navCases != null) {
            navCases.setOnClickListener(
                    v -> startActivity(new Intent(DoctorSearchActivity.this, DoctorCasesActivity.class)));
        }

        View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(
                    v -> startActivity(new Intent(DoctorSearchActivity.this, ReportsActivity.class)));
        }

        View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(
                    v -> startActivity(new Intent(DoctorSearchActivity.this, DoctorProfileActivity.class)));
        }
    }

    // Patient data class
    static class Patient {
        private final String name;
        private final String id;

        public Patient(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }
    }

    // Adapter for the RecyclerView
    static class CaseSearchAdapter extends RecyclerView.Adapter<CaseSearchAdapter.ViewHolder> {
        private List<CaseData> cases;
        private final OnCaseClickListener listener;

        public interface OnCaseClickListener {
            void onCaseClick(CaseData data);
        }

        public CaseSearchAdapter(List<CaseData> cases, OnCaseClickListener listener) {
            this.cases = cases;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient_search, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CaseData data = cases.get(position);
            holder.bind(data, listener);
        }

        @Override
        public int getItemCount() {
            return cases.size();
        }

        public void updateList(List<CaseData> newList) {
            cases = newList;
            notifyDataSetChanged();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvPatientName;
            private final TextView tvPatientId;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvPatientName = itemView.findViewById(R.id.tvPatientName);
                tvPatientId = itemView.findViewById(R.id.tvPatientId);
            }

            public void bind(final CaseData data, final OnCaseClickListener listener) {
                String name = data.patientName != null && !data.patientName.isEmpty() ? data.patientName
                        : data.patientId;
                tvPatientName.setText(name);
                tvPatientId.setText("ID: " + data.patientId);
                itemView.setOnClickListener(v -> listener.onCaseClick(data));
            }
        }
    }
}
