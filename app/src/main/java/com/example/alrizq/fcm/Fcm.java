package com.example.alrizq.fcm;

import androidx.annotation.NonNull;

import com.example.alrizq.utils.ApiClient;
import com.example.alrizq.utils.ApiService;
import com.example.alrizq.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fcm {
    public static void sendMessage(String body) {
        ApiClient.getClient().create(ApiService.class).sendRemoteMessage(
                Constant.getRemoteMessageHeaders(), body
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

            }
        });
    }
}
