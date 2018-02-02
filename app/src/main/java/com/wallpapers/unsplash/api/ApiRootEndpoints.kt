package com.wallpapers.unsplash.api

import com.wallpapers.unsplash.common.data.entity.unsplash.Photos
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by hoanghiep on 1/17/18.
 */
interface ApiRootEndpoints {
    @GET("napi/search/photos")
    abstract fun getPhotoByTag(@Query("query") query: String,
                               @Query("per_page") perPage: Int,
                               @Query("page") page: Int): Observable<Photos>
}