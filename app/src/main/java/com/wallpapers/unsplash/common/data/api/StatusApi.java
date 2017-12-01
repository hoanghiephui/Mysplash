package com.wallpapers.unsplash.common.data.api;

import com.wallpapers.unsplash.common.data.entity.unsplash.Total;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Status api.
 * */

public interface StatusApi {

    @GET("stats/total")
    Call<Total> getTotal();
}
