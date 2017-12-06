package com.wallpapers.unsplash.main.view.widget

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.wallpapers.unsplash.R
import com.wallpapers.unsplash.common.basic.activity.BaseActivity
import com.wallpapers.unsplash.common.data.entity.unsplash.User
import com.wallpapers.unsplash.common.interfaces.model.LoadModel
import com.wallpapers.unsplash.common.interfaces.model.PagerModel
import com.wallpapers.unsplash.common.interfaces.model.ScrollModel
import com.wallpapers.unsplash.common.interfaces.model.UsersQueryModel
import com.wallpapers.unsplash.common.interfaces.presenter.LoadPresenter
import com.wallpapers.unsplash.common.interfaces.presenter.PagerPresenter
import com.wallpapers.unsplash.common.interfaces.presenter.ScrollPresenter
import com.wallpapers.unsplash.common.interfaces.presenter.UsersQueryPresenter
import com.wallpapers.unsplash.common.interfaces.view.LoadView
import com.wallpapers.unsplash.common.interfaces.view.PagerView
import com.wallpapers.unsplash.common.interfaces.view.ScrollView
import com.wallpapers.unsplash.common.interfaces.view.UsersView
import com.wallpapers.unsplash.common.ui.adapter.UsersQueryAdapter
import com.wallpapers.unsplash.common.ui.widget.nestedScrollView.NestedScrollFrameLayout
import com.wallpapers.unsplash.common.ui.widget.swipeRefreshView.BothWaySwipeRefreshLayout
import com.wallpapers.unsplash.common.utils.AnimUtils
import com.wallpapers.unsplash.common.utils.BackToTopUtils
import com.wallpapers.unsplash.common.utils.DisplayUtils
import com.wallpapers.unsplash.common.utils.manager.ThemeManager
import com.wallpapers.unsplash.main.model.widget.LoadObject
import com.wallpapers.unsplash.main.model.widget.PagerObject
import com.wallpapers.unsplash.main.model.widget.ScrollObject
import com.wallpapers.unsplash.main.model.widget.UsersQueryObject
import com.wallpapers.unsplash.main.presenter.widget.LoadImplementor
import com.wallpapers.unsplash.main.presenter.widget.PagerImplementor
import com.wallpapers.unsplash.main.presenter.widget.ScrollImplementor
import com.wallpapers.unsplash.main.presenter.widget.UsersQueryImplementor
import java.util.*

@SuppressLint("ViewConstructor")
/**
 * Created by hoanghiep on 11/30/17.
 */
class UsersQueryView(activity: BaseActivity?, id: Int, index: Int, selected: Boolean, private val query: String) : NestedScrollFrameLayout(activity),
        UsersView,
        PagerView, LoadView, ScrollView, BothWaySwipeRefreshLayout.OnRefreshAndLoadListener {
    var usersModel: UsersQueryModel? = null
    var usersPresenter: UsersQueryPresenter? = null
    private var pagerModel: PagerModel? = null
    private var pagerPresenter: PagerPresenter? = null

    private var loadModel: LoadModel? = null
    private var loadPresenter: LoadPresenter? = null

    private var scrollModel: ScrollModel? = null
    private var scrollPresenter: ScrollPresenter? = null

    internal var progressView: CircularProgressView? = null
    internal var feedbackContainer: RelativeLayout? = null
    internal var feedbackText: TextView? = null
    internal var refreshLayout: BothWaySwipeRefreshLayout? = null
    internal var recyclerView: RecyclerView? = null

    init {
        this.id = id
        this.initialize(activity, index, selected)
    }

    fun initialize(activity: BaseActivity?, index: Int, selected: Boolean) {
        val loadingView = LayoutInflater.from(context)
                .inflate(R.layout.container_loading_view_large, this, false)
        addView(loadingView)

        val contentView = LayoutInflater.from(context)
                .inflate(R.layout.container_photo_list, this, false)
        addView(contentView)

        initModel(activity, index, selected)
        initPresenter(activity)
        initView(contentView, loadingView)
    }

    private fun initModel(activity: BaseActivity?, index: Int, selected: Boolean) {
        this.usersModel = UsersQueryObject(UsersQueryAdapter())
        this.pagerModel = PagerObject(index, selected)
        this.loadModel = LoadObject(LoadModel.LOADING_STATE)
        this.scrollModel = ScrollObject(true)
    }

    private fun initPresenter(activity: BaseActivity?) {
        this.usersPresenter = usersModel?.let { UsersQueryImplementor(it, this) }
        this.pagerPresenter = PagerImplementor(pagerModel, this)
        this.loadPresenter = LoadImplementor(loadModel, this)
        this.scrollPresenter = ScrollImplementor(scrollModel, this)
        activity?.let { loadPresenter?.bindActivity(it) }
        activity?.let { (usersPresenter as UsersQueryImplementor?)?.setActivityForAdapter(it) }
    }

    private fun initView(view: View, viewL: View) {
        this.progressView = viewL.findViewById(R.id.container_loading_view_large_progressView)
        this.feedbackContainer = viewL.findViewById(R.id.container_loading_view_large_feedbackContainer)
        this.feedbackText = viewL.findViewById(R.id.container_loading_view_large_feedbackTxt)
        this.refreshLayout = view.findViewById(R.id.container_photo_list_swipeRefreshLayout)
        this.recyclerView = view.findViewById(R.id.container_photo_list_recyclerView)

        this.initContentView()
        this.initLoadingView()
    }

    private fun initContentView() {
        refreshLayout?.setColorSchemeColors(ThemeManager.getContentColor(context))
        refreshLayout?.setProgressBackgroundColorSchemeColor(ThemeManager.getRootColor(context))
        refreshLayout?.setOnRefreshAndLoadListener(this)
        refreshLayout?.visibility = View.GONE

        val navigationBarHeight = DisplayUtils.getNavigationBarHeight(resources)
        refreshLayout?.setDragTriggerDistance(
                BothWaySwipeRefreshLayout.DIRECTION_BOTTOM,
                navigationBarHeight + resources.getDimensionPixelSize(R.dimen.normal_margin))

        val columnCount = DisplayUtils.getGirdColumnCount(context)
        recyclerView?.adapter = usersPresenter?.getAdapter()
        if (columnCount > 1) {
            val margin = resources.getDimensionPixelSize(R.dimen.little_margin)
            recyclerView?.setPadding(margin, margin, 0, 0)
        } else {
            recyclerView?.setPadding(0, 0, 0, 0)
        }
        recyclerView?.layoutManager = StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL)
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //scrollPresenter?.autoLoad(dy)
            }
        })
    }

    private fun initLoadingView() {
        progressView?.visibility = View.VISIBLE
        feedbackContainer?.visibility = View.GONE
    }

    /**
     * Get the users from the adapter in this view.
     *
     * @return Users in adapter.
     */
    fun getUsers(): List<User> {
        return usersPresenter?.getAdapter()?.listUsers!!
    }

    /**
     * Set photos to the adapter in this view.
     *
     * @param list Photos that will be set to the adapter.
     */
    fun setUsers(list: List<User>?) {
        var mList = list
        if (mList == null) {
            mList = ArrayList()
        }
        usersPresenter?.getAdapter()?.setUserData(mList)
        if (mList.size == 0) {
            refreshPager()
        } else {
            loadPresenter?.setNormalState()
        }
    }

    override fun setRefreshing(refreshing: Boolean) {
        refreshLayout?.isRefreshing = refreshing
    }

    override fun setLoading(loading: Boolean) {
        refreshLayout?.isLoading = loading
    }

    override fun setPermitRefreshing(permit: Boolean) {
        refreshLayout?.setPermitRefresh(permit)
    }

    override fun setPermitLoading(permit: Boolean) {
        refreshLayout?.setPermitLoad(permit)
    }

    override fun initRefreshStart() {
        loadPresenter?.setLoadingState()
    }

    override fun requestUsersSuccess() {
        loadPresenter?.setNormalState()
    }

    override fun requestUsersFailed(feedback: String) {
        if (usersPresenter?.getAdapter()?.realItemCount!! > 0) {
            loadPresenter?.setNormalState()
        } else {
            feedbackText?.text = feedback
            loadPresenter?.setFailedState()
        }
    }

    override fun onSaveInstanceState(bundle: Bundle?) {
        bundle?.putParcelable(id.toString(), SavedState(this))
    }

    override fun onRestoreInstanceState(bundle: Bundle?) {
        val save: SavedState? = bundle?.getParcelable(id.toString())
        if (save != null) {
            usersPresenter?.setPage(save.page)
            usersPresenter?.setOver(save.over)
        } else {
            refreshPager()
        }
    }

    override fun checkToRefresh() {
        if (pagerPresenter?.checkNeedRefresh()!!) {
            pagerPresenter?.refreshPager()
        }
    }

    override fun needBackToTop(): Boolean {
        return !scrollPresenter?.isToTop!!
                && loadPresenter?.loadState == LoadObject.NORMAL_STATE
    }

    override fun checkNeedRefresh(): Boolean {
        return usersPresenter?.getAdapter()?.realItemCount!! <= 0
                && !usersPresenter?.isRefreshing()!! && !usersPresenter?.isLoading()!!
    }

    override fun scrollToTop() {
        BackToTopUtils.scrollToTop(recyclerView)
    }

    override fun checkNeedBackToTop(): Boolean {
        return scrollPresenter?.needBackToTop()!!
    }

    override fun autoLoad(dy: Int) {
        val lastVisibleItems = (recyclerView?.layoutManager as StaggeredGridLayoutManager)
                .findLastVisibleItemPositions(null)
        val totalItemCount = usersPresenter?.getAdapter()?.realItemCount
        if (usersPresenter?.canLoadMore()!!
                && lastVisibleItems[lastVisibleItems.size - 1] >= totalItemCount!! - 10
                && totalItemCount > 0
                && dy > 0) {
            usersPresenter?.loadMore(context, false, query)
            Log.e("aaaa", "cccc")
        }
        if (!recyclerView?.canScrollVertically(-1)!!) {
            scrollPresenter?.isToTop = true
        } else {
            scrollPresenter?.isToTop = false
        }
        if (!recyclerView?.canScrollVertically(1)!! && usersPresenter?.isLoading()!!) {
            refreshLayout?.isLoading = true
        }
    }

    override fun animShow(v: View?) {
        AnimUtils.animShow(v)
    }

    override fun refreshPager() {
        usersPresenter?.initRefresh(context, query)
    }

    override fun animHide(v: View?) {
        AnimUtils.animHide(v)
    }

    override fun setLoadingState(activity: BaseActivity?, old: Int) {
        if (activity != null && pagerPresenter?.isSelected!!) {
            DisplayUtils.setNavigationBarStyle(
                    activity, false, activity.hasTranslucentNavigationBar())
        }
        animShow(progressView)
        animHide(feedbackContainer)
        animHide(refreshLayout)
    }

    override fun scrollToPageTop() {
        scrollPresenter?.scrollToTop()
    }

    override fun cancelRequest() {
        usersPresenter?.cancelRequest()
    }

    override fun getKey(): String? {
        return null
    }

    override fun setFailedState(activity: BaseActivity?, old: Int) {
        animShow(feedbackContainer)
        animHide(progressView)
        animHide(refreshLayout)
    }

    override fun setKey(key: String?) {
    }

    override fun getItemCount(): Int {
        return if (loadPresenter?.loadState != LoadModel.NORMAL_STATE) {
            0
        } else {
            usersPresenter?.getAdapter()?.realItemCount!!
        }
    }

    override fun canSwipeBack(dir: Int): Boolean {
        return false
    }

    override fun setNormalState(activity: BaseActivity?, old: Int) {
        if (activity != null && pagerPresenter?.isSelected!!) {
            DisplayUtils.setNavigationBarStyle(
                    activity, true, activity.hasTranslucentNavigationBar())
        }
        animShow(refreshLayout)
        animHide(progressView)
        animHide(feedbackContainer)
    }

    override fun isNormalState(): Boolean {
        return loadPresenter?.loadState == LoadModel.NORMAL_STATE

    }

    override fun isParentOffset(): Boolean {
        return true
    }

    override fun onRefresh() {
        usersPresenter?.refreshNew(context, false, query)
    }

    override fun onLoad() {
        usersPresenter?.loadMore(context, false, query)
    }

    private class SavedState : Parcelable {

        internal var page: Int = 0
        internal var over: Boolean = false

        internal constructor(view: UsersQueryView) {
            this.page = view.usersModel?.getUsersPage()!!
            this.over = view.usersModel!!.isOver()
        }

        private constructor(`in`: Parcel) {
            this.page = `in`.readInt()
            this.over = `in`.readByte().toInt() != 0
        }

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            out.writeInt(this.page)
            out.writeByte(if (this.over) 1.toByte() else 0.toByte())
        }

        companion object {

            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

}
