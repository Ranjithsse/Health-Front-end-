package com.example.healthpredict;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.google.android.material.button.MaterialButton;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DownloadReportActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "download_channel";
    private static final int NOTIFICATION_ID = 1;
    
    private String pendingPatientName;
    private Uri pendingPdfUri;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    if (pendingPatientName != null && pendingPdfUri != null) {
                        showDownloadNotification(pendingPatientName, pendingPdfUri);
                    }
                } else {
                    Toast.makeText(this, "Notification permission denied. You won't see the download status in notifications.", Toast.LENGTH_LONG).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_report);
        setupBottomNavigation();

        createNotificationChannel();

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }

        findViewById(R.id.btnCancel).setOnClickListener(v -> finish());

        CaseData data = CaseData.getInstance();
        final String patientName = data.patientName.isEmpty() ? "Robert Wilson" : data.patientName;
        final String patientId = data.patientId.isEmpty() ? "1024" : data.patientId;

        TextView tvPatientDetailHeader = findViewById(R.id.tvPatientDetailHeader);
        if (tvPatientDetailHeader != null) {
            tvPatientDetailHeader.setText(String.format("Patient #%s - %s", patientId, patientName));
        }

        MaterialButton btnDownloadPdf = findViewById(R.id.btnDownloadPdf);
        if (btnDownloadPdf != null) {
            btnDownloadPdf.setOnClickListener(v -> {
                Toast.makeText(this, "Generating and downloading report...", Toast.LENGTH_SHORT).show();
                
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    Uri pdfUri = generateAndSavePdf();
                    if (pdfUri != null) {
                        checkPermissionAndShowNotification(patientName, pdfUri);
                        Toast.makeText(this, "Report saved to Downloads", Toast.LENGTH_LONG).show();
                        
                        Intent intent = new Intent(DownloadReportActivity.this, DownloadCompleteActivity.class);
                        intent.putExtra("PDF_URI", pdfUri.toString());
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Failed to download report. Check storage space.", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            });
        }
    }

    private void checkPermissionAndShowNotification(String patientName, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                showDownloadNotification(patientName, uri);
            } else {
                pendingPatientName = patientName;
                pendingPdfUri = uri;
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        } else {
            showDownloadNotification(patientName, uri);
        }
    }

    private Uri generateAndSavePdf() {
        CaseData data = CaseData.getInstance();
        String name = data.patientName.isEmpty() ? "Robert Wilson" : data.patientName;
        String id = data.patientId.isEmpty() ? "1024" : data.patientId;

        // Inflate the PDF layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View pdfView = inflater.inflate(R.layout.layout_pdf_report, null);

        // Bind data
        TextView tvPatientIdHeaderPdf = pdfView.findViewById(R.id.tvPatientIdHeaderPdf);
        TextView tvReportDatePdf = pdfView.findViewById(R.id.tvReportDatePdf);
        TextView tvValueNamePdf = pdfView.findViewById(R.id.tvValueNamePdf);
        TextView tvValueAgePdf = pdfView.findViewById(R.id.tvValueAgePdf);
        TextView tvValueGenderPdf = pdfView.findViewById(R.id.tvValueGenderPdf);
        TextView tvPredictionTextPdf = pdfView.findViewById(R.id.tvPredictionTextPdf);
        TextView tvRiskLevelPdf = pdfView.findViewById(R.id.tvRiskLevelPdf);
        TextView tvProtocolPdf = pdfView.findViewById(R.id.tvProtocolPdf);
        TextView tvInterventionPdf = pdfView.findViewById(R.id.tvInterventionPdf);
        TextView tvMonitoringPdf = pdfView.findViewById(R.id.tvMonitoringPdf);
        TextView tvProviderNotesPdf = pdfView.findViewById(R.id.tvProviderNotesPdf);

        tvPatientIdHeaderPdf.setText("PATIENT #" + id);
        tvReportDatePdf.setText(data.date.isEmpty() ? java.text.DateFormat.getDateInstance().format(new java.util.Date()) : data.date);
        tvValueNamePdf.setText(name);
        tvValueAgePdf.setText(data.age.isEmpty() ? "N/A" : data.age);
        tvValueGenderPdf.setText(data.gender.isEmpty() ? "N/A" : data.gender);
        tvPredictionTextPdf.setText(data.oneYearPrediction + " Stability Probability\n(1-Year)");
        tvRiskLevelPdf.setText("Risk Level: " + (data.oneYearRisk.isEmpty() ? "Low" : data.oneYearRisk));
        tvProtocolPdf.setText("Protocol: " + (data.primaryMedication.isEmpty() ? "ACE Inhibitors" : data.primaryMedication));
        tvInterventionPdf.setText("Intervention: " + (data.interventionType.isEmpty() ? "Non-Invasive" : data.interventionType));
        tvMonitoringPdf.setText("Monitoring: " + (data.monitoringLevel.isEmpty() ? "Standard" : data.monitoringLevel));
        tvProviderNotesPdf.setText(data.providerNotes.isEmpty() ? "No notes added." : data.providerNotes);

        // Use device screen width so dp and sp scale proportionately 
        android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int pageWidth = displayMetrics.widthPixels;

        // Measure layout with AT_MOST height to get the actual required height
        pdfView.measure(
                View.MeasureSpec.makeMeasureSpec(pageWidth, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        
        int pageHeight = pdfView.getMeasuredHeight();
        // Add a little bottom padding
        pageHeight += 40; 
        
        // Safety check: ensure height at least somewhat proportional to A4 if content is tiny
        int minHeight = (int) (pageWidth * 1.414f);
        if (pageHeight < minHeight) {
            pageHeight = minHeight;
        }

        // Layout the view with its measured height
        pdfView.layout(0, 0, pageWidth, pageHeight);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        pdfView.draw(canvas);
        document.finishPage(page);

        String fileName = "HealthReport_" + id + "_" + System.currentTimeMillis() + ".pdf";
        Uri uri = null;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
                if (uri != null) {
                    try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
                        if (outputStream != null) {
                            document.writeTo(outputStream);
                        }
                    }
                }
            } else {
                File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                File file = new File(directory, fileName);
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    document.writeTo(fos);
                }
                MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, null, null);
                uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
            }
        } catch (IOException e) {
            e.printStackTrace();
            uri = null;
        } finally {
            document.close();
        }
        
        return uri;
    }

    private void showDownloadNotification(String patientName, Uri uri) {
        CaseData data = CaseData.getInstance();
        String patientId = data.patientId.isEmpty() ? "1024" : data.patientId;

        // Trigger local app notification
        LocalNotificationManager.getInstance(this).addNotification(new Notification(
                "Health Report Ready",
                "PDF report for " + patientName + " (#" + patientId + ") is available for download.",
                "Just now",
                "INFO"
        ));

        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
        viewIntent.setDataAndType(uri, "application/pdf");
        viewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, viewIntent, 
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_download)
                .setContentTitle("Download Complete")
                .setContentText("Report for " + patientName + " is ready to view.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Download Channel";
            String description = "Channel for download notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void setupBottomNavigation() {
        android.view.View navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, DoctorHomeActivity.class);
                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP | android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }

        android.view.View navCases = findViewById(R.id.navCases);
        if (navCases != null) {
            navCases.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, DoctorCasesActivity.class);
                startActivity(intent);
                finish();
            });
        }

        android.view.View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, ReportsActivity.class);
                startActivity(intent);
                finish();
            });
        }

        android.view.View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, DoctorProfileActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }

}
