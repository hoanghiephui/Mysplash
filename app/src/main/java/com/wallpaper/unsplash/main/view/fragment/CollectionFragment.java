package com.wallpaper.unsplash.main.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.collection.view.activity.CollectionActivity;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.basic.fragment.BaseFragment;
import com.wallpaper.unsplash.common.data.entity.unsplash.Collection;
import com.wallpaper.unsplash.common.interfaces.model.PagerManageModel;
import com.wallpaper.unsplash.common.interfaces.presenter.PagerManagePresenter;
import com.wallpaper.unsplash.common.interfaces.presenter.ToolbarPresenter;
import com.wallpaper.unsplash.common.interfaces.view.PagerManageView;
import com.wallpaper.unsplash.common.interfaces.view.PagerView;
import com.wallpaper.unsplash.common.ui.adapter.MyPagerAdapter;
import com.wallpaper.unsplash.common.ui.widget.AutoHideInkPageIndicator;
import com.wallpaper.unsplash.common.ui.widget.coordinatorView.StatusBarView;
import com.wallpaper.unsplash.common.ui.widget.nestedScrollView.NestedScrollAppBarLayout;
import com.wallpaper.unsplash.common.utils.BackToTopUtils;
import com.wallpaper.unsplash.common.utils.DisplayUtils;
import com.wallpaper.unsplash.common.utils.manager.ThemeManager;
import com.wallpaper.unsplash.main.model.fragment.PagerManageObject;
import com.wallpaper.unsplash.main.presenter.fragment.PagerManageImplementor;
import com.wallpaper.unsplash.main.presenter.fragment.ToolbarImplementor;
import com.wallpaper.unsplash.main.view.activity.MainActivity;
import com.wallpaper.unsplash.main.view.widget.CollectionsView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Collection fragment.
 *
 * This fragment is used to show the collections.
 *
 * */

public class CollectionFragment extends BaseFragment
        implements PagerManageView,
        View.OnClickListener, ViewPager.OnPageChangeListener,
        NestedScrollAppBarLayout.OnNestedScrollingListener {

    @BindView(R.id.fragment_collection_statusBar)
    StatusBarView statusBar;

    @BindView(R.id.fragment_collection_container)
    CoordinatorLayout container;

    @BindView(R.id.fragment_collection_appBar)
    NestedScrollAppBarLayout appBar;

    @BindView(R.id.fragment_collection_toolbar)
    Toolbar toolbar;

    @BindView(R.id.fragment_collection_viewPager)
    ViewPager viewPager;

    @BindView(R.id.fragment_collection_indicator)
    AutoHideInkPageIndicator indicator;

    private PagerView[] pagers = new PagerView[3];

    private ToolbarPresenter toolbarPresenter;

    private PagerManageModel pagerManageModel;
    private PagerManagePresenter pagerManagePresenter;

    private final String KEY_COLLECTION_FRAGMENT_PAGE_POSITION = "collection_fragment_page_position";
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.bind(this, view);
        initModel(savedInstanceState);
        initPresenter();
        initView(view, savedInstanceState);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (PagerView p : pagers) {
            if (p != null) {
                p.cancelRequest();
            }
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_COLLECTION_FRAGMENT_PAGE_POSITION, pagerManagePresenter.getPagerPosition());
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
    public void writeLargeData(BaseActivity.BaseSavedStateFragment outState) {
        if (pagers[0] != null) {
            ((MainActivity.SavedStateFragment) outState).setFeaturedCollectionList(
                    ((CollectionsView) pagers[0]).getCollections());
        }
        if (pagers[1] != null) {
            ((MainActivity.SavedStateFragment) outState).setAllCollectionList(
                    ((CollectionsView) pagers[1]).getCollections());
        }
        if (pagers[2] != null) {
            ((MainActivity.SavedStateFragment) outState).setCuratedCollectionList(
                    ((CollectionsView) pagers[2]).getCollections());
        }
    }

    @Override
    public void readLargeData(BaseActivity.BaseSavedStateFragment savedInstanceState) {
        if (pagers[0] != null) {
            ((CollectionsView) pagers[0]).setCollections(
                    ((MainActivity.SavedStateFragment) savedInstanceState).getFeaturedCollectionList());
        }
        if (pagers[1] != null) {
            ((CollectionsView) pagers[1]).setCollections(
                    ((MainActivity.SavedStateFragment) savedInstanceState).getAllCollectionList());
        }
        if (pagers[2] != null) {
            ((CollectionsView) pagers[2]).setCollections(
                    ((MainActivity.SavedStateFragment) savedInstanceState).getCuratedCollectionList());
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
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case UnsplashApplication.COLLECTION_ACTIVITY:
                Collection collection = data.getParcelableExtra(
                        CollectionActivity.KEY_COLLECTION_ACTIVITY_COLLECTION);
                if (collection != null) {
                    ((CollectionsView) pagers[pagerManagePresenter.getPagerPosition()])
                            .updateCollection(collection, false);
                }
                break;
        }
    }

    @Override
    public CoordinatorLayout getSnackbarContainer() {
        return container;
    }

    // init.

    private void initModel(Bundle savedInstanceState) {
        this.pagerManageModel = new PagerManageObject(
                savedInstanceState == null ?
                        0 : savedInstanceState.getInt(KEY_COLLECTION_FRAGMENT_PAGE_POSITION, 0));
    }

    private void initView(View v, Bundle savedInstanceState) {
        appBar.setOnNestedScrollingListener(this);

        toolbar.setTitle(R.string.action_collection);
        ThemeManager.setNavigationIcon(
                toolbar, R.drawable.ic_toolbar_menu_light, R.drawable.ic_toolbar_menu_dark);
        toolbar.setNavigationOnClickListener(this);

        initPages(v, savedInstanceState);
    }

    private void initPages(View v, Bundle savedInstanceState) {
        List<View> pageList = new ArrayList<>();
        pageList.add(
                new CollectionsView(
                        (MainActivity) getActivity(),
                        UnsplashApplication.COLLECTION_TYPE_FEATURED,
                        R.id.fragment_collection_page_featured,
                        0, pagerManagePresenter.getPagerPosition() == 0));
        pageList.add(
                new CollectionsView(
                        (MainActivity) getActivity(),
                        UnsplashApplication.COLLECTION_TYPE_ALL,
                        R.id.fragment_collection_page_all,
                        1, pagerManagePresenter.getPagerPosition() == 1));
        pageList.add(
                new CollectionsView(
                        (MainActivity) getActivity(),
                        UnsplashApplication.COLLECTION_TYPE_CURATED,
                        R.id.fragment_collection_page_curated,
                        2, pagerManagePresenter.getPagerPosition() == 2));
        for (int i = 0; i < pageList.size(); i ++) {
            pagers[i] = (PagerView) pageList.get(i);
        }

        String[] homeTabs = getResources().getStringArray(R.array.collection_tabs);

        List<String> tabList = new ArrayList<>();
        Collections.addAll(tabList, homeTabs);

        MyPagerAdapter adapter = new MyPagerAdapter(pageList, tabList);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pagerManagePresenter.getPagerPosition(), false);
        viewPager.addOnPageChangeListener(this);

        TabLayout tabLayout = ButterKnife.findById(v, R.id.fragment_collection_tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);

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
        this.pagerManagePresenter = new PagerManageImplementor(pagerManageModel, this);
        // this.notificationBarPresenter = new HomeFragmentNotificationBarImplementor();
    }

    // interface.

    // on click listener.

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case -1:
                toolbarPresenter.touchNavigatorIcon((BaseActivity) getActivity());
                break;
        }
    }

    @OnClick(R.id.fragment_collection_toolbar) void clickToolbar() {
        toolbarPresenter.touchToolbar((BaseActivity) getActivity());
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
}