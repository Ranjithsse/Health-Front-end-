package com.example.healthpredict;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthpredict.network.ApiService;
import com.example.healthpredict.network.PredictionResponse;
import com.example.healthpredict.network.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.example.healthpredict.utils.FileUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import java.io.File;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewCaseNineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case_nine);

        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView btnClose = findViewById(R.id.btnClose);
        MaterialButton btnUploadAnalyze = findViewById(R.id.btnUploadAnalyze);
        TextView tvFileName = findViewById(R.id.tvFileName);
        TextView tvFileSize = findViewById(R.id.tvFileSize);

        // Load and display file info
        displayFileInfo(tvFileName, tvFileSize);

        btnBack.setOnClickListener(v -> finish());

        if (btnClose != null) {
            btnClose.setOnClickListener(v -> {
                // Navigate back to selection
                finish();
            });
        }

        btnUploadAnalyze.setOnClickListener(v -> {
            uploadAndCreateCase();
        });
    }

    private void uploadAndCreateCase() {
        CaseData data = CaseData.getInstance();

        // If case already exists on server, we still need to upload and validate the image
        if (data.id > 0) {
            uploadImageFile(data.id);
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating case and preparing upload...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiService apiService = RetrofitClient.getRetrofitInstance(this).create(ApiService.class);

        apiService.createCase(data).enqueue(new Callback<CaseData>() {
            @Override
            public void onResponse(Call<CaseData> call, Response<CaseData> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    // Update singleton with valid ID from server
                    CaseData.getInstance().id = response.body().id;
 
                    // Update persistent monthly statistics for dashboard
                    SharedPreferences userPrefs = getSharedPreferences("HealthPredictPrefs", MODE_PRIVATE);
                    String userEmail = userPrefs.getString("user_email", "anonymous");
                    StatsManager.getInstance().incrementMonthlyCount(NewCaseNineActivity.this, userEmail);
 
                    // Proceed to upload image
                    uploadImageFile(response.body().id);
                } else {
                    String errorMsg = "Bad Request";
                    try {
                        if (response.errorBody() != null) {
                            errorMsg = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(NewCaseNineActivity.this, "Failed to create case: " + errorMsg, Toast.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<CaseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NewCaseNineActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayFileInfo(TextView tvName, TextView tvSize) {
        String uriStr = CaseData.getInstance().fileUri;
        if (uriStr == null || uriStr.isEmpty())
            return;

        Uri uri = Uri.parse(uriStr);
        String fileName = "Unknown file";
        String fileSizeStr = "Unknown size";

        try {
            android.database.Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME);
                int sizeIndex = cursor.getColumnIndex(android.provider.OpenableColumns.SIZE);

                if (nameIndex != -1)
                    fileName = cursor.getString(nameIndex);
                if (sizeIndex != -1) {
                    long size = cursor.getLong(sizeIndex);
                    fileSizeStr = formatFileSize(size) + " • Ready to upload";
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (tvName != null)
            tvName.setText(fileName);
        if (tvSize != null)
            tvSize.setText(fileSizeStr);
    }

    private void uploadImageFile(int caseId) {
        String uriStr = CaseData.getInstance().fileUri;
        if (uriStr == null || uriStr.isEmpty()) {
            Toast.makeText(this, "No image selected for analysis", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = FileUtils.getFileFromUri(this, Uri.parse(uriStr));
        if (file == null) {
            Toast.makeText(this, "Failed to process image file", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog uploadProgress = new ProgressDialog(this);
        uploadProgress.setMessage("Uploading medical imaging...");
        uploadProgress.setCancelable(false);
        uploadProgress.show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        ApiService apiService = RetrofitClient.getRetrofitInstance(this).create(ApiService.class);
        apiService.uploadImage(caseId, body).enqueue(new Callback<PredictionResponse>() {
            @Override
            public void onResponse(Call<PredictionResponse> call, Response<PredictionResponse> response) {
                uploadProgress.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    // Update file URI in singleton with server path
                    if (response.body().caseData != null) {
                        CaseData.getInstance().fileUri = response.body().caseData.fileUri;
                    }
                    
                    Intent intent = new Intent(NewCaseNineActivity.this, NewCaseTenActivity.class);
                    // Pass the local URI for immediate viewing on first load
                    intent.putExtra("FILE_URI", uriStr);
                    startActivity(intent);
                    finish();
                } else {
                    // Stay on current page and show descriptive error from server
                    String errorMessage = "This image cannot be analyzed.";
                    try {
                        if (response.errorBody() != null) {
                            String errorJson = response.errorBody().string();
                            // Parse {"error": "message"}
                            org.json.JSONObject obj = new org.json.JSONObject(errorJson);
                            if (obj.has("error")) {
                                errorMessage = obj.getString("error");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    Toast.makeText(NewCaseNineActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    // Do NOT navigate to the next page
                }
            }

            @Override
            public void onFailure(Call<PredictionResponse> call, Throwable t) {
                uploadProgress.dismiss();
                Toast.makeText(NewCaseNineActivity.this, "Upload error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                // Do NOT navigate to the next page
            }
        });
    }

    private String formatFileSize(long size) {
        if (size <= 0)
            return "0 B";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new java.text.DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " "
                + units[digitGroups];
    }
}
