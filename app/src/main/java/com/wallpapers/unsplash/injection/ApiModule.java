package com.wallpapers.unsplash.injection;

import android.util.Log;

import com.wallpapers.unsplash.TLSSocketFactory;
import com.wallpapers.unsplash.api.EnvironmentSettings;
import com.wallpapers.unsplash.common.utils.widget.interceptor.AuthInterceptor;
import com.wallpapers.unsplash.common.utils.widget.interceptor.LogInterceptor;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hoanghiep on 1/17/18.
 */

@Module
public class ApiModule {
    private static final String TAG = ApiModule.class.getSimpleName();
    private static final int API_TIMEOUT = 30;
    private static final int PING_INTERVAL = 10;

    @Provides
    @Singleton
    protected OkHttpClient provideOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(ConnectionSpec.MODERN_TLS))
                .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
                .pingInterval(PING_INTERVAL, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                // Add logging for debugging purposes
                .addInterceptor(new LogInterceptor().invoke())
                // Add header in all requests
                .addInterceptor(new AuthInterceptor());

        /*
          Enable TLS specific version V.1.2
          Issue Details : https://github.com/square/okhttp/issues/1934
         */
        try {
            TLSSocketFactory tlsSocketFactory = new TLSSocketFactory();
            builder.sslSocketFactory(tlsSocketFactory, tlsSocketFactory.systemDefaultTrustManager());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            Log.e(TAG, "Failed to create Socket connection ", e);
        }

        return builder.build();
    }

    @Provides
    @Singleton
    protected GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    protected RxJava2CallAdapterFactory provideRxJavaCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    @Named("api_root")
    protected Retrofit provideRetrofitApiRootInstance(OkHttpClient okHttpClient,
                                                      GsonConverterFactory converterFactory,
                                                      RxJava2CallAdapterFactory rxJavaCallFactory,
                                                      EnvironmentSettings environmentSettings) {

        return new Retrofit.Builder()
                .baseUrl(environmentSettings.getApiRootUrl())
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(rxJavaCallFactory)
                .build();
    }

    @Provides
    @Singleton
    @Named("api")
    protected Retrofit provideRetrofitApiInstance(OkHttpClient okHttpClient,
                                                  GsonConverterFactory converterFactory,
                                                  RxJava2CallAdapterFactory rxJavaCallFactory,
                                                  EnvironmentSettings environmentSettings) {

        return new Retrofit.Builder()
                .baseUrl(environmentSettings.getApiRootUrl())
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(rxJavaCallFactory)
                .build();
    }
}
