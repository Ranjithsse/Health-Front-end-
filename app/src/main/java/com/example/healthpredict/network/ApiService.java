package com.example.healthpredict.network;

import com.example.healthpredict.CaseData;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("api/settings/")
    Call<java.util.Map<String, Object>> getSettings();

    @retrofit2.http.PATCH("api/settings/update/")
    Call<java.util.Map<String, Object>> updateSettings(@Body java.util.Map<String, Object> settings);

    @POST("api/signup/")
    Call<AuthResponse> signup(@Body RegisterRequest request);

    @POST("api/login/")
    Call<AuthResponse> login(@Body LoginRequest request);

    @GET("api/cases/")
    Call<List<CaseData>> getCases(@retrofit2.http.Query("status") String status);

    @GET("api/cases/{id}/")
    Call<CaseData> getCase(@retrofit2.http.Path("id") int id);

    @POST("api/cases/")
    Call<CaseData> createCase(@Body CaseData caseData);

    @retrofit2.http.PATCH("api/cases/{id}/")
    Call<CaseData> updateCase(@retrofit2.http.Path("id") int id, @Body java.util.Map<String, Object> updates);

    @POST("api/cases/{id}/predict/")
    Call<CaseData> predictCase(@retrofit2.http.Path("id") int id);

    @POST("api/cases/{id}/tissue-analysis/")
    Call<CaseData> tissueAnalysis(@retrofit2.http.Path("id") int id);

    @GET("api/explainability/{case_id}/")
    Call<java.util.Map<String, Object>> getExplainability(@retrofit2.http.Path("case_id") int caseId);

    @GET("api/dashboard-stats/")
    Call<java.util.Map<String, Object>> getDashboardStats();

    @GET("api/notifications/")
    Call<List<com.example.healthpredict.Notification>> getNotifications();

    @GET("api/config/")
    Call<java.util.Map<String, Object>> getAppConfig();

    @GET("api/auth/check/")
    Call<java.util.Map<String, Object>> checkSession();

    @GET("api/my-achievements/")
    Call<List<java.util.Map<String, Object>>> getAchievements();

    @POST("api/password-reset-request/")
    Call<java.util.Map<String, String>> requestPasswordReset(@Body java.util.Map<String, String> body);

    @POST("api/password-reset-confirm/")
    Call<java.util.Map<String, String>> confirmPasswordReset(@Body java.util.Map<String, String> body);
}
