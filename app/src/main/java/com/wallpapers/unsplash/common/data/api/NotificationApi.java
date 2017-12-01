package com.wallpapers.unsplash.common.data.api;

import com.wallpapers.unsplash.Unsplash;
import com.wallpapers.unsplash.common.data.entity.unsplash.NotificationFeed;

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
    @POST(Unsplash.UNSPLASH_NOTIFICATION_URL)
    Call<NotificationFeed> getNotification(@Body RequestBody body);
}
