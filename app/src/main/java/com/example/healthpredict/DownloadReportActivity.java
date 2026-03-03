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

        createNotificationChannel();

        ImageView ivBack = findViewById(R.id.ivBack);
        if (ivBack != null) {
            ivBack.setOnClickListener(v -> finish());
        }

        findViewById(R.id.btnCancel).setOnClickListener(v -> finish());

        TextView tvPatientDetailHeader = findViewById(R.id.tvPatientDetailHeader);
        String name = getIntent().getStringExtra("PATIENT_NAME");
        String id = getIntent().getStringExtra("PATIENT_ID");

        if (name == null) name = "Robert Wilson";
        if (id == null) id = "1024";

        if (tvPatientDetailHeader != null) {
            tvPatientDetailHeader.setText(String.format("Patient #%s - %s", id, name));
        }

        final String patientName = name;
        final String patientId = id;

        MaterialButton btnDownloadPdf = findViewById(R.id.btnDownloadPdf);
        if (btnDownloadPdf != null) {
            btnDownloadPdf.setOnClickListener(v -> {
                Toast.makeText(this, "Generating and downloading report...", Toast.LENGTH_SHORT).show();
                
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    Uri pdfUri = generateAndSavePdf(patientName, patientId);
                    if (pdfUri != null) {
                        checkPermissionAndShowNotification(patientName, pdfUri);
                        Toast.makeText(this, "Report saved to Downloads", Toast.LENGTH_LONG).show();
                        
                        Intent intent = new Intent(DownloadReportActivity.this, DownloadCompleteActivity.class);
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

    private Uri generateAndSavePdf(String name, String id) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        paint.setTextSize(16f);
        paint.setFakeBoldText(true);
        canvas.drawText("HEALTH PREDICT REPORT", 10, 30, paint);

        paint.setFakeBoldText(false);
        paint.setTextSize(12f);
        canvas.drawText("Patient ID: " + id, 10, 60, paint);
        canvas.drawText("Patient Name: " + name, 10, 80, paint);
        canvas.drawText("Date: " + java.text.DateFormat.getDateTimeInstance().format(new java.util.Date()), 10, 100, paint);
        
        canvas.drawText("Summary:", 10, 140, paint);
        canvas.drawText("- AI Stability Prediction: 98.2%", 20, 160, paint);
        canvas.drawText("- Risk Level: Low", 20, 180, paint);
        canvas.drawText("- Recommended Protocol: ACE Inhibitors", 20, 200, paint);

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
}
