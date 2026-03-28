package com.example.healthpredict.network;

import android.content.Context;
import android.content.SharedPreferences;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import android.util.Log;

public class RetrofitClient {
    private static String getBaseUrl(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("HealthPredictPrefs", Context.MODE_PRIVATE);
        // Using Android Emulator 10.0.2.2 as the default base URL
        String defaultUrl = "https://jjgqvztm-8000.inc1.devtunnels.ms/" +
                "";
        String baseUrl = prefs.getString("server_url", defaultUrl);

        // Safeguard: If it's an old IP-based URL or incorrect tunnel, reset to default
        if (baseUrl.contains("10.") || baseUrl.contains("172.") || !baseUrl.contains("inc1.devtunnels.ms")) {
            baseUrl = defaultUrl;
            prefs.edit().putString("server_url", baseUrl).apply();
        }

        return baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

    private static Retrofit retrofit = null;
    private static String currentBaseUrl = null;

    public static Retrofit getRetrofitInstance(Context context) {
        String baseUrl = getBaseUrl(context);
        Log.d("RetrofitClient", "Using Base URL: " + baseUrl);

        if (retrofit == null || !baseUrl.equals(currentBaseUrl)) {
            currentBaseUrl = baseUrl;
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(
                                    "HealthPredictPrefs",
                                    Context.MODE_PRIVATE);
                            String token = prefs.getString("access_token", null);

                            Request.Builder builder = chain.request().newBuilder();
                            if (token != null) {
                                builder.addHeader("Authorization", "Bearer " + token);
                            }
                            return chain.proceed(builder.build());
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService(Context context) {
        return getRetrofitInstance(context).create(ApiService.class);
    }

    public static void resetRetrofitInstance() {
        retrofit = null;
    }
}
