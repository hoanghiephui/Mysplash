package com.wallpapers.unsplash.common.data.api

import com.wallpapers.unsplash.UnsplashApplication.UNSPLASH_NODE_API_URL
import com.wallpapers.unsplash.common.data.entity.unsplash.SearchCollectionsResult
import com.wallpapers.unsplash.common.data.entity.unsplash.SearchUsersResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by hoanghiep on 1/15/18.
 */
interface SearchNodeApi {
    @GET(UNSPLASH_NODE_API_URL + "search/users")
    abstract fun searchUsers(@Query("query") query: String,
                             @Query("page") page: Int,
                             @Query("per_page") per_page: Int): Call<SearchUsersResult>

    @GET(UNSPLASH_NODE_API_URL + "search/collections")
    abstract fun searchCollections(@Query("query") query: String,
                                   @Query("page") page: Int,
                                   @Query("per_page") per_page: Int): Call<SearchCollectionsResult>
}