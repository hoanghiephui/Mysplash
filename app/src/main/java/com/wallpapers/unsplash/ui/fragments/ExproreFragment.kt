package com.wallpapers.unsplash.ui.fragments

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wallpapers.unsplash.R
import com.wallpapers.unsplash.UnsplashApplication.SEARCH_QUERY_ID
import com.wallpapers.unsplash.common.basic.activity.BaseActivity
import com.wallpapers.unsplash.common.interfaces.presenter.ToolbarPresenter
import com.wallpapers.unsplash.common.ui.widget.nestedScrollView.NestedScrollAppBarLayout
import com.wallpapers.unsplash.common.utils.DisplayUtils
import com.wallpapers.unsplash.common.utils.manager.ThemeManager
import com.wallpapers.unsplash.exprore.ExprorePresenter
import com.wallpapers.unsplash.exprore.ExproreView
import com.wallpapers.unsplash.injection.Injector
import com.wallpapers.unsplash.kotlin.extensions.gone
import com.wallpapers.unsplash.kotlin.extensions.visible
import com.wallpapers.unsplash.kotlin.unsafeLazy
import com.wallpapers.unsplash.main.presenter.fragment.ToolbarImplementor
import com.wallpapers.unsplash.main.view.activity.MainActivity
import com.wallpapers.unsplash.ui.adapters.exprore.ExproreDelegate
import com.wallpapers.unsplash.ui.adapters.exprore.ExproreDelegateAdapter
import com.wallpapers.unsplash.ui.base.BaseFragment
import kotlinx.android.synthetic.main.container_notification_bar.*
import kotlinx.android.synthetic.main.fragment_exprore.*
import javax.inject.Inject

/**
 * Created by hoanghiep on 1/17/18.
 */
class ExproreFragment : BaseFragment<ExproreView, ExprorePresenter>(), ExproreView,
        NestedScrollAppBarLayout.OnNestedScrollingListener, View.OnClickListener, ExproreDelegate.CallBack, SwipeRefreshLayout.OnRefreshListener {


    override fun onRefresh() {
        swipeRefresh.isRefreshing = true
        recycler.gone()
        presenter?.loadData()
    }


    override fun onClick(position: Int) {
        when (position) {
            0 -> {
                (activity as MainActivity).changeFragment(SEARCH_QUERY_ID, "Business", true)
            }
            1 -> {
                (activity as MainActivity).changeFragment(SEARCH_QUERY_ID, "Girl", true)
            }

            3 -> {
                (activity as MainActivity).changeFragment(SEARCH_QUERY_ID, "Nature", true)
            }
            4 -> {
                (activity as MainActivity).changeFragment(SEARCH_QUERY_ID, "Technology", true)
            }

            5 -> {
                (activity as MainActivity).changeFragment(SEARCH_QUERY_ID, "Food", true)
            }
            7 -> {
                (activity as MainActivity).changeFragment(SEARCH_QUERY_ID, "Travel", true)
            }

            8 -> {
                (activity as MainActivity).changeFragment(SEARCH_QUERY_ID, "Happy", true)
            }
            9 -> {
                (activity as MainActivity).changeFragment(SEARCH_QUERY_ID, "Cool", true)
            }
        }
    }

    override fun onClick(v: View?) {
        when (view?.getId()) {
            -1 -> toolbarPresenter?.touchNavigatorIcon(activity as BaseActivity?)
        }
    }

    override fun onStartNestedScroll() {

    }

    override fun onNestedScrolling() {
        if (needSetDarkStatusBar()) {
            if (fragment_home_statusBar.isInitState()) {
                fragment_home_statusBar.animToDarkerAlpha()
                DisplayUtils.setStatusBarStyle(activity!!, true)
            }
        } else {
            if (!fragment_home_statusBar.isInitState()) {
                fragment_home_statusBar.animToInitAlpha()
                DisplayUtils.setStatusBarStyle(activity!!, false)
            }
        }
    }

    override fun onStopNestedScroll() {
    }

    @Inject
    lateinit var exprorePresenter: ExprorePresenter
    private val exproreAdapter by unsafeLazy { activity?.let { ExproreDelegateAdapter(it, this) } }

    init {
        Injector.INSTANCE.presenterComponent.inject(this)
    }

    private var toolbarPresenter: ToolbarPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exprore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.apply {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = exproreAdapter
        }
        fragment_home_appBar.setOnNestedScrollingListener(this)
        ThemeManager.setNavigationIcon(
                fragment_home_toolbar, R.drawable.ic_toolbar_menu_light, R.drawable.ic_toolbar_menu_dark)
        fragment_home_toolbar.setNavigationOnClickListener(this)
        fragment_home_toolbar?.title = "Explore"
        container_notification_bar_title.text = "Explore"
        container_notification_bar_button.visibility = View.GONE
        container_notification_bar_unreadFlag.visibility = View.GONE
        initPresenter()
        swipeRefresh.setOnRefreshListener(this)

        onViewReady()
        swipeRefresh.isRefreshing = true
        recycler.gone()
        presenter?.loadData()
    }

    override fun onResume() {
        //presenter?.on
        super.onResume()
    }

    private fun initPresenter() {
        this.toolbarPresenter = ToolbarImplementor()
    }

    override fun updatePhoto(photos: ExprorePresenter.ExproreData) {
        exproreAdapter?.updatePhoto(photo = photos.exBusiness.results)
        exproreAdapter?.updatePhotoGirl(photos.exGirl.results)
        exproreAdapter?.updatePhotoNature(photos.exNature.results)
        exproreAdapter?.updatePhotoTechnology(photos.exTechnology.results)
        exproreAdapter?.updatePhotoFood(photos.exFood.results)
        exproreAdapter?.updatePhotoTravel(photos.exTravel.results)
        exproreAdapter?.updatePhotoHappy(photos.exHappy.results)
        exproreAdapter?.updatePhotoCool(photos.exCool.results)
    }

    override fun onComplete() {
        exproreAdapter?.onComplete()
        swipeRefresh.isRefreshing = false
        recycler.visible()
    }

    override fun onError(message: String?) {
        exproreAdapter?.onError()
        swipeRefresh.isRefreshing = false
        recycler.gone()
    }

    override fun notifyItemAdded(displayItems: MutableList<Any>, position: Int) {
        exproreAdapter?.items = displayItems
        exproreAdapter?.notifyItemInserted(position)
        recycler.scrollToPosition(0)
    }

    override fun notifyItemRemoved(displayItems: MutableList<Any>, position: Int) {
    }

    override val mvpView: ExproreView
        get() = this

    override fun createPresenter(): ExprorePresenter {
        return exprorePresenter
    }

    override fun initStatusBarStyle() {
        DisplayUtils.setStatusBarStyle(activity!!, needSetDarkStatusBar())
    }

    override fun initNavigationBarStyle() {
    }

    override fun needSetDarkStatusBar(): Boolean {
        return fragment_home_appBar.getY() <= -fragment_home_appBar.getMeasuredHeight()
    }

    override fun writeLargeData(outState: BaseActivity.BaseSavedStateFragment?) {
    }

    override fun readLargeData(savedInstanceState: BaseActivity.BaseSavedStateFragment?) {
    }

    override fun getSnackbarContainer(): CoordinatorLayout {
        return fragment_home_container
    }

    override fun needBackToTop(): Boolean {
        return false
    }

    override fun backToTop() {

    }
}