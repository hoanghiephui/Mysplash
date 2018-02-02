package com.wallpapers.unsplash;

import retrofit2.Retrofit;

/**
 * Created by hoanghiep on 1/17/18.
 */

public class UnsplashFramework {
    private static FrameworkInterface blockchainInterface;

    public static void init(FrameworkInterface frameworkInterface) {
        blockchainInterface = frameworkInterface;
    }

    public static Retrofit getRetrofitApiInstance() {
        return blockchainInterface.getRetrofitApiInstance();
    }

    public static Retrofit getRetrofitApiRootInstance() {
        return blockchainInterface.getRetrofitApiRootInstance();
    }
}
