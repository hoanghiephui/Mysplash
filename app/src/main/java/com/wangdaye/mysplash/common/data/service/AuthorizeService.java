package com.wangdaye.mysplash.common.data.service;

import android.content.Context;

import com.wangdaye.mysplash.Mysplash;
import com.wangdaye.mysplash.common.data.BaseOkHttpClient;
import com.wangdaye.mysplash.common.data.api.AuthorizeApi;
import com.wangdaye.mysplash.common.data.entity.unsplash.AccessToken;
import com.wangdaye.mysplash.common.utils.widget.interceptor.LogInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Authorize service.
 * */

public class AuthorizeService {

    private Call call;

    public static AuthorizeService getService() {
        return new AuthorizeService();
    }

    private OkHttpClient buildClient() {
        return new BaseOkHttpClient().invoke()
                .build();
    }
    private AuthorizeApi buildApi() {
        return new Retrofit.Builder()
                .baseUrl(Mysplash.UNSPLASH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildClient())
                .build()
                .create((AuthorizeApi.class));
    }

    public void requestAccessToken(Context c, String code, final OnRequestAccessTokenListener l) {
        Call<AccessToken> getAccessToken = buildApi()
                .getAccessToken(
                        Mysplash.getAppId(c, true),
                        Mysplash.getSecret(c),
                        "livingphoto://" + Mysplash.UNSPLASH_LOGIN_CALLBACK,
                        code,
                        "authorization_code");
        getAccessToken.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (l != null) {
                    l.onRequestAccessTokenSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                if (l != null) {
                    l.onRequestAccessTokenFailed(call, t);
                }
            }
        });
        call = getAccessToken;
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }

    // interface.

    public interface OnRequestAccessTokenListener {
        void onRequestAccessTokenSuccess(Call<AccessToken> call, retrofit2.Response<AccessToken> response);
        void onRequestAccessTokenFailed(Call<AccessToken> call, Throwable t);
    }
}
