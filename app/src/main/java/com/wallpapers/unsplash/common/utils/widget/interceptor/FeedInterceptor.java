package com.wallpapers.unsplash.common.utils.widget.interceptor;

import com.wallpapers.unsplash.BuildConfig;
import com.wallpapers.unsplash.common.data.service.FeedService;
import com.wallpapers.unsplash.common.utils.manager.AuthManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Feed interceptor.
 *
 * A interceptor for {@link retrofit2.Retrofit}, it can add authorization information into
 * HTTP request header for {@link FeedService}.
 *
 * */

public class FeedInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request;
        if (AuthManager.getInstance().isAuthorized()) {
            request = chain.request()
                    .newBuilder()
                    .addHeader("x-unsplash-client", "web")
                    .addHeader("accept-version", "v1")
                    .addHeader("Authorization", "Bearer " + "b3b878c011d594f25e5d8ea28e1b446af2d63ea838360875779e9014f96de564")
                    .addHeader("Accept", "*/*")
                    .addHeader("Referer", "https://unsplash.com/following?onboarding=true")
                    .build();
        } else {
            request = chain.request()
                    .newBuilder()
                    .addHeader("x-unsplash-client", "web")
                    .addHeader("accept-version", "v1")
                    //.addHeader("Authorization", "Bearer " + BuildConfig.FEED_TOKEN)
                    .addHeader("Authorization", "Client-ID " + BuildConfig.FEED_TOKEN)
                    .addHeader("Accept", "*/*")
                    .addHeader("Referer", "https://unsplash.com/following?onboarding=true")
                    .build();
        }

        return chain.proceed(request);
    }
}
