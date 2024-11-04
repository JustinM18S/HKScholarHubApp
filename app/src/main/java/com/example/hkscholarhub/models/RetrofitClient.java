package com.example.hkscholarhub.models;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:8000/";
    private static Retrofit retrofit = null;
    public static Retrofit getInstance(Context context) {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("TOKEN", null);
                Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(request);
            }).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
