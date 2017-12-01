package com.wallpapers.unsplash.main.view.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wallpapers.unsplash.Unsplash;
import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common._basic.fragment.LoadableFragment;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.interfaces.model.PagerManageModel;
import com.wallpapers.unsplash.common.interfaces.presenter.PagerManagePresenter;
import com.wallpapers.unsplash.common.interfaces.presenter.ToolbarPresenter;
import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;
import com.wallpapers.unsplash.common.ui.widget.AutoHideInkPageIndicator;
import com.wallpapers.unsplash.common.ui.widget.nestedScrollView.NestedScrollAppBarLayout;
import com.wallpapers.unsplash.common.utils.BackToTopUtils;
import com.wallpapers.unsplash.common.interfaces.view.PagerManageView;
import com.wallpapers.unsplash.common.interfaces.view.PagerView;
import com.wallpapers.unsplash.common.interfaces.view.PopupManageView;
import com.wallpapers.unsplash.common.utils.DisplayUtils;
import com.wallpapers.unsplash.common.utils.helper.NotificationHelper;
import com.wallpapers.unsplash.common.utils.manager.ThemeManager;
import com.wallpapers.unsplash.main.model.fragment.PagerManageObject;
import com.wallpapers.unsplash.main.presenter.fragment.HomeFragmentPopupManageImplementor;
import com.wallpapers.unsplash.main.presenter.fragment.PagerManageImplementor;
import com.wallpapers.unsplash.main.presenter.fragment.ToolbarImplementor;
import com.wallpapers.unsplash.common.ui.adapter.MyPagerAdapter;
import com.wallpapers.unsplash.common.ui.widget.coordinatorView.StatusBarView;
import com.wallpapers.unsplash.main.view.activity.MainActivity;
import com.wallpapers.unsplash.main.view.widget.HomePhotosView;
import com.wallpapers.unsplash.main.view.widget.HomeTrendingView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wallpapers.unsplash.common.utils.DisplayUtils.getNavigationBarHeight;

/**
 * Home fragment.
 *
 * This fragment is used to show the home page of Unsplash.
 *
 * */

public class HomeFragment extends LoadableFragment<Photo>
        implements PopupManageView, PagerManageView,
        View.OnClickListener, Toolbar.OnMenuItemClickListener, ViewPager.OnPageChangeListener,
        NestedScrollAppBarLayout.OnNestedScrollingListener/*,
        UserNotificationManager.OnUpdateNotificationListener*/ {

    @BindView(R.id.fragment_home_statusBar)
    StatusBarView statusBar;

    @BindView(R.id.fragment_home_container)
    CoordinatorLayout container;

    @BindView(R.id.fragment_home_appBar)
    NestedScrollAppBarLayout appBar;

    @BindView(R.id.fragment_home_toolbar)
    Toolbar toolbar;

    @BindView(R.id.container_notification_bar_button)
    ImageButton bellBtn;

    @BindView(R.id.container_notification_bar_unreadFlag)
    ImageView redDot;

    @BindView(R.id.fragment_home_viewPager)
    ViewPager viewPager;

    @BindView(R.id.fragment_home_indicator)
    AutoHideInkPageIndicator indicator;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;

    private PagerView[] pagers = new PagerView[3];

    private ToolbarPresenter toolbarPresenter;

    private HomeFragmentPopupManageImplementor popupManageImplementor;

    private PagerManageModel pagerManageModel;
    private PagerManagePresenter pagerManagePresenter;

    // private NotificationBarPresenter notificationBarPresenter;

    private final String KEY_HOME_FRAGMENT_PAGE_POSITION = "home_fragment_page_position";
    private final String KEY_HOME_FRAGMENT_PAGE_ORDER = "home_fragment_page_order";
/*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuthManager.getInstance()
                .getNotificationManager()
                .addOnUpdateNotificationListener(this);
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initModel(savedInstanceState);
        initPresenter();
        initView(view, savedInstanceState);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*
        AuthManager.getInstance()
                .getNotificationManager()
                .removeOnUpdateNotificationListener(this);*/
        for (PagerView p : pagers) {
            if (p != null) {
                p.cancelRequest();
            }
        }
    }
/*
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            notificationBarPresenter.setVisible(bellBtn, redDot);
        }
    }
*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_HOME_FRAGMENT_PAGE_POSITION, pagerManagePresenter.getPagerPosition());
        for (PagerView p : pagers) {
            p.onSaveInstanceState(outState);
        }
    }

    @Override
    public void initStatusBarStyle() {
        DisplayUtils.setStatusBarStyle(getActivity(), needSetDarkStatusBar());
    }

    @Override
    public void initNavigationBarStyle() {
        DisplayUtils.setNavigationBarStyle(
                getActivity(),
                pagers[pagerManagePresenter.getPagerPosition()].isNormalState(),
                true);
    }

    @Override
    public boolean needSetDarkStatusBar() {
        return appBar.getY() <= -appBar.getMeasuredHeight();
    }

    @Override
    public void writeLargeData(MysplashActivity.BaseSavedStateFragment outState) {
        if (pagers[0] != null) {
            ((MainActivity.SavedStateFragment) outState).setHomeTrendingList(
                    ((HomeTrendingView) pagers[0]).getPhotos());
        }
        if (pagers[1] != null) {
            ((MainActivity.SavedStateFragment) outState).setHomeNewList(
                    ((HomePhotosView) pagers[1]).getPhotos());
        }
        if (pagers[2] != null) {
            ((MainActivity.SavedStateFragment) outState).setHomeFeaturedList(
                    ((HomePhotosView) pagers[2]).getPhotos());
        }
    }

    @Override
    public void readLargeData(MysplashActivity.BaseSavedStateFragment savedInstanceState) {
        if (pagers[0] != null) {
            ((HomeTrendingView) pagers[0]).setPhotos(
                    ((MainActivity.SavedStateFragment) savedInstanceState).getHomeTrendingList());
        }
        if (pagers[1] != null) {
            ((HomePhotosView) pagers[1]).setPhotos(
                    ((MainActivity.SavedStateFragment) savedInstanceState).getHomeNewList());
        }
        if (pagers[2] != null) {
            ((HomePhotosView) pagers[2]).setPhotos(
                    ((MainActivity.SavedStateFragment) savedInstanceState).getHomeFeaturedList());
        }
    }

    @Override
    public boolean needBackToTop() {
        return pagerManagePresenter.needPagerBackToTop();
    }

    @Override
    public void backToTop() {
        statusBar.animToInitAlpha();
        DisplayUtils.setStatusBarStyle(getActivity(), false);
        BackToTopUtils.showTopBar(appBar, viewPager);
        pagerManagePresenter.pagerScrollToTop();
    }

    @Override
    public CoordinatorLayout getSnackbarContainer() {
        return container;
    }

    @Override
    public List<Photo> loadMoreData(List<Photo> list, int headIndex, boolean headDirection, Bundle bundle) {
        int pagerIndex = bundle.getInt(KEY_HOME_FRAGMENT_PAGE_POSITION, -1);
        switch (pagerIndex) {
            case 0:
                return ((HomeTrendingView) pagers[0]).loadMore(list, headIndex, headDirection);

            case 1:
            case 2:
                if (((HomePhotosView) pagers[pagerIndex])
                        .getOrder()
                        .equals(bundle.getString(KEY_HOME_FRAGMENT_PAGE_ORDER, ""))) {
                    return ((HomePhotosView) pagers[pagerIndex]).loadMore(list, headIndex, headDirection);
                }
        }
        return new ArrayList<>();
    }

    @Override
    public Bundle getBundleOfList(Bundle bundle) {
        int pagerIndex = pagerManagePresenter.getPagerPosition();
        bundle.putInt(KEY_HOME_FRAGMENT_PAGE_POSITION, pagerIndex);
        if (pagerManagePresenter.getPagerPosition() == 1
                || pagerManagePresenter.getPagerPosition() == 2) {
            bundle.putString(KEY_HOME_FRAGMENT_PAGE_ORDER, ((HomePhotosView) pagers[pagerIndex]).getOrder());
        }
        return bundle;
    }

    @Override
    public void updateData(Photo photo) {
        int page = pagerManagePresenter.getPagerPosition();
        if (page == 0) {
            ((HomeTrendingView) pagers[0]).updatePhoto(photo, true);
        } else {
            ((HomePhotosView) pagers[page]).updatePhoto(photo, true);
        }
    }

    // init.

    private void initModel(Bundle savedInstanceState) {
        this.pagerManageModel = new PagerManageObject(
                savedInstanceState == null ?
                        0 : savedInstanceState.getInt(KEY_HOME_FRAGMENT_PAGE_POSITION, 0));
    }

    private void initView(View v, Bundle savedInstanceState) {
        appBar.setOnNestedScrollingListener(this);

        ThemeManager.inflateMenu(
                toolbar, R.menu.fragment_home_toolbar_light, R.menu.fragment_home_toolbar_dark);
        ThemeManager.setNavigationIcon(
                toolbar, R.drawable.ic_toolbar_menu_light, R.drawable.ic_toolbar_menu_dark);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(this);

        TextView title = v.findViewById(R.id.container_notification_bar_title);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            title.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    getContext().getResources().getDimension(R.dimen.subtitle_text_size));
        } else {
            title.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    getContext().getResources().getDimension(R.dimen.large_title_text_size));
        }
        TextPaint paint = title.getPaint();
        paint.setFakeBoldText(true);

        bellBtn.setAlpha(0F);
        bellBtn.setEnabled(false);
        redDot.setAlpha(0F);
        redDot.setEnabled(false);

        if (getNavigationBarHeight(getActivity().getResources()) != 0) {
            bottomNavigationView.getLayoutParams().height =
                    getResources().getDimensionPixelSize(R.dimen.bottom_navigation_height);
            bottomNavigationView.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.navigation_bar_padding));
            bottomNavigationView.requestLayout();
        } else {
            bottomNavigationView.getLayoutParams().height =
                    getResources().getDimensionPixelSize(android.R.dimen.app_icon_size);
            bottomNavigationView.setPadding(0, 0, 0, 0);
            bottomNavigationView.requestLayout();
        }
/*
        notificationBarPresenter.setVisible(bellBtn, redDot);
        if (AuthManager.getInstance().isAuthorized()
                && AuthManager.getInstance().getNumericId() > 0) {
            AuthManager.getInstance().getNotificationManager().requestPersonalNotifications();
        }
*/
        initPages(v, savedInstanceState);
    }

    private void initPages(View v, Bundle savedInstanceState) {
        List<View> pageList = new ArrayList<>();
        pageList.add(
                new HomeTrendingView(
                        (MainActivity) getActivity(),
                        R.id.fragment_home_page_trending,
                        0, pagerManagePresenter.getPagerPosition() == 0));
        pageList.add(
                new HomePhotosView(
                        (MainActivity) getActivity(),
                        Unsplash.CATEGORY_TOTAL_NEW,
                        R.id.fragment_home_page_new,
                        1, pagerManagePresenter.getPagerPosition() == 1));
        pageList.add(
                new HomePhotosView(
                        (MainActivity) getActivity(),
                        Unsplash.CATEGORY_TOTAL_FEATURED,
                        R.id.fragment_home_page_featured,
                        2, pagerManagePresenter.getPagerPosition() == 2));
        for (int i = 0; i < pageList.size(); i ++) {
            pagers[i] = (PagerView) pageList.get(i);
        }

        String[] homeTabs = getResources().getStringArray(R.array.home_tabs);

        List<String> tabList = new ArrayList<>();
        Collections.addAll(tabList, homeTabs);

        MyPagerAdapter adapter = new MyPagerAdapter(pageList, tabList);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pagerManagePresenter.getPagerPosition(), false);
        viewPager.addOnPageChangeListener(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_trending:
                        viewPager.setCurrentItem(0, true);
                        return true;
                    case R.id.navigation_new:
                        viewPager.setCurrentItem(1, true);
                        return true;
                    case R.id.navigation_featured:
                        viewPager.setCurrentItem(2, true);
                        return true;
                    default:
                        return false;
                }
            }
        });
        /*TabLayout tabLayout = ButterKnife.findById(v, R.id.fragment_home_tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);*/

        indicator.setViewPager(viewPager);
        indicator.setAlpha(0f);

        if (savedInstanceState == null) {
            for (PagerView pager : pagers) {
                pager.refreshPager();
            }
        } else {
            for (PagerView pager : pagers) {
                pager.onRestoreInstanceState(savedInstanceState);
            }
        }
    }

    private void initPresenter() {
        this.toolbarPresenter = new ToolbarImplementor();
        this.popupManageImplementor = new HomeFragmentPopupManageImplementor(this);
        this.pagerManagePresenter = new PagerManageImplementor(pagerManageModel, this);
        // this.notificationBarPresenter = new HomeFragmentNotificationBarImplementor();
    }

    // control.

    public void showPopup() {
        int page = pagerManagePresenter.getPagerPosition();
        if (page == 0) {
            NotificationHelper.showSnackbar(getString(R.string.feedback_no_filter));
        } else {
            popupManageImplementor.showPopup(
                    getActivity(),
                    toolbar,
                    pagerManagePresenter.getPagerKey(page),
                    page);
        }
    }

    // interface.

    // on click listener.

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case -1:
                toolbarPresenter.touchNavigatorIcon((MysplashActivity) getActivity());
                break;
        }
    }

    @OnClick(R.id.fragment_home_toolbar) void clickToolbar() {
        toolbarPresenter.touchToolbar((MysplashActivity) getActivity());
    }
/*
    @OnClick(R.id.container_notification_bar_button) void clickBellBtn() {
        IntentHelper.startNotificationActivity((MysplashActivity) getActivity());
    }
*/
    // on menu item click listener.

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return toolbarPresenter.touchMenuItem((MysplashActivity) getActivity(), item.getItemId());
    }

    // on page changed listener.

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // do nothing.
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < pagers.length; i ++) {
            pagers[i].setSelected(i == position);
        }
        switch (position) {
            case 1:
                bottomNavigationView.setSelectedItemId(R.id.navigation_new);
                break;
            case 2:
                bottomNavigationView.setSelectedItemId(R.id.navigation_featured);
                break;
            default:
                bottomNavigationView.setSelectedItemId(R.id.navigation_trending);
                break;
        }
        pagerManagePresenter.setPagerPosition(position);
        pagerManagePresenter.checkToRefresh(position);
        DisplayUtils.setNavigationBarStyle(
                getActivity(),
                pagers[position].isNormalState(),
                true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (appBar.getY() <= -appBar.getMeasuredHeight()) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    indicator.setDisplayState(true);
                    break;

                case ViewPager.SCROLL_STATE_IDLE:
                    indicator.setDisplayState(false);
                    break;
            }
        }
    }

    // on nested scrolling listener.

    @Override
    public void onStartNestedScroll() {
        // do nothing.
    }

    @Override
    public void onNestedScrolling() {
        if (needSetDarkStatusBar()) {
            if (statusBar.isInitState()) {
                statusBar.animToDarkerAlpha();
                DisplayUtils.setStatusBarStyle(getActivity(), true);
            }
        } else {
            if (!statusBar.isInitState()) {
                statusBar.animToInitAlpha();
                DisplayUtils.setStatusBarStyle(getActivity(), false);
            }
        }
    }

    @Override
    public void onStopNestedScroll() {
        // do nothing.
    }

    // on update notification listener.
/*
    @Override
    public void onRequestNotificationSucceed(List<NotificationResult> resultList) {
        notificationBarPresenter.setImage(bellBtn, redDot);
    }

    @Override
    public void onRequestNotificationFailed() {
        // do nothing.
    }

    @Override
    public void onAddNotification(NotificationResult result, int position) {
        // do nothing.
    }

    @Override
    public void onClearNotification() {
        notificationBarPresenter.setVisible(bellBtn, redDot);
    }

    @Override
    public void onSetLatestTime() {
        notificationBarPresenter.setVisible(bellBtn, redDot);
    }
*/
    // view.

    // pager manage view.

    @Override
    public PagerView getPagerView(int position) {
        return pagers[position];
    }

    @Override
    public boolean canPagerSwipeBack(int position, int dir) {
        return false;
    }

    @Override
    public int getPagerItemCount(int position) {
        return pagers[position].getItemCount();
    }

    // popup manage view.

    @Override
    public void responsePopup(String value, int position) {
        pagers[position].setKey(value);
        pagers[position].refreshPager();
    }
}
