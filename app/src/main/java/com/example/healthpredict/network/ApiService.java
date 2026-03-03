package com.example.healthpredict.network;

import com.example.healthpredict.CaseData;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/signup/")
    Call<AuthResponse> signup(@Body RegisterRequest request);

    @POST("api/login/")
    Call<AuthResponse> login(@Body LoginRequest request);

    @GET("api/cases/")
    Call<List<CaseData>> getCases();

    @POST("api/cases/")
    Call<CaseData> createCase(@Body CaseData caseData);
}
