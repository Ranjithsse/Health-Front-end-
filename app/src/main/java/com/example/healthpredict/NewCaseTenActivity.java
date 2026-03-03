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
                Intent intent = new Intent(NewCaseTenActivity.this, NewCaseElevenActivity.class);
                startActivity(intent);
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
                ivDicomImage.setImageURI(fileUri);
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
        if (sliderContainer == null || sliderThumb == null) return;

        sliderContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float y = event.getY();
                float height = v.getHeight();

                // Constrain y within container bounds
                if (y < 0) y = 0;
                if (y > height) y = height;

                // Move the thumb
                // Center the thumb horizontally and position it vertically at touch point
                // Note: sliderThumb is inside sliderContainer which is a FrameLayout/CardView
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) sliderThumb.getLayoutParams();
                params.topMargin = (int) (y - (sliderThumb.getHeight() / 2f));
                
                // Keep thumb within container
                if (params.topMargin < 0) params.topMargin = 0;
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
}
