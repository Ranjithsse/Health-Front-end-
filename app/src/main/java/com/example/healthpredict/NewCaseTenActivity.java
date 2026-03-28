package com.example.healthpredict;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import com.example.healthpredict.network.RetrofitClient;
import com.example.healthpredict.network.ApiService;
import com.example.healthpredict.network.PredictionResponse;
import android.app.ProgressDialog;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.bumptech.glide.Glide;

public class NewCaseTenActivity extends AppCompatActivity {

    private ImageView ivDicomImage;
    private View mockupVisuals;
    private TextView tvViewerTitle, tvSliceLabel;
    private View crosshairH, crosshairV;
    private MaterialCardView focusCircle;

    private MaterialCardView cardAxial, cardSagittal, cardCoronal;
    private TextView tvAxial, tvSagittal, tvCoronal;

    // Zoom components
    private View sliderContainer;
    private MaterialCardView sliderThumb;
    private float currentScale = 1.0f;
    private static final float MIN_SCALE = 0.5f;
    private static final float MAX_SCALE = 3.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_ten);

        // Initialize UI components
        ivDicomImage = findViewById(R.id.ivDicomImage);
        mockupVisuals = findViewById(R.id.mockupVisuals);
        tvViewerTitle = findViewById(R.id.tvViewerTitle);
        tvSliceLabel = findViewById(R.id.tvSliceLabel);

        crosshairH = findViewById(R.id.crosshairH);
        crosshairV = findViewById(R.id.crosshairV);
        focusCircle = findViewById(R.id.focusCircle);

        cardAxial = findViewById(R.id.cardAxial);
        cardSagittal = findViewById(R.id.cardSagittal);
        cardCoronal = findViewById(R.id.cardCoronal);

        tvAxial = findViewById(R.id.tvAxial);
        tvSagittal = findViewById(R.id.tvSagittal);
        tvCoronal = findViewById(R.id.tvCoronal);

        sliderContainer = findViewById(R.id.sliderContainer);
        sliderThumb = findViewById(R.id.sliderThumb);

        setupToolbar();
        handleIntentData();
        setupTabSelection();
        setupZoomSlider();

        View btnExtractTissue = findViewById(R.id.btnExtractTissue);
        if (btnExtractTissue != null) {
            btnExtractTissue.setOnClickListener(v -> {
                triggerAnalysis();
            });
        }

        // Initial state
        updateMode("Axial");
    }

    private void handleIntentData() {
        String fileUriString = getIntent().getStringExtra("FILE_URI");
        if (fileUriString != null) {
            Uri fileUri = Uri.parse(fileUriString);
            if (ivDicomImage != null) {
                // Use Glide for robust loading of local URIs and Network URLs
                Glide.with(this)
                     .load(fileUri)
                     .placeholder(R.drawable.ic_brain) 
                     .error(R.drawable.ic_warning_outline)
                     .into(ivDicomImage);
                ivDicomImage.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setupTabSelection() {
        cardAxial.setOnClickListener(v -> updateMode("Axial"));
        cardSagittal.setOnClickListener(v -> updateMode("Sagittal"));
        cardCoronal.setOnClickListener(v -> updateMode("Coronal"));
    }

    private void setupZoomSlider() {
        if (sliderContainer == null || sliderThumb == null)
            return;

        sliderContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float y = event.getY();
                float height = v.getHeight();

                // Constrain y within container bounds
                if (y < 0)
                    y = 0;
                if (y > height)
                    y = height;

                // Move the thumb
                // Center the thumb horizontally and position it vertically at touch point
                // Note: sliderThumb is inside sliderContainer which is a FrameLayout/CardView
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) sliderThumb.getLayoutParams();
                params.topMargin = (int) (y - (sliderThumb.getHeight() / 2f));

                // Keep thumb within container
                if (params.topMargin < 0)
                    params.topMargin = 0;
                if (params.topMargin > height - sliderThumb.getHeight())
                    params.topMargin = (int) (height - sliderThumb.getHeight());

                sliderThumb.setLayoutParams(params);

                // Calculate zoom level based on thumb position
                // Top is zoom in (MAX_SCALE), bottom is zoom out (MIN_SCALE)
                float percentage = 1.0f - (y / height); // 1.0 at top, 0.0 at bottom
                currentScale = MIN_SCALE + (percentage * (MAX_SCALE - MIN_SCALE));

                updateImageZoom();

                return true;
            }
        });
    }

    private void updateImageZoom() {
        if (ivDicomImage != null) {
            ivDicomImage.setScaleX(currentScale);
            ivDicomImage.setScaleY(currentScale);
        }
    }

    private void updateMode(String mode) {
        tvViewerTitle.setText("Medical Imaging Viewer (" + mode + ")");
        resetUI();
        if (mode.equals("Axial")) {
            setSelectedTab(cardAxial, tvAxial);
            setOverlayColor("#3B82F6");
            tvSliceLabel.setText("DICOM Render");
        } else if (mode.equals("Sagittal")) {
            setSelectedTab(cardSagittal, tvSagittal);
            setOverlayColor("#10B981");
            tvSliceLabel.setText("Sagittal Slice");
        } else if (mode.equals("Coronal")) {
            setSelectedTab(cardCoronal, tvCoronal);
            setOverlayColor("#EF4444");
            tvSliceLabel.setText("Coronal Slice");
        }
    }

    private void setOverlayColor(String colorCode) {
        int color = Color.parseColor(colorCode);
        crosshairH.setBackgroundColor(color);
        crosshairV.setBackgroundColor(color);
        focusCircle.setStrokeColor(color);
        tvSliceLabel.setTextColor(color);
    }

    private void resetUI() {
        setUnselectedTab(cardAxial, tvAxial);
        setUnselectedTab(cardSagittal, tvSagittal);
        setUnselectedTab(cardCoronal, tvCoronal);
    }

    private void setSelectedTab(MaterialCardView card, TextView text) {
        card.setCardBackgroundColor(Color.parseColor("#2563EB"));
        text.setTextColor(Color.WHITE);
        text.setTypeface(null, android.graphics.Typeface.BOLD);
    }

    private void setUnselectedTab(MaterialCardView card, TextView text) {
        card.setCardBackgroundColor(Color.TRANSPARENT);
        text.setTextColor(Color.parseColor("#475569"));
        text.setTypeface(null, android.graphics.Typeface.NORMAL);
    }

    private void setupToolbar() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void triggerAnalysis() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Analyzing Medical Imaging...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        int caseId = CaseData.getInstance().id;
        ApiService apiService = RetrofitClient.getRetrofitInstance(this).create(ApiService.class);
        apiService.tissueAnalysis(caseId).enqueue(new Callback<PredictionResponse>() {
            @Override
            public void onResponse(Call<PredictionResponse> call, Response<PredictionResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    CaseData data = response.body().caseData;
                    if (data != null) {
                        CaseData.getInstance().copyFrom(data);
                        
                        // Trigger local notification
                        LocalNotificationManager.getInstance(NewCaseTenActivity.this).addNotification(new Notification(
                                "Risk Assessment Complete",
                                "AI analysis for " + data.patientName + " (#" + data.patientId + ") is ready.",
                                "Just now",
                                "SUCCESS"
                        ));

                        Intent intent = new Intent(NewCaseTenActivity.this, NewCaseElevenActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(NewCaseTenActivity.this, "Analysis failed: No data returned",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NewCaseTenActivity.this, "Analysis failed: " + response.message(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PredictionResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NewCaseTenActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
