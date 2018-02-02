package com.wallpapers.unsplash.common.utils.widget.interceptor;

import android.content.Context;

import com.wallpapers.unsplash.common.data.service.FeedService;
import com.wallpapers.unsplash.common.utils.PrefsUtils;
import com.wallpapers.unsplash.common.utils.manager.AuthManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Feed interceptor.
 * <p>
 * A interceptor for {@link retrofit2.Retrofit}, it can add authorization information into
 * HTTP request header for {@link FeedService}.
 */

public class FeedInterceptor implements Interceptor {
    private Context context;

    public FeedInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request;
        if (AuthManager.getInstance().isAuthorized()) {
            request = chain.request()
                    .newBuilder()
                    .addHeader("x-unsplash-client", "web")
                    .addHeader("accept-version", "v1")
                    .addHeader("Authorization", "Bearer " + PrefsUtils.Companion.getAuthenBearer(context))
                    .addHeader("Accept", "*/*")
                    .addHeader("Referer", "https://unsplash.com/following?onboarding=true")
                    .build();
        } else {
            request = chain.request()
                    .newBuilder()
                    .addHeader("x-unsplash-client", "web")
                    .addHeader("accept-version", "v1")
                    .addHeader("Authorization", "Client-ID " + PrefsUtils.Companion.getAuthenId(context))
                    .addHeader("Accept", "*/*")
                    .addHeader("Referer", "https://unsplash.com/following?onboarding=true")
                    .build();
        }

        return chain.proceed(request);
    }
}
