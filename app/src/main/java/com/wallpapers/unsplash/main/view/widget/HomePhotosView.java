package com.wallpapers.unsplash.main.view.widget;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.wallpapers.unsplash.Constants;
import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.data.entity.unsplash.Collection;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.data.entity.unsplash.User;
import com.wallpapers.unsplash.common.interfaces.model.LoadModel;
import com.wallpapers.unsplash.common.interfaces.model.PagerModel;
import com.wallpapers.unsplash.common.interfaces.model.PhotosModel;
import com.wallpapers.unsplash.common.interfaces.model.ScrollModel;
import com.wallpapers.unsplash.common.interfaces.presenter.LoadPresenter;
import com.wallpapers.unsplash.common.interfaces.presenter.PagerPresenter;
import com.wallpapers.unsplash.common.interfaces.presenter.PhotosPresenter;
import com.wallpapers.unsplash.common.interfaces.presenter.ScrollPresenter;
import com.wallpapers.unsplash.common.interfaces.view.LoadView;
import com.wallpapers.unsplash.common.interfaces.view.PagerView;
import com.wallpapers.unsplash.common.interfaces.view.PhotosView;
import com.wallpapers.unsplash.common.interfaces.view.ScrollView;
import com.wallpapers.unsplash.common.ui.adapter.PhotoAdapter;
import com.wallpapers.unsplash.common.ui.dialog.SelectCollectionDialog;
import com.wallpapers.unsplash.common.ui.widget.nestedScrollView.NestedScrollFrameLayout;
import com.wallpapers.unsplash.common.ui.widget.swipeRefreshView.BothWaySwipeRefreshLayout;
import com.wallpapers.unsplash.common.utils.AnimUtils;
import com.wallpapers.unsplash.common.utils.BackToTopUtils;
import com.wallpapers.unsplash.common.utils.DisplayUtils;
import com.wallpapers.unsplash.common.utils.helper.ImageHelper;
import com.wallpapers.unsplash.common.utils.manager.ThemeManager;
import com.wallpapers.unsplash.main.model.widget.LoadObject;
import com.wallpapers.unsplash.main.model.widget.PagerObject;
import com.wallpapers.unsplash.main.model.widget.PhotosObject;
import com.wallpapers.unsplash.main.model.widget.ScrollObject;
import com.wallpapers.unsplash.main.presenter.widget.LoadImplementor;
import com.wallpapers.unsplash.main.presenter.widget.PagerImplementor;
import com.wallpapers.unsplash.main.presenter.widget.PhotosImplementor;
import com.wallpapers.unsplash.main.presenter.widget.ScrollImplementor;
import com.wallpapers.unsplash.main.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Home photos view.
 * <p>
 * This view is used to show photos for
 * {@link com.wallpapers.unsplash.main.view.fragment.HomeFragment}.
 */

@SuppressLint("ViewConstructor")
public class HomePhotosView extends NestedScrollFrameLayout
        implements PhotosView, PagerView, LoadView, ScrollView,
        BothWaySwipeRefreshLayout.OnRefreshAndLoadListener,
        SelectCollectionDialog.OnCollectionsChangedListener {

    @BindView(R.id.container_loading_view_large_progressView)
    CircularProgressView progressView;

    @BindView(R.id.container_loading_view_large_feedbackContainer)
    RelativeLayout feedbackContainer;

    @BindView(R.id.container_loading_view_large_feedbackTxt)
    TextView feedbackText;

    @BindView(R.id.container_photo_list_swipeRefreshLayout)
    BothWaySwipeRefreshLayout refreshLayout;

    @BindView(R.id.container_photo_list_recyclerView)
    RecyclerView recyclerView;

    private PhotosModel photosModel;
    private PhotosPresenter photosPresenter;

    private PagerModel pagerModel;
    private PagerPresenter pagerPresenter;

    private LoadModel loadModel;
    private LoadPresenter loadPresenter;

    private ScrollModel scrollModel;
    private ScrollPresenter scrollPresenter;
    private String query;


    private static class SavedState implements Parcelable {

        String order;
        int page;
        List<Integer> pageList;
        boolean over;

        SavedState(HomePhotosView view) {
            this.order = view.photosModel.getPhotosOrder();
            this.page = view.photosModel.getPhotosPage();
            this.pageList = new ArrayList<>();
            this.pageList.addAll(view.photosModel.getPageList());
            this.over = view.photosModel.isOver();
        }

        private SavedState(Parcel in) {
            this.order = in.readString();
            this.page = in.readInt();

            this.pageList = new ArrayList<>();
            int[] pages = new int[in.readInt()];
            in.readIntArray(pages);
            pageList = new ArrayList<>(pages.length);
            for (int p : pages) {
                pageList.add(p);
            }

            this.over = in.readByte() != 0;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(this.order);
            out.writeInt(this.page);

            int[] pages = new int[pageList.size()];
            for (int i = 0; i < pages.length; i++) {
                pages[i] = pageList.get(i);
            }
            out.writeInt(pages.length);
            out.writeIntArray(pages);

            out.writeByte(this.over ? (byte) 1 : (byte) 0);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    public HomePhotosView(MainActivity a,
                          @UnsplashApplication.PhotosTypeRule int photosType,
                          int id,
                          int index,
                          boolean selected) {
        super(a);
        this.setId(id);
        this.initialize(a, photosType, index, selected);
    }

    public HomePhotosView(MainActivity a,
                          @UnsplashApplication.PhotosTypeRule int photosType,
                          int id,
                          int index,
                          boolean selected,
                          String query) {
        super(a);
        this.setId(id);
        this.initialize(a, photosType, index, selected);
        this.query = query;
    }

    // init.

    @SuppressLint("InflateParams")
    private void initialize(MainActivity a, @UnsplashApplication.PhotosTypeRule int photosType,
                            int index, boolean selected) {
        View loadingView = LayoutInflater.from(getContext())
                .inflate(R.layout.container_loading_view_large, this, false);
        addView(loadingView);

        View contentView = LayoutInflater.from(getContext())
                .inflate(R.layout.container_photo_list, null);
        addView(contentView);

        ButterKnife.bind(this, this);
        initModel(a, photosType, index, selected);
        initPresenter(a);
        initView();
        if (Constants.SHOW_ADS) {
            initUpdateAdsTimer();
        }
    }

    private void initModel(MainActivity mainActivity, @UnsplashApplication.PhotosTypeRule int photosType,
                           int index, boolean selected) {
        this.photosModel = new PhotosObject(
                mainActivity,
                new PhotoAdapter(mainActivity, new ArrayList<Photo>(UnsplashApplication.DEFAULT_PER_PAGE), this, mainActivity, false),
                photosType);
        this.pagerModel = new PagerObject(index, selected);
        this.loadModel = new LoadObject(LoadModel.LOADING_STATE);
        this.scrollModel = new ScrollObject(true);
    }

    private void initPresenter(BaseActivity a) {
        this.photosPresenter = new PhotosImplementor(photosModel, this);
        this.pagerPresenter = new PagerImplementor(pagerModel, this);
        this.loadPresenter = new LoadImplementor(loadModel, this);
        this.scrollPresenter = new ScrollImplementor(scrollModel, this);

        loadPresenter.bindActivity(a);
    }

    private void initView() {
        this.initContentView();
        this.initLoadingView();
    }

    private void initContentView() {
        refreshLayout.setColorSchemeColors(ThemeManager.getContentColor(getContext()));
        refreshLayout.setProgressBackgroundColorSchemeColor(ThemeManager.getRootColor(getContext()));
        refreshLayout.setOnRefreshAndLoadListener(this);
        refreshLayout.setVisibility(GONE);

        int navigationBarHeight = DisplayUtils.getNavigationBarHeight(getResources());
        refreshLayout.setDragTriggerDistance(
                BothWaySwipeRefreshLayout.DIRECTION_BOTTOM,
                navigationBarHeight + getResources().getDimensionPixelSize(R.dimen.normal_margin));

        int columnCount = DisplayUtils.getGirdColumnCount(getContext());
        if (Constants.SHOW_ADS) {
            initAds();
            recyclerView.setAdapter(adapterWrapper);
        } else {
            recyclerView.setAdapter(photosPresenter.getAdapter());
        }
        if (columnCount > 1) {
            int margin = getResources().getDimensionPixelSize(R.dimen.little_margin);
            recyclerView.setPadding(margin, margin, 0, 0);
        } else {
            recyclerView.setPadding(0, 0, 0, 0);
        }
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addOnScrollListener(onScrollListener);

        photosPresenter.getAdapter().setRecyclerView(recyclerView);
    }

    private void initLoadingView() {
        progressView.setVisibility(VISIBLE);
        feedbackContainer.setVisibility(GONE);

        ImageView feedbackImg = this.findViewById(R.id.container_loading_view_large_feedbackImg);
        ImageHelper.loadResourceImage(getContext(), feedbackImg, R.drawable.feedback_no_photos);
    }

    @Override
    public RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapterContent() {
        return photosPresenter.getAdapter();
    }

    // control.

    @Override
    public boolean isParentOffset() {
        return true;
    }

    public List<Photo> loadMore(List<Photo> list, int headIndex, boolean headDirection) {
        if ((headDirection && photosPresenter.getAdapter().getRealItemCount() < headIndex)
                || (!headDirection && photosPresenter.getAdapter().getRealItemCount() < headIndex + list.size())) {
            return new ArrayList<>();
        }

        if (!headDirection && photosPresenter.canLoadMore()) {
            photosPresenter.loadMore(getContext(), false, query);
        }
        if (!recyclerView.canScrollVertically(1) && photosPresenter.isLoading()) {
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

    // photo.

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
        if (list.size() == 0) {
            refreshPager();
        } else {
            loadPresenter.setNormalState();
        }
    }

    public String getOrder() {
        return photosPresenter.getOrder();
    }

    // interface.

    // on click listener.

    @OnClick(R.id.container_loading_view_large_feedbackBtn)
    void retryRefresh() {
        photosPresenter.initRefresh(getContext(), query);
    }

    // on refresh an load listener.

    @Override
    public void onRefresh() {
        photosPresenter.refreshNew(getContext(), false, query);
    }

    @Override
    public void onLoad() {
        photosPresenter.loadMore(getContext(), false, query);
    }

    // on scroll listener.

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            scrollPresenter.autoLoad(dy);
        }
    };

    // on collection changed listener.

    @Override
    public void onAddCollection(Collection c) {
        // do nothing.
    }

    @Override
    public void onUpdateCollection(Collection c, User u, Photo p) {
        photosPresenter.getAdapter().updatePhoto(p, true, false);
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
            feedbackText.setText(feedback);
            loadPresenter.setFailedState();
        }
    }

    // pager view.

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable(String.valueOf(getId()), new SavedState(this));
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        SavedState ss = bundle.getParcelable(String.valueOf(getId()));
        if (ss != null) {
            photosPresenter.setOrder(ss.order);
            photosPresenter.setPage(ss.page);
            photosPresenter.setPageList(ss.pageList);
            photosPresenter.setOver(ss.over);
        } else {
            refreshPager();
        }
    }

    @Override
    public void checkToRefresh() { // interface
        if (pagerPresenter.checkNeedRefresh()) {
            pagerPresenter.refreshPager();
        }
    }

    @Override
    public boolean checkNeedRefresh() {
        return photosPresenter.getAdapter().getRealItemCount() <= 0
                && !photosPresenter.isRefreshing() && !photosPresenter.isLoading();
    }

    @Override
    public boolean checkNeedBackToTop() {
        return scrollPresenter.needBackToTop();
    }

    @Override
    public void refreshPager() {
        photosPresenter.initRefresh(getContext(), query);
    }

    @Override
    public void setSelected(boolean selected) {
        pagerPresenter.setSelected(selected);
    }

    @Override
    public void scrollToPageTop() { // interface.
        scrollPresenter.scrollToTop();
    }

    @Override
    public void cancelRequest() {
        photosPresenter.cancelRequest();
        if (Constants.SHOW_ADS) {
            onDestroy();
        }
    }

    @Override
    public void setKey(String key) {
        photosPresenter.setOrder(key);
    }

    @Override
    public String getKey() {
        return photosPresenter.getPhotosOrder();
    }

    @Override
    public int getItemCount() {
        if (loadPresenter.getLoadState() != LoadObject.NORMAL_STATE) {
            return 0;
        } else {
            return photosPresenter.getAdapter().getRealItemCount();
        }
    }

    @Override
    public boolean canSwipeBack(int dir) {
        return false;
    }

    @Override
    public boolean isNormalState() {
        return loadPresenter.getLoadState() == LoadModel.NORMAL_STATE;
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
        if (activity != null && pagerPresenter.isSelected()) {
            DisplayUtils.setNavigationBarStyle(
                    activity, false, activity.hasTranslucentNavigationBar());
        }
        animShow(progressView);
        animHide(feedbackContainer);
        animHide(refreshLayout);
    }

    @Override
    public void setFailedState(@Nullable BaseActivity activity, int old) {
        animShow(feedbackContainer);
        animHide(progressView);
        animHide(refreshLayout);
    }

    @Override
    public void setNormalState(@Nullable BaseActivity activity, int old) {
        if (activity != null && pagerPresenter.isSelected()) {
            DisplayUtils.setNavigationBarStyle(
                    activity, true, activity.hasTranslucentNavigationBar());
        }
        animShow(refreshLayout);
        animHide(progressView);
        animHide(feedbackContainer);
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
            photosPresenter.loadMore(getContext(), false, query);
        }
        if (!recyclerView.canScrollVertically(-1)) {
            scrollPresenter.setToTop(true);
        } else {
            scrollPresenter.setToTop(false);
        }
        if (!recyclerView.canScrollVertically(1) && photosPresenter.isLoading()) {
            refreshLayout.setLoading(true);
        }
    }

    @Override
    public boolean needBackToTop() {
        return !scrollPresenter.isToTop()
                && loadPresenter.getLoadState() == LoadObject.NORMAL_STATE;
    }
}
