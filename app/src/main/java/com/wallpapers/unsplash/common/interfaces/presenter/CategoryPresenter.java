package com.wallpapers.unsplash.common.interfaces.presenter;

import android.content.Context;

import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.ui.adapter.PhotoAdapter;

import java.util.List;

/**
 * Category presenter.
 *
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.CategoryView}.
 *
 * */

public interface CategoryPresenter {

    // HTTP request.

    void requestPhotos(Context c, @UnsplashApplication.PageRule int page, boolean refresh);
    void cancelRequest();

    // load data interface.

    /**
     * The param notify is used to control the SwipeRefreshLayout. If set true, the
     * SwipeRefreshLayout will show the refresh animation.
     * */
    void refreshNew(Context c, boolean notify);
    void loadMore(Context c, boolean notify);
    void initRefresh(Context c);

    boolean canLoadMore();
    boolean isRefreshing();
    boolean isLoading();

    // manage HTTP request parameters.

    void setCategory(int key);
    void setOrder(String key);
    String getOrder();

    void setPage(@UnsplashApplication.PageRule int page);
    void setPageList(List<Integer> pageList);

    void setOver(boolean over);

    void setActivityForAdapter(BaseActivity a);
    int getAdapterItemCount();

    PhotoAdapter getAdapter();
}
