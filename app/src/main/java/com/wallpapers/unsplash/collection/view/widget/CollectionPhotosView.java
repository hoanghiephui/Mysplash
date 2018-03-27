package com.wallpapers.unsplash.collection.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.wallpapers.unsplash.Constants;
import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.collection.model.widget.LoadObject;
import com.wallpapers.unsplash.collection.model.widget.PhotosObject;
import com.wallpapers.unsplash.collection.model.widget.ScrollObject;
import com.wallpapers.unsplash.collection.presenter.widget.LoadImplementor;
import com.wallpapers.unsplash.collection.presenter.widget.PhotosImplementor;
import com.wallpapers.unsplash.collection.presenter.widget.ScrollImplementor;
import com.wallpapers.unsplash.collection.presenter.widget.SwipeBackImplementor;
import com.wallpapers.unsplash.collection.view.activity.CollectionActivity;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.data.entity.unsplash.Collection;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.data.entity.unsplash.User;
import com.wallpapers.unsplash.common.interfaces.model.LoadModel;
import com.wallpapers.unsplash.common.interfaces.model.PhotosModel;
import com.wallpapers.unsplash.common.interfaces.model.ScrollModel;
import com.wallpapers.unsplash.common.interfaces.presenter.LoadPresenter;
import com.wallpapers.unsplash.common.interfaces.presenter.PhotosPresenter;
import com.wallpapers.unsplash.common.interfaces.presenter.ScrollPresenter;
import com.wallpapers.unsplash.common.interfaces.presenter.SwipeBackPresenter;
import com.wallpapers.unsplash.common.interfaces.view.LoadView;
import com.wallpapers.unsplash.common.interfaces.view.PhotosView;
import com.wallpapers.unsplash.common.interfaces.view.ScrollView;
import com.wallpapers.unsplash.common.interfaces.view.SwipeBackView;
import com.wallpapers.unsplash.common.ui.adapter.PhotoAdapter;
import com.wallpapers.unsplash.common.ui.dialog.SelectCollectionDialog;
import com.wallpapers.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;
import com.wallpapers.unsplash.common.ui.widget.nestedScrollView.NestedScrollFrameLayout;
import com.wallpapers.unsplash.common.ui.widget.swipeRefreshView.BothWaySwipeRefreshLayout;
import com.wallpapers.unsplash.common.utils.AnimUtils;
import com.wallpapers.unsplash.common.utils.BackToTopUtils;
import com.wallpapers.unsplash.common.utils.DisplayUtils;
import com.wallpapers.unsplash.common.utils.manager.AuthManager;
import com.wallpapers.unsplash.common.utils.manager.ThemeManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Collection photos view.
 * <p>
 * This view is used to show the photos in a collection.
 */

public class CollectionPhotosView extends NestedScrollFrameLayout
        implements PhotosView, LoadView, ScrollView, SwipeBackView,
        View.OnClickListener, BothWaySwipeRefreshLayout.OnRefreshAndLoadListener,
        SelectCollectionDialog.OnCollectionsChangedListener {

    @BindView(R.id.container_loading_view_mini_progressView)
    CircularProgressView progressView;

    @BindView(R.id.container_loading_view_mini_retryButton)
    Button retryButton;

    @BindView(R.id.container_photo_list_swipeRefreshLayout)
    BothWaySwipeRefreshLayout refreshLayout;

    @BindView(R.id.container_photo_list_recyclerView)
    RecyclerView recyclerView;

    private PhotosModel photosModel;
    private PhotosPresenter photosPresenter;

    private LoadModel loadModel;
    private LoadPresenter loadPresenter;

    private ScrollModel scrollModel;
    private ScrollPresenter scrollPresenter;

    private SwipeBackPresenter swipeBackPresenter;

    public CollectionPhotosView(Context context) {
        super(context);
        this.initialize();
    }

    public CollectionPhotosView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialize();
    }

    public CollectionPhotosView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initialize();
    }

    // init.

    @SuppressLint("InflateParams")
    private void initialize() {
        View loadingView = LayoutInflater.from(getContext())
                .inflate(R.layout.container_loading_view_mini, this, false);
        addView(loadingView);

        View contentView = LayoutInflater.from(getContext())
                .inflate(R.layout.container_photo_list, null);
        addView(contentView);

        ButterKnife.bind(this, this);
        initView();
        if (Constants.SHOW_ADS) {
            initUpdateAdsTimer();
        }
    }

    private void initView() {
        this.initContentView();
        this.initLoadingView();
    }

    private void initContentView() {
        refreshLayout.setColorSchemeColors(ThemeManager.getContentColor(getContext()));
        refreshLayout.setProgressBackgroundColorSchemeColor(ThemeManager.getRootColor(getContext()));
        refreshLayout.setOnRefreshAndLoadListener(this);
        refreshLayout.setPermitRefresh(false);
        refreshLayout.setVisibility(GONE);

        int navigationBarHeight = DisplayUtils.getNavigationBarHeight(getResources());
        refreshLayout.setDragTriggerDistance(
                BothWaySwipeRefreshLayout.DIRECTION_BOTTOM,
                (int) (navigationBarHeight + new DisplayUtils(getContext()).dpToPx(16)));

        int columnCount = DisplayUtils.getGirdColumnCount(getContext());
        if (columnCount > 1) {
            int margin = getResources().getDimensionPixelSize(R.dimen.little_margin);
            recyclerView.setPadding(margin, margin, 0, 0);
        } else {
            recyclerView.setPadding(0, 0, 0, 0);
        }
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addOnScrollListener(onScrollListener);

    }

    private void initLoadingView() {
        retryButton.setVisibility(GONE);
        retryButton.setOnClickListener(this);
    }

    public void initMP(CollectionActivity a, Collection c) {
        initModel(a, c);
        initPresenter();
        if (Constants.SHOW_ADS) {
            initAds();
            if (adapterWrapper != null) {
                recyclerView.setAdapter(adapterWrapper);
            }
        } else {
            recyclerView.setAdapter(photosPresenter.getAdapter());
        }
        photosPresenter.getAdapter().setRecyclerView(recyclerView);
        loadPresenter.bindActivity(a);
    }

    private void initModel(CollectionActivity a, Collection c) {
        PhotoAdapter adapter = new PhotoAdapter(
                a, new ArrayList<Photo>(UnsplashApplication.DEFAULT_PER_PAGE), this, a, false);
        adapter.setInMyCollection(
                AuthManager.getInstance().getUsername() != null
                        && AuthManager.getInstance().getUsername().equals(c.user.username));

        this.photosModel = new PhotosObject(
                adapter,
                c,
                c.curated ? PhotosObject.PHOTOS_TYPE_CURATED : PhotosObject.PHOTOS_TYPE_NORMAL);
        this.loadModel = new LoadObject(LoadModel.LOADING_STATE);
        this.scrollModel = new ScrollObject();
    }

    private void initPresenter() {
        this.photosPresenter = new PhotosImplementor(photosModel, this);
        this.loadPresenter = new LoadImplementor(loadModel, this);
        this.scrollPresenter = new ScrollImplementor(scrollModel, this);
        this.swipeBackPresenter = new SwipeBackImplementor(this);
    }

    @Override
    public RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapterContent() {
        return photosPresenter.getAdapter();
    }

    // control nested scroll.

    @Override
    public boolean isParentOffset() {
        return false;
    }

    // control.

    /**
     * Set activity for the adapter in this view.
     *
     * @param a Container activity.
     */
    public void setActivity(BaseActivity a) {
        photosPresenter.setActivityForAdapter(a);
    }

    /**
     * Execute the initialize animation.
     */
    public void initAnimShow() {
        AnimUtils.animInitShow(progressView, 400);
    }

    public void initRefresh() {
        photosPresenter.initRefresh(getContext(), null);
    }

    public List<Photo> loadMore(List<Photo> list, int headIndex, boolean headDirection) {
        if ((headDirection && photosPresenter.getAdapter().getRealItemCount() < headIndex)
                || (!headDirection && photosPresenter.getAdapter().getRealItemCount() < headIndex + list.size())) {
            return new ArrayList<>();
        }

        if (!headDirection && photosPresenter.canLoadMore()) {
            photosPresenter.loadMore(getContext(), false, null);
        }
        if (!ViewCompat.canScrollVertically(recyclerView, 1) && photosPresenter.isLoading()) {
            refreshLayout.setLoading(true);
        }

        if (headDirection) {
            if (headIndex == 0) {
                return new ArrayList<>();
            } else {
                return photosPresenter.getAdapter().getPhotoData().subList(0, headIndex - 1);
            }
        } else {
            if (photosPresenter.getAdapter().getRealItemCount() == headIndex + list.size()) {
                return new ArrayList<>();
            } else {
                return photosPresenter.getAdapter()
                        .getPhotoData()
                        .subList(
                                headIndex + list.size(),
                                photosPresenter.getAdapter().getRealItemCount() - 1);
            }
        }
    }

    public void cancelRequest() {
        photosPresenter.cancelRequest();
        if (Constants.SHOW_ADS) {
            onDestroy();
        }
    }

    public boolean needPagerBackToTop() {
        return scrollPresenter.needBackToTop();
    }

    /**
     * Control the photo list in this view to scroll to the top.
     */
    public void pagerBackToTop() {
        scrollPresenter.scrollToTop();
    }

    public boolean canSwipeBack(int dir) {
        return swipeBackPresenter.checkCanSwipeBack(dir);
    }

    public Collection getCollection() {
        return (Collection) photosPresenter.getRequestKey();
    }

    public void updatePhoto(Photo photo, boolean refreshView) {
        photosPresenter.getAdapter().updatePhoto(photo, refreshView, false);
    }

    /**
     * Get the photos from the adapter in this view.
     *
     * @return Photos in adapter.
     */
    public List<Photo> getPhotos() {
        return photosPresenter.getAdapter().getPhotoData();
    }

    /**
     * Set photos to the adapter in this view.
     *
     * @param list Photos that will be set to the adapter.
     */
    public void setPhotos(List<Photo> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        photosPresenter.getAdapter().setPhotoData(list);
        photosPresenter.setPage(list.size() / UnsplashApplication.DEFAULT_PER_PAGE + 1);
        if (list.size() == 0) {
            initRefresh();
        } else {
            loadPresenter.setNormalState();
        }
    }

    // interface.

    // on click listener.

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.container_loading_view_mini_retryButton:
                photosPresenter.initRefresh(getContext(), null);
                break;
        }
    }

    // on refresh and load listener.

    @Override
    public void onRefresh() {
        photosPresenter.refreshNew(getContext(), false, null);
    }

    @Override
    public void onLoad() {
        photosPresenter.loadMore(getContext(), false, null);
    }

    // on scroll listener.

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            scrollPresenter.autoLoad(dy);
        }
    };

    // on collections change listener.

    @Override
    public void onAddCollection(Collection c) {
        // do nothing.
    }

    @Override
    public void onUpdateCollection(Collection c, User u, Photo p) {
        photosPresenter.getAdapter().updatePhoto(p, true, false);
        if (((Collection) photosPresenter.getRequestKey()).id == c.id) {
            for (int i = 0; i < p.current_user_collections.size(); i++) {
                if (p.current_user_collections.get(i).id == c.id) {
                    photosPresenter.getAdapter().insertItemToFirst(p);
                    return;
                }
            }
            photosPresenter.getAdapter().removeItem(p);
        }
    }

    // view.

    // photos view.

    @Override
    public void setRefreshing(boolean refreshing) {
        refreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void setLoading(boolean loading) {
        refreshLayout.setLoading(loading);
    }

    @Override
    public void setPermitRefreshing(boolean permit) {
        refreshLayout.setPermitRefresh(permit);
    }

    @Override
    public void setPermitLoading(boolean permit) {
        refreshLayout.setPermitLoad(permit);
    }

    @Override
    public void initRefreshStart() {
        loadPresenter.setLoadingState();
    }

    @Override
    public void requestPhotosSuccess() {
        loadPresenter.setNormalState();
    }

    @Override
    public void requestPhotosFailed(String feedback) {
        if (photosPresenter.getAdapter().getRealItemCount() > 0) {
            loadPresenter.setNormalState();
        } else {
            loadPresenter.setFailedState();
        }
    }

    // load view.

    @Override
    public void animShow(View v) {
        AnimUtils.animShow(v);
    }

    @Override
    public void animHide(final View v) {
        AnimUtils.animHide(v);
    }

    @Override
    public void setLoadingState(@Nullable BaseActivity activity, int old) {
        if (activity != null) {
            DisplayUtils.setNavigationBarStyle(
                    activity, false, activity.hasTranslucentNavigationBar());
        }
        animShow(progressView);
        animHide(retryButton);
        animHide(refreshLayout);
    }

    @Override
    public void setFailedState(@Nullable BaseActivity activity, int old) {
        animShow(retryButton);
        animHide(progressView);
        animHide(refreshLayout);
    }

    @Override
    public void setNormalState(@Nullable BaseActivity activity, int old) {
        if (activity != null) {
            DisplayUtils.setNavigationBarStyle(
                    activity, true, activity.hasTranslucentNavigationBar());
        }
        animShow(refreshLayout);
        animHide(progressView);
        animHide(retryButton);
    }

    // scroll view.

    @Override
    public void scrollToTop() {
        BackToTopUtils.scrollToTop(recyclerView);
    }

    @Override
    public void autoLoad(int dy) {
        int[] lastVisibleItems = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager())
                .findLastVisibleItemPositions(null);
        int totalItemCount = photosPresenter.getAdapter().getRealItemCount();
        if (photosPresenter.canLoadMore()
                && lastVisibleItems[lastVisibleItems.length - 1] >= totalItemCount - 10
                && totalItemCount > 0
                && dy > 0) {
            photosPresenter.loadMore(getContext(), false, null);
        }
        if (!ViewCompat.canScrollVertically(recyclerView, -1)) {
            scrollPresenter.setToTop(true);
        } else {
            scrollPresenter.setToTop(false);
        }
        if (!ViewCompat.canScrollVertically(recyclerView, 1) && photosPresenter.isLoading()) {
            refreshLayout.setLoading(true);
        }
    }

    @Override
    public boolean needBackToTop() {
        return !scrollPresenter.isToTop()
                && loadPresenter.getLoadState() == LoadModel.NORMAL_STATE;
    }

    // swipe back view.

    @Override
    public boolean checkCanSwipeBack(int dir) {
        switch (loadPresenter.getLoadState()) {
            case LoadModel.NORMAL_STATE:
                return SwipeBackCoordinatorLayout.canSwipeBack(recyclerView, dir)
                        || photosPresenter.getAdapter().getRealItemCount() <= 0;

            default:
                return true;
        }
    }
}