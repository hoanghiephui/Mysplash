package com.wallpapers.unsplash.common.data.api;

import com.wallpapers.unsplash.Unsplash;
import com.wallpapers.unsplash.common.data.entity.unsplash.FollowingFeed;
import com.wallpapers.unsplash.common.data.entity.unsplash.TrendingFeed;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Feed api.
 * */

public interface FeedApi {

    @GET(Unsplash.UNSPLASH_TREND_FEEDING_URL)
    Call<TrendingFeed> getTrendingFeed(@Query("after") String after);

    @GET(Unsplash.UNSPLASH_FOLLOWING_FEED_URL)
    Call<FollowingFeed> getFollowingFeed(@Query("after") String after);

    @POST("napi/users/{username}/follow")
    Call<ResponseBody> follow(@Path("username") String username);

    @DELETE("napi/users/{username}/follow")
    Call<ResponseBody> cancelFollow(@Path("username") String username);
}
