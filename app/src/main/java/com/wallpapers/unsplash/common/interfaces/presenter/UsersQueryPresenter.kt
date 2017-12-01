package com.wallpapers.unsplash.common.interfaces.presenter

import android.content.Context
import com.wallpapers.unsplash.Unsplash
import com.wallpapers.unsplash.common._basic.activity.MysplashActivity
import com.wallpapers.unsplash.common.ui.adapter.UsersQueryAdapter

/**
 * Created by hoanghiep on 11/29/17.
 */
interface UsersQueryPresenter {
    // HTTP request.

    abstract fun requestUsersQuey(context: Context, @Unsplash.PageRule page: Int, refresh: Boolean, query: String)
    abstract fun cancelRequest()

    // load data interface.

    /**
     * The param notify is used to control the SwipeRefreshLayout. If set true, the
     * SwipeRefreshLayout will show the refresh animation.
     */
    abstract fun refreshNew(context: Context, notify: Boolean, query: String)

    abstract fun loadMore(context: Context, notify: Boolean, query: String)
    abstract fun initRefresh(context: Context, query: String)

    abstract fun canLoadMore(): Boolean
    abstract fun isRefreshing(): Boolean
    abstract fun isLoading(): Boolean
    abstract fun setPage(@Unsplash.PageRule page: Int)
    abstract fun setOver(over: Boolean)
    abstract fun setActivityForAdapter(activity: MysplashActivity)
    abstract fun getAdapter(): UsersQueryAdapter?
}