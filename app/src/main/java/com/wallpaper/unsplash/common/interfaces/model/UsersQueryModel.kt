package com.wallpaper.unsplash.common.interfaces.model

import com.wallpaper.unsplash.UnsplashApplication
import com.wallpaper.unsplash.common.data.service.UserService
import com.wallpaper.unsplash.common.ui.adapter.UsersQueryAdapter

/**
 * Created by hoanghiep on 11/29/17.
 */
interface UsersQueryModel {
    abstract fun getAdapter(): UsersQueryAdapter?
    abstract fun getService(): UserService?

    abstract fun getUsersPage(): Int
    abstract fun setUsersPage(@UnsplashApplication.PageRule page: Int)

    // control load state.

    abstract fun isRefreshing(): Boolean
    abstract fun setRefreshing(refreshing: Boolean)

    abstract fun isLoading(): Boolean
    abstract fun setLoading(loading: Boolean)

    /** The flag to mark the photos already load over.  */
    abstract fun isOver(): Boolean

    abstract fun setOver(over: Boolean)
}