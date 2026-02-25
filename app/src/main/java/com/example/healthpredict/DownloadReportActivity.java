package com.example.healthpredict;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class DownloadReportActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private CaseData caseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_report);

        caseData = CaseData.getInstance();

        TextView tvDetail = findViewById(R.id.tvPatientDetailHeader);
        if (tvDetail != null) {
            String patientInfo = "Patient #" + (caseData.patientId.isEmpty() ? "1024" : caseData.patientId) + 
                                " - " + (caseData.patientName.isEmpty() ? "Robert Wilson" : caseData.patientName);
            tvDetail.setText(patientInfo);
        }

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }

        View btnDownload = findViewById(R.id.btnDownloadPdf);
        if (btnDownload != null) {
            btnDownload.setOnClickListener(v -> {
                if (checkPermission()) {
                    downloadPdf();
                } else {
                    requestPermission();
                }
            });
        }

        View btnCancel = findViewById(R.id.btnCancel);
        if (btnCancel != null) {
            btnCancel.setOnClickListener(v -> finish());
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return true;
        }
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadPdf();
            } else {
                Toast.makeText(this, "Permission Denied to Save File", Toast.LENGTH_SHORT).show();
                // Navigate anyway for demonstration if requested, or stay on page
                // navigateToComplete(); 
            }
        }
    }

    private void downloadPdf() {
        try {
            String fileUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
            String fileName = "Assessment_Report_" + (caseData.patientId.isEmpty() ? "1024" : caseData.patientId) + ".pdf";

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
            request.setTitle(fileName);
            request.setDescription("Downloading patient report for " + caseData.patientName);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            if (manager != null) {
                manager.enqueue(request);
                Toast.makeText(this, "Download started...", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Download failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        
        // Always navigate to completion screen as per requirement
        navigateToComplete();
    }

    private void navigateToComplete() {
        Intent intent = new Intent(DownloadReportActivity.this, DownloadCompleteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        // finish(); // Optional: finish this activity so user can't go back to 'downloading' state
    }
}
