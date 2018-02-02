package com.wallpapers.unsplash.common.data.api

import com.wallpapers.unsplash.UnsplashApplication.UNSPLASH_NODE_API_URL
import com.wallpapers.unsplash.common.data.entity.unsplash.Collection
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by hoanghiep on 1/15/18.
 * Collection node api.
 */
interface CollectionNodeApi {
    @GET(UNSPLASH_NODE_API_URL + "collections")
    fun getAllCollections(@Query("page") page: Int,
                          @Query("per_page") per_page: Int): Call<List<Collection>>

    @GET(UNSPLASH_NODE_API_URL + "collections/curated")
    fun getCuratedCollections(@Query("page") page: Int,
                              @Query("per_page") per_page: Int): Call<List<Collection>>

    @GET(UNSPLASH_NODE_API_URL + "collections/featured")
    fun getFeaturedCollections(@Query("page") page: Int,
                               @Query("per_page") per_page: Int): Call<List<Collection>>

    @GET(UNSPLASH_NODE_API_URL + "collections/{id}")
    fun getACollection(@Path("id") id: String): Call<Collection>

    @GET(UNSPLASH_NODE_API_URL + "collections/curated/{id}")
    fun getACuratedCollection(@Path("id") id: String): Call<Collection>

    @GET(UNSPLASH_NODE_API_URL + "users/{username}/collections")
    fun getUserCollections(@Path("username") username: String,
                           @Query("page") page: Int,
                           @Query("per_page") per_page: Int): Call<List<Collection>>
}