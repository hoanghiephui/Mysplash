package com.wallpapers.unsplash.common.utils.widget.interceptor;

import com.wallpapers.unsplash.Unsplash;
import com.wallpapers.unsplash.common.utils.manager.AuthManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Auth interceptor.
 *
 * A interceptor for {@link retrofit2.Retrofit}, it can add authorization information into the
 * HTTP request header.
 *
 * */

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request;
        if (AuthManager.getInstance().isAuthorized()) {
            request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + AuthManager.getInstance().getAccessToken())
                    .build();
        } else {
            request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Client-ID " + Unsplash.getAppId(Unsplash.getInstance(), false))
                    .build();
        }
        return chain.proceed(request);
    }
}