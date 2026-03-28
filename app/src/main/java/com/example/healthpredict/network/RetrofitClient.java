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

public class RetrofitClient {
    private static String getBaseUrl(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("HealthPredictPrefs", Context.MODE_PRIVATE);
        String ip = prefs.getString("server_ip", "10.19.162.179");

        // Safeguard: If the IP is from an old session, update it to the new default
        if (ip.equals("10.187.32.85") || ip.equals("10.11.176.135") || ip.equals("172.29.245.123") || ip.equals("10.189.172.196") || ip.equals("10.189.172.64")) {
            ip = "10.19.162.179";
            prefs.edit().putString("server_ip", ip).apply();
        }

        return "http://" + ip + ":8000/";
    }

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {
            String baseUrl = getBaseUrl(context);

            OkHttpClient client = new OkHttpClient.Builder()
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
