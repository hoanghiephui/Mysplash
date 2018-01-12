package com.wallpaper.unsplash.common.data.api;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.common.data.entity.unsplash.NotificationFeed;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Notification api.
 * */

public interface NotificationApi {

    @Headers("content-type: application/x-www-form-urlencoded")
    @POST(UnsplashApplication.UNSPLASH_NOTIFICATION_URL)
    Call<NotificationFeed> getNotification(@Body RequestBody body);
}
