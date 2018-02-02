package com.wallpapers.unsplash.main.view.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wallpapers.unsplash.Constants
import com.wallpapers.unsplash.R
import com.wallpapers.unsplash.UnsplashApplication
import com.wallpapers.unsplash.common.basic.activity.BaseActivity
import com.wallpapers.unsplash.common.basic.fragment.LoadableFragment
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo
import com.wallpapers.unsplash.common.interfaces.model.PagerManageModel
import com.wallpapers.unsplash.common.interfaces.presenter.PagerManagePresenter
import com.wallpapers.unsplash.common.interfaces.view.PagerManageView
import com.wallpapers.unsplash.common.interfaces.view.PagerView
import com.wallpapers.unsplash.common.ui.adapter.MyPagerAdapter
import com.wallpapers.unsplash.common.ui.adapter.UsersQueryAdapter
import com.wallpapers.unsplash.common.ui.widget.nestedScrollView.NestedScrollAppBarLayout
import com.wallpapers.unsplash.common.utils.BackToTopUtils
import com.wallpapers.unsplash.common.utils.DisplayUtils
import com.wallpapers.unsplash.common.utils.DisplayUtils.getNavigationBarHeight
import com.wallpapers.unsplash.common.utils.manager.ThemeManager
import com.wallpapers.unsplash.main.model.fragment.PagerManageObject
import com.wallpapers.unsplash.main.presenter.fragment.PagerManageImplementor
import com.wallpapers.unsplash.main.view.activity.MainActivity
import com.wallpapers.unsplash.main.view.widget.CollectionsView
import com.wallpapers.unsplash.main.view.widget.HomePhotosView
import com.wallpapers.unsplash.main.view.widget.UsersQueryView
import kotlinx.android.synthetic.main.container_notification_bar.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by hoanghiep on 11/28/17.
 */

class SearchQueryFragment : LoadableFragment<Photo>(),
        View.OnClickListener, PagerManageView,
        ViewPager.OnPageChangeListener,
        NestedScrollAppBarLayout.OnNestedScrollingListener, UsersQueryAdapter.CallBack {
    override fun onHeader() {
        //adContainer?.visibility = View.VISIBLE
    }

    override fun onFooter() {
        //adContainer?.visibility = View.GONE
    }

    private var title: String? = null
    var isExprore: Boolean? = null
    private val KEY_HOME_FRAGMENT_PAGE_POSITION = "home_fragment_page_position"
    private val KEY_HOME_FRAGMENT_PAGE_ORDER = "home_fragment_page_order"

    private val pagers = arrayOfNulls<PagerView>(3)
    private var pagerManageModel: PagerManageModel? = null
    private var pagerManagePresenter: PagerManagePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString("title")
        isExprore = arguments?.getBoolean("ex")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAds(getString(R.string.banner_search_query_facebook), Constants.ADMOB_BANNER_TAG, view)
        val toolbar = view.findViewById<Toolbar>(R.id.fragment_home_toolbar)
        toolbar?.title = title
        container_notification_bar_title.text = title
        container_notification_bar_button.visibility = View.GONE
        container_notification_bar_unreadFlag.visibility = View.GONE
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            context?.resources?.getDimension(R.dimen.subtitle_text_size)?.let {
                container_notification_bar_title.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        it)
            }
        } else {
            context?.resources?.getDimension(R.dimen.large_title_text_size)?.let {
                container_notification_bar_title.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        it)
            }
        }
        val paint = container_notification_bar_title.getPaint()
        paint.setFakeBoldText(true)
        if (getNavigationBarHeight(activity?.resources) != 0) {
            bottom_navigation.getLayoutParams().height = resources.getDimensionPixelSize(R.dimen.bottom_navigation_height)
            bottom_navigation.setPadding(0, 0, 0, resources.getDimensionPixelSize(R.dimen.navigation_bar_padding))
            bottom_navigation.requestLayout()
            adContainer.setPadding(0, 0, 0, resources.getDimensionPixelSize(R.dimen.navigation_bar_padding))
            adContainer.requestLayout()
        } else {
            bottom_navigation.getLayoutParams().height = resources.getDimensionPixelSize(android.R.dimen.app_icon_size)
            bottom_navigation.setPadding(0, 0, 0, 0)
            bottom_navigation.requestLayout()
            adContainer.setPadding(0, 0, 0, 0)
            adContainer.requestLayout()
        }

        ThemeManager.setNavigationIcon(
                toolbar, R.drawable.ic_toolbar_back_light, R.drawable.ic_toolbar_back_dark)
        toolbar?.setNavigationOnClickListener(this)
        fragment_home_appBar.setOnNestedScrollingListener(this)
        val menu = bottom_navigation.menu
        menu.clear()
        bottom_navigation.inflateMenu(R.menu.menu_tag)

        bottom_navigation.setOnNavigationItemSelectedListener({ item ->
            when (item.itemId) {
                R.id.navigation_photo -> {
                    fragment_home_viewPager.setCurrentItem(0)
                    true
                }
                R.id.navigation_collection -> {
                    fragment_home_viewPager.setCurrentItem(1)
                    true
                }
                R.id.navigation_users -> {
                    fragment_home_viewPager.setCurrentItem(2)
                    true
                }
                else -> false
            }
        })
        initModel(savedInstanceState)
        initPresenter()
        initPage(savedInstanceState)


        fragment_home_appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                Log.d("AAA", "ccc" + verticalOffset)
            }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        pagerManagePresenter?.pagerPosition?.let { outState.putInt(KEY_HOME_FRAGMENT_PAGE_POSITION, it) }
        for (viewPage in pagers) {
            viewPage?.onSaveInstanceState(outState)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        for (pageView in pagers) {
            pageView?.cancelRequest()
        }
    }

    fun initModel(savedInstanceState: Bundle?) {
        this.pagerManageModel = PagerManageObject(
                savedInstanceState?.getInt(KEY_HOME_FRAGMENT_PAGE_POSITION, 0) ?: 0)
    }

    fun initPresenter() {
        this.pagerManagePresenter = PagerManageImplementor(pagerManageModel, this)
    }

    fun initPage(savedInstanceState: Bundle?) {
        val pageList: ArrayList<View> = ArrayList()
        pageList.add(
                HomePhotosView(
                        activity as MainActivity,
                        UnsplashApplication.CATEGORY_PHOTO_TAG,
                        R.id.fragment_home_page_new,
                        0, pagerManagePresenter?.getPagerPosition() == 0,
                        title))

        pageList.add(
                CollectionsView(
                        activity as MainActivity,
                        UnsplashApplication.COLLECTION_TYPE_QUERY,
                        R.id.fragment_collection_page_query,
                        1, pagerManagePresenter?.getPagerPosition() == 1,
                        title))

        title?.let {
            UsersQueryView(
                    activity as MainActivity,
                    R.id.fragment_users_query,
                    2, pagerManagePresenter?.getPagerPosition() == 2,
                    it, this)
        }?.let {
            pageList.add(
                    it)
        }

        for (i in pageList.indices) {
            pagers[i] = pageList[i] as PagerView
        }

        val homeTabs = resources.getStringArray(R.array.home_tabs)

        val tabList = ArrayList<String>()
        Collections.addAll(tabList, *homeTabs)

        val adapter = MyPagerAdapter(pageList, tabList)

        fragment_home_viewPager.setAdapter(adapter)
        pagerManagePresenter?.getPagerPosition()?.let { fragment_home_viewPager.setCurrentItem(it, false) }
        fragment_home_viewPager.addOnPageChangeListener(this)



        fragment_home_indicator.setViewPager(fragment_home_viewPager)
        fragment_home_indicator.alpha = 0f

        if (savedInstanceState == null) {
            for (pager in pagers) {
                pager?.refreshPager()
            }
        } else {
            for (pager in pagers) {
                pager?.onRestoreInstanceState(savedInstanceState)
            }
        }
    }

    override fun loadMoreData(list: List<Photo>, headIndex: Int, headDirection: Boolean, bundle: Bundle): List<Photo>? {
        return null
    }

    override fun getBundleOfList(bundle: Bundle): Bundle? {
        val pagerIndex = pagerManagePresenter?.getPagerPosition()
        pagerIndex?.let { bundle.putInt(KEY_HOME_FRAGMENT_PAGE_POSITION, it) }
        if (pagerManagePresenter?.getPagerPosition() == 1 || pagerManagePresenter?.getPagerPosition() == 2) {
            pagerIndex?.let {
                bundle.putString(KEY_HOME_FRAGMENT_PAGE_ORDER, (pagers[it] as HomePhotosView).order)

            }
        }
        return bundle
    }

    override fun updateData(photorResponse: Photo) {
        val page = pagerManagePresenter?.getPagerPosition()
        if (page == 0) {
            (pagers[0] as HomePhotosView).updatePhoto(photorResponse, true)
        }
    }

    override fun initStatusBarStyle() {
        activity?.let { DisplayUtils.setStatusBarStyle(it, needSetDarkStatusBar()) }
    }

    override fun initNavigationBarStyle() {
        pagers[pagerManagePresenter?.getPagerPosition()!!]?.isNormalState()?.let {
            activity?.let { it1 ->
                DisplayUtils.setNavigationBarStyle(
                        it1,
                        it,
                        true)
            }
        }

    }

    override fun needSetDarkStatusBar(): Boolean {
        return fragment_home_appBar.y <= -fragment_home_appBar.measuredHeight
    }

    override fun writeLargeData(outState: BaseActivity.BaseSavedStateFragment) {
        if (pagers[0] != null) {
            (outState as MainActivity.SavedStateFragment).photoQueryList = (pagers[0] as HomePhotosView).getPhotos()
        }
        if (pagers[1] != null) {
            (outState as MainActivity.SavedStateFragment).queryCollectionList = (pagers[1] as CollectionsView).collections
        }
        if (pagers[2] != null) {
            (outState as MainActivity.SavedStateFragment).queryUsersList = (pagers[2] as UsersQueryView).getUsers()
        }
    }

    override fun readLargeData(savedInstanceState: BaseActivity.BaseSavedStateFragment) {
        if (pagers[0] != null) {
            (pagers[0] as HomePhotosView).photos = (savedInstanceState as MainActivity.SavedStateFragment).photoQueryList
        }
        if (pagers[1] != null) {
            (pagers[1] as CollectionsView).collections = (savedInstanceState as MainActivity.SavedStateFragment).queryCollectionList
        }
        if (pagers[2] != null) {
            (pagers[2] as UsersQueryView).setUsers((savedInstanceState as MainActivity.SavedStateFragment).queryUsersList)
        }
    }

    override fun getSnackbarContainer(): CoordinatorLayout? {
        return fragment_home_container
    }

    override fun needBackToTop(): Boolean {
        return pagerManagePresenter?.needPagerBackToTop()!!
    }

    override fun backToTop() {
        fragment_home_statusBar.animToInitAlpha()
        activity?.let { DisplayUtils.setStatusBarStyle(it, false) }
        BackToTopUtils.showTopBar(fragment_home_appBar, fragment_home_viewPager)
        pagerManagePresenter?.pagerScrollToTop()
    }

    override fun onClick(v: View) {

    }

    override fun getPagerItemCount(position: Int): Int {
        return pagers[position]!!.itemCount
    }

    override fun getPagerView(position: Int): PagerView {
        return pagers[position]!!
    }

    override fun canPagerSwipeBack(position: Int, dir: Int): Boolean {
        return false
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (fragment_home_appBar.y <= -fragment_home_appBar.measuredHeight) {
            when (state) {
                ViewPager.SCROLL_STATE_DRAGGING -> {
                    fragment_home_indicator.setDisplayState(true)
                }

                ViewPager.SCROLL_STATE_IDLE -> {
                    fragment_home_indicator.setDisplayState(false)
                }
            }
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        for (i in pagers.indices) {
            pagers[i]?.setSelected(i == position)
        }

        when (position) {
            0 -> bottom_navigation.setSelectedItemId(R.id.navigation_photo)
            1 -> bottom_navigation.setSelectedItemId(R.id.navigation_collection)
            2 -> bottom_navigation.setSelectedItemId(R.id.navigation_users)
        }

        pagerManagePresenter?.pagerPosition = position
        pagerManagePresenter?.checkToRefresh(position)
        activity?.let {
            DisplayUtils.setNavigationBarStyle(it,
                    pagers[position]!!.isNormalState,
                    false)
        }
    }

    override fun onStartNestedScroll() {
        if (needSetDarkStatusBar()) {
            if (fragment_home_statusBar.isInitState) {
                fragment_home_statusBar.animToDarkerAlpha()
                activity?.let { DisplayUtils.setStatusBarStyle(it, true) }
            }
        } else {
            if (!fragment_home_statusBar.isInitState) {
                fragment_home_statusBar.animToInitAlpha()
                activity?.let { DisplayUtils.setStatusBarStyle(it, false) }
            }
        }
    }

    override fun onNestedScrolling() {
        if (needSetDarkStatusBar()) {
            if (fragment_home_statusBar.isInitState) {
                fragment_home_statusBar.animToDarkerAlpha()
                activity?.let { DisplayUtils.setStatusBarStyle(it, true) }
            }
        } else {
            if (!fragment_home_statusBar.isInitState) {
                fragment_home_statusBar.animToInitAlpha()
                activity?.let { DisplayUtils.setStatusBarStyle(it, false) }
            }
        }
    }

    override fun onStopNestedScroll() {
    }

    companion object {
        fun newInstance(tilte: String, isExprore: Boolean): SearchQueryFragment {

            val args = Bundle()
            args.putString("title", tilte)
            args.putBoolean("ex", isExprore)
            val fragment = SearchQueryFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
