package com.wallpapers.unsplash;

import retrofit2.Retrofit;

/**
 * Created by hoanghiep on 1/17/18.
 */

public interface FrameworkInterface {
    Retrofit getRetrofitApiInstance();

    Retrofit getRetrofitApiRootInstance();
}
