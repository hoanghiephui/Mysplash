package com.wallpapers.unsplash.common.data.service;

import com.google.gson.GsonBuilder;
import com.wallpapers.unsplash.Unsplash;
import com.wallpapers.unsplash.common.data.BaseOkHttpClient;
import com.wallpapers.unsplash.common.data.api.PhotoInfoApi;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.utils.widget.interceptor.AuthInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Photo info service.
 * */

public class PhotoInfoService {

    private Call call;

    public static PhotoInfoService getService() {
        return new PhotoInfoService();
    }

    private OkHttpClient buildClient() {
        return new BaseOkHttpClient().invoke()
                .addInterceptor(new AuthInterceptor())
                .build();
    }

    private PhotoInfoApi buildApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Unsplash.UNSPLASH_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .setDateFormat(Unsplash.DATE_FORMAT)
                                        .create()))
                .build()
                .create((PhotoInfoApi.class));
    }

    public void requestAPhoto(String id, final OnRequestSinglePhotoListener l) {
        Call<Photo> getPhotoInfo = buildApi(buildClient()).getPhotoInfo(id);
        getPhotoInfo.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if (l != null) {
                    l.onRequestSinglePhotoSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                if (l != null) {
                    l.onRequestSinglePhotoFailed(call, t);
                }
            }
        });
        call = getPhotoInfo;
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }

    // interface.

    public interface OnRequestSinglePhotoListener {
        void onRequestSinglePhotoSuccess(Call<Photo> call, retrofit2.Response<Photo> response);
        void onRequestSinglePhotoFailed(Call<Photo> call, Throwable t);
    }
}