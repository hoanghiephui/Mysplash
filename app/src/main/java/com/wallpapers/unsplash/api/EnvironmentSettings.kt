package com.wallpapers.unsplash.api

import com.wallpapers.unsplash.UnsplashApplication.UNSPLASH_API_BASE_URL
import com.wallpapers.unsplash.UnsplashApplication.UNSPLASH_URL

/**
 * Created by hoanghiep on 1/17/18.
 */
class EnvironmentSettings {
    val apiRootUrl: String
        get() = UNSPLASH_URL

    val apiUrl: String
        get() = UNSPLASH_API_BASE_URL
}