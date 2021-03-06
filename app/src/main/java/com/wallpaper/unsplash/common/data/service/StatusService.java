package com.wallpaper.unsplash.common.data.service;

import com.google.gson.GsonBuilder;
import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.common.data.BaseOkHttpClient;
import com.wallpaper.unsplash.common.data.api.StatusApi;
import com.wallpaper.unsplash.common.data.entity.unsplash.Total;
import com.wallpaper.unsplash.common.utils.widget.interceptor.AuthInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Status service.
 * */

public class StatusService {

    private Call call;

    public static StatusService getService() {
        return new StatusService();
    }

    private OkHttpClient buildClient() {
        return new BaseOkHttpClient().invoke()
                .addInterceptor(new AuthInterceptor())
                .build();
    }

    private StatusApi buildApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(UnsplashApplication.UNSPLASH_API_BASE_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .setDateFormat(UnsplashApplication.DATE_FORMAT)
                                        .create()))
                .build()
                .create((StatusApi.class));
    }

    public void requestTotal(final OnRequestTotalListener l) {
        Call<Total> getTotal = buildApi(buildClient()).getTotal();
        getTotal.enqueue(new Callback<Total>() {
            @Override
            public void onResponse(Call<Total> call, retrofit2.Response<Total> response) {
                if (l != null) {
                    l.onRequestTotalSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Total> call, Throwable t) {
                if (l != null) {
                    l.onRequestTotalFailed(call, t);
                }
            }
        });
        call = getTotal;
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }

    // interface.

    public interface OnRequestTotalListener {
        void onRequestTotalSuccess(Call<Total> call, retrofit2.Response<Total> response);
        void onRequestTotalFailed(Call<Total> call, Throwable t);
    }
}
