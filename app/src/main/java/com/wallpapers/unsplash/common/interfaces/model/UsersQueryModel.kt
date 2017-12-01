package com.wallpapers.unsplash.common.interfaces.model

import com.wallpapers.unsplash.Unsplash
import com.wallpapers.unsplash.common.data.service.UserService
import com.wallpapers.unsplash.common.ui.adapter.UsersQueryAdapter

/**
 * Created by hoanghiep on 11/29/17.
 */
interface UsersQueryModel {
    abstract fun getAdapter(): UsersQueryAdapter?
    abstract fun getService(): UserService?

    abstract fun getUsersPage(): Int
    abstract fun setUsersPage(@Unsplash.PageRule page: Int)

    // control load state.

    abstract fun isRefreshing(): Boolean
    abstract fun setRefreshing(refreshing: Boolean)

    abstract fun isLoading(): Boolean
    abstract fun setLoading(loading: Boolean)

    /** The flag to mark the photos already load over.  */
    abstract fun isOver(): Boolean

    abstract fun setOver(over: Boolean)
}