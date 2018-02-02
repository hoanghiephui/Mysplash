package com.wallpapers.unsplash.common.data.service;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.wallpapers.unsplash.common.data.BaseOkHttpClient;
import com.wallpapers.unsplash.common.data.api.UserNodeApi;
import com.wallpapers.unsplash.common.data.entity.unsplash.User;
import com.wallpapers.unsplash.common.utils.widget.interceptor.AuthInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.wallpapers.unsplash.UnsplashApplication.DATE_FORMAT;
import static com.wallpapers.unsplash.UnsplashApplication.UNSPLASH_API_BASE_URL;
import static com.wallpapers.unsplash.UnsplashApplication.UNSPLASH_NODE_API_URL;

/**
 * Created by hoanghiep on 1/15/18.
 */

public class UserNodeService {
    private Call call;

    static UserNodeService getService() {
        return TextUtils.isEmpty(UNSPLASH_NODE_API_URL) ? null : new UserNodeService();
    }

    private OkHttpClient buildClient() {
        return new BaseOkHttpClient().invoke()
                .addInterceptor(new AuthInterceptor())
                .build();
    }

    private UserNodeApi buildApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(UNSPLASH_API_BASE_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .setDateFormat(DATE_FORMAT)
                                        .create()))
                .build()
                .create((UserNodeApi.class));
    }

    void requestUserProfile(String username, final UserService.OnRequestUserProfileListener l) {
        Call<User> getUserProfile = buildApi(buildClient()).getUserProfile(username, 256, 256);
        getUserProfile.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (l != null) {
                    l.onRequestUserProfileSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (l != null) {
                    l.onRequestUserProfileFailed(call, t);
                }
            }
        });
        call = getUserProfile;
    }

    void requestFollowers(String username, int page, int perPage, final UserService.OnRequestUsersListener l) {
        Call<List<User>> requestFollowers = buildApi(buildClient()).getFollowers(username, page, perPage);
        requestFollowers.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (l != null) {
                    l.onRequestUsersSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                if (l != null) {
                    l.onRequestUsersFailed(call, t);
                }
            }
        });
        call = requestFollowers;
    }

    void requestFollowing(String username, int page, int perPage, final UserService.OnRequestUsersListener l) {
        Call<List<User>> requestFollowing = buildApi(buildClient()).getFolloweing(username, page, perPage);
        requestFollowing.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (l != null) {
                    l.onRequestUsersSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                if (l != null) {
                    l.onRequestUsersFailed(call, t);
                }
            }
        });
        call = requestFollowing;
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }
}
