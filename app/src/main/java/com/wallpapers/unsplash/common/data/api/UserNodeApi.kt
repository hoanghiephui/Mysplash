package com.wallpapers.unsplash.common.data.api

import com.wallpapers.unsplash.UnsplashApplication.UNSPLASH_NODE_API_URL
import com.wallpapers.unsplash.common.data.entity.unsplash.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by hoanghiep on 1/15/18.
 */
interface UserNodeApi {
    @GET(UNSPLASH_NODE_API_URL + "users/{username}")
    abstract fun getUserProfile(@Path("username") username: String,
                                @Query("w") w: Int,
                                @Query("h") h: Int): Call<User>

    @GET(UNSPLASH_NODE_API_URL + "users/{username}/following")
    abstract fun getFolloweing(@Path("username") username: String,
                               @Query("page") page: Int,
                               @Query("per_page") per_page: Int): Call<List<User>>

    @GET(UNSPLASH_NODE_API_URL + "users/{username}/followers")
    abstract fun getFollowers(@Path("username") username: String,
                              @Query("page") page: Int,
                              @Query("per_page") per_page: Int): Call<List<User>>
}