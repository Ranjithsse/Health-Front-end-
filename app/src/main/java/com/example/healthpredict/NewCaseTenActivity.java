package com.example.healthpredict;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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

        setupToolbar();
        handleIntentData();
        setupTabSelection();

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
        // Retrieve the selected file URI from Step 8/9
        String fileUriString = getIntent().getStringExtra("FILE_URI");
        if (fileUriString != null) {
            Uri fileUri = Uri.parse(fileUriString);
            // Display the selected file (assuming it's an image for the viewer)
            if (ivDicomImage != null) {
                ivDicomImage.setImageURI(fileUri);
                ivDicomImage.setVisibility(View.VISIBLE);
                // We keep the mockup lines on top of the image as per design
            }
        }
    }

    private void setupTabSelection() {
        cardAxial.setOnClickListener(v -> updateMode("Axial"));
        cardSagittal.setOnClickListener(v -> updateMode("Sagittal"));
        cardCoronal.setOnClickListener(v -> updateMode("Coronal"));
    }

    private void updateMode(String mode) {
        // 1. Update Title and Labels
        tvViewerTitle.setText("Medical Imaging Viewer (" + mode + ")");
        
        // 2. Update Visual Overlays (Exact design colors from images)
        resetUI();
        if (mode.equals("Axial")) {
            setSelectedTab(cardAxial, tvAxial);
            setOverlayColor("#3B82F6"); // Blue for Axial
            tvSliceLabel.setText("DICOM Render");
        } else if (mode.equals("Sagittal")) {
            setSelectedTab(cardSagittal, tvSagittal);
            setOverlayColor("#10B981"); // Green for Sagittal
            tvSliceLabel.setText("Sagittal Slice");
        } else if (mode.equals("Coronal")) {
            setSelectedTab(cardCoronal, tvCoronal);
            setOverlayColor("#EF4444"); // Red for Coronal
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
