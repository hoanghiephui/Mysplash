package com.wallpaper.unsplash.common.interfaces.view

/**
 * Created by hoanghiep on 11/30/17.
 */
interface UsersView {
    abstract fun setRefreshing(refreshing: Boolean)
    abstract fun setLoading(loading: Boolean)

    abstract fun setPermitRefreshing(permit: Boolean)
    abstract fun setPermitLoading(permit: Boolean)

    abstract fun initRefreshStart()
    abstract fun requestUsersSuccess()
    abstract fun requestUsersFailed(feedback: String)
}