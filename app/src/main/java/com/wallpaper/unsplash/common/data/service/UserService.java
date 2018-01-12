package com.wallpaper.unsplash.common.data.service;

import com.google.gson.GsonBuilder;
import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.common.data.BaseOkHttpClient;
import com.wallpaper.unsplash.common.data.api.UserApi;
import com.wallpaper.unsplash.common.data.entity.unsplash.Me;
import com.wallpaper.unsplash.common.data.entity.unsplash.User;
import com.wallpaper.unsplash.common.data.entity.unsplash.Users;
import com.wallpaper.unsplash.common.utils.widget.interceptor.AuthInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * User service.
 */

public class UserService {

    private Call call;

    public static UserService getService() {
        return new UserService();
    }

    private OkHttpClient buildClient() {
        return new BaseOkHttpClient().invoke()
                .addInterceptor(new AuthInterceptor())
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    private UserApi buildApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(UnsplashApplication.UNSPLASH_API_BASE_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .setDateFormat(UnsplashApplication.DATE_FORMAT)
                                        .create()))
                .build()
                .create((UserApi.class));
    }

    private UserApi buildApiFull(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(UnsplashApplication.UNSPLASH_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .setDateFormat(UnsplashApplication.DATE_FORMAT)
                                        .create()))
                .build()
                .create((UserApi.class));
    }

    public void requestUserProfile(String username, final OnRequestUserProfileListener l) {
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

    public void requestMeProfile(final OnRequestMeProfileListener l) {
        Call<Me> getMeProfile = buildApi(buildClient()).getMeProfile();
        getMeProfile.enqueue(new Callback<Me>() {
            @Override
            public void onResponse(Call<Me> call, Response<Me> response) {
                if (l != null) {
                    l.onRequestMeProfileSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Me> call, Throwable t) {
                if (l != null) {
                    l.onRequestMeProfileFailed(call, t);
                }
            }
        });
        call = getMeProfile;
    }

    public void updateMeProfile(String username, String first_name, String last_name,
                                String email, String url, String location, String bio,
                                final OnRequestMeProfileListener l) {
        Call<Me> updateMeProfile = buildApi(buildClient()).updateMeProfile(
                username, first_name, last_name,
                email, url, location, bio);
        updateMeProfile.enqueue(new Callback<Me>() {
            @Override
            public void onResponse(Call<Me> call, Response<Me> response) {
                if (l != null) {
                    l.onRequestMeProfileSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Me> call, Throwable t) {
                if (l != null) {
                    l.onRequestMeProfileFailed(call, t);
                }
            }
        });
        call = updateMeProfile;
    }

    public void requestFollowers(String username, int page, int perPage, final OnRequestUsersListener l) {
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

    public void requestFollowing(String username, int page, int perPage, final OnRequestUsersListener l) {
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

    /**
     * @param query
     * @param perPage
     * @param page
     */
    public void requestUserByQuery(String query,
                                   @UnsplashApplication.PerPageRule int perPage,
                                   @UnsplashApplication.PageRule int page,
                                   final OnRequestUsersByQueryListener listener) {
        Call<Users> resultUsers = buildApiFull(buildClient())
                .getListUserByQuery(query, perPage, page);
        resultUsers.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (listener != null) {
                    listener.onRequestPhotosSuccesss(call, response);
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                if (listener != null) {
                    listener.onRequestPhotosFailedd(call, t);
                }
            }
        });
        call = resultUsers;
    }

    // interface.

    public interface OnRequestUserProfileListener {
        void onRequestUserProfileSuccess(Call<User> call, Response<User> response);

        void onRequestUserProfileFailed(Call<User> call, Throwable t);
    }

    public interface OnRequestMeProfileListener {
        void onRequestMeProfileSuccess(Call<Me> call, Response<Me> response);

        void onRequestMeProfileFailed(Call<Me> call, Throwable t);
    }

    public interface OnRequestUsersListener {
        void onRequestUsersSuccess(Call<List<User>> call, Response<List<User>> response);

        void onRequestUsersFailed(Call<List<User>> call, Throwable t);
    }

    public interface OnRequestUsersByQueryListener {
        void onRequestPhotosSuccesss(Call<Users> call, retrofit2.Response<Users> response);

        void onRequestPhotosFailedd(Call<Users> call, Throwable t);
    }
}
