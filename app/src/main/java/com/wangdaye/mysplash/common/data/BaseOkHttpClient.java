package com.wangdaye.mysplash.common.data;

import com.wangdaye.mysplash.common.utils.widget.interceptor.LogInterceptor;

import okhttp3.OkHttpClient;

/**
 * Created by hoanghiep on 11/24/17.
 */

public class BaseOkHttpClient {
    public OkHttpClient.Builder invoke() {
        return new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor().invoke());

    }
}
