package com.wangdaye.mysplash.main.presenter.widget

import android.content.Context
import com.wangdaye.mysplash.Mysplash
import com.wangdaye.mysplash.R
import com.wangdaye.mysplash.common._basic.activity.MysplashActivity
import com.wangdaye.mysplash.common.data.entity.unsplash.Users
import com.wangdaye.mysplash.common.data.service.UserService
import com.wangdaye.mysplash.common.interfaces.model.UsersQueryModel
import com.wangdaye.mysplash.common.interfaces.presenter.UsersQueryPresenter
import com.wangdaye.mysplash.common.interfaces.view.UsersView
import com.wangdaye.mysplash.common.ui.adapter.UsersQueryAdapter
import com.wangdaye.mysplash.common.utils.helper.NotificationHelper
import retrofit2.Call
import retrofit2.Response

/**
 * Created by hoanghiep on 11/30/17.
 */
class UsersQueryImplementor(private val model: UsersQueryModel, private val view: UsersView) : UsersQueryPresenter {
    var listen: OnRequestUsersListener? = null

    override fun requestUsersQuey(context: Context, page: Int, refresh: Boolean, query: String) {
        if (!model.isRefreshing() && !model.isLoading()) {
            if (refresh) {
                model.setRefreshing(true)
            } else {
                model.setLoading(true)
            }
            onRequestUsersQuey(context, query, page, refresh)
        }
    }

    override fun cancelRequest() {
        listen?.cancel()
        model.getService()?.cancel()
        model.setRefreshing(false)
        model.setLoading(false)
    }

    override fun refreshNew(context: Context, notify: Boolean, query: String) {
        if (notify) {
            view.setRefreshing(true)
        }
        onRequestUsersQuey(context, query, model.getUsersPage(), true)
    }

    override fun loadMore(context: Context, notify: Boolean, query: String) {
        if (notify) {
            view.setLoading(true)
        }
        onRequestUsersQuey(context, query, model.getUsersPage(), false)
    }

    override fun initRefresh(context: Context, query: String) {
        cancelRequest()
        refreshNew(context, false, query)
        view.initRefreshStart()
    }

    override fun canLoadMore(): Boolean {
        return !model.isRefreshing() && !model.isLoading() && !model.isOver()
    }

    override fun isRefreshing(): Boolean {
        return model.isRefreshing()
    }

    override fun isLoading(): Boolean {
        return model.isLoading()
    }

    override fun setPage(page: Int) {
        model.setUsersPage(page)
    }

    override fun setOver(over: Boolean) {
        model.setOver(over)
        view.setPermitLoading(!over)
    }

    override fun setActivityForAdapter(activity: MysplashActivity) {
        model.getAdapter()?.setActivity(activity)
    }

    override fun getAdapter(): UsersQueryAdapter? {
        return model.getAdapter()
    }

    fun onRequestUsersQuey(context: Context, query: String, page: Int, refresh: Boolean) {
        val mPage = Math.max(1, if (refresh) 1 else page + 1)
        listen = OnRequestUsersListener(context, mPage, refresh)
        model.getService()?.requestUserByQuery(query, Mysplash.DEFAULT_PER_PAGE, mPage, listen)
    }

    inner class OnRequestUsersListener(// data
            private val context: Context, private val page: Int, private val refresh: Boolean) : UserService.OnRequestUsersByQueryListener {

        private var canceled: Boolean = false

        fun cancel() {
            canceled = true
        }
        override fun onRequestPhotosSuccesss(call: Call<Users>?, response: Response<Users>?) {
            if (canceled) {
                return
            }

            model.setRefreshing(false)
            model.setLoading(false)
            if (refresh) {
                view.setRefreshing(false)
            } else {
                view.setLoading(false)
            }
            if (response?.isSuccessful!! &&  response.body()?.results?.size!! > 0) {
                model.setUsersPage(page)
                if (refresh) {
                    model.getAdapter()?.clearItem()
                    setOver(false)
                }
                for (i in 0 until response.body()?.results!!.size) {
                    response.body()?.results?.get(i)?.let { model.getAdapter()?.insertItem(it) }
                }
                if (response.body()?.results!!.size < Mysplash.DEFAULT_PER_PAGE) {
                    setOver(true)
                }
                view.requestUsersSuccess()
            } else {
                view.requestUsersFailed(context.getString(R.string.feedback_load_nothing_tv))
            }

        }

        override fun onRequestPhotosFailedd(call: Call<Users>?, t: Throwable?) {
            if (canceled) {
                return
            }
            model.setRefreshing(false)
            model.setLoading(false)
            if (refresh) {
                view.setRefreshing(false)
            } else {
                view.setLoading(false)
            }
            NotificationHelper.showSnackbar(
                    context.getString(R.string.feedback_load_failed_toast)
                    + " (" + t?.message + ")")
            view.requestUsersFailed(context.getString(R.string.feedback_load_failed_tv))
        }

        init {
            this.canceled = false
        }
    }
}