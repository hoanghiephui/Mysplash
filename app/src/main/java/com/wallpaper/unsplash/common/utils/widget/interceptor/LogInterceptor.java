package com.wallpaper.unsplash.common.utils.widget.interceptor;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.wallpaper.unsplash.BuildConfig;

import okhttp3.internal.platform.Platform;

/**
 * Created by hoanghiep on 11/24/17.
 */

public class LogInterceptor {
    public LoggingInterceptor invoke() {
        return new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .addHeader("version", BuildConfig.VERSION_NAME)
                .build();
    }
}
