package com.wallpaper.unsplash.common.interfaces.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.wallpaper.unsplash.UnsplashApplication;

/**
 * Search presenter.
 *
 * Presenter for {@link com.wallpaper.unsplash.common.interfaces.view.SearchView}.
 *
 * */

public interface SearchPresenter {

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

    void setQuery(String key);
    String getQuery();

    void setPage(@UnsplashApplication.PageRule int page);
    void setOver(boolean over);

    int getAdapterItemCount();
    RecyclerView.Adapter getAdapter();
}
