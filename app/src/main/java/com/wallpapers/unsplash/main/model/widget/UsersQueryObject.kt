package com.wallpapers.unsplash.main.model.widget

import com.wallpapers.unsplash.Unsplash
import com.wallpapers.unsplash.common.data.service.UserService
import com.wallpapers.unsplash.common.interfaces.model.UsersQueryModel
import com.wallpapers.unsplash.common.ui.adapter.UsersQueryAdapter

/**
 * Created by hoanghiep on 11/30/17.
 */
class UsersQueryObject(private val adapter: UsersQueryAdapter) : UsersQueryModel {
    private var usersPage: Int = 0
    private var refreshing: Boolean = false
    private var loading: Boolean = false
    private var over: Boolean = false

    private var usersSevice: UserService? = null

    init {
        this.usersSevice = UserService.getService()
        usersPage = adapter.getItemCount() / Unsplash.DEFAULT_PER_PAGE;
    }

    override fun getAdapter(): UsersQueryAdapter {
        return adapter
    }

    override fun getService(): UserService? {
        return usersSevice
    }

    override fun getUsersPage(): Int {
        return usersPage
    }

    override fun setUsersPage(page: Int) {
        this.usersPage = page
    }

    override fun isRefreshing(): Boolean {
        return refreshing
    }

    override fun setRefreshing(refreshing: Boolean) {
        this.refreshing = refreshing
    }

    override fun isLoading(): Boolean {
        return loading
    }

    override fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    override fun isOver(): Boolean {
        return over
    }

    override fun setOver(over: Boolean) {
        this.over = over
    }

}