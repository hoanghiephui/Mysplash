package com.wallpaper.unsplash.common.interfaces.presenter;

import android.content.Context;

import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.ui.adapter.PhotoAdapter;

/**
 * Trending presenter.
 *
 * Presenter for {@link com.wallpaper.unsplash.common.interfaces.view.TrendingView}.
 *
 * */

public interface TrendingPresenter {

    // HTTP request.

    void requestTrendingFeed(Context c, boolean refresh);
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

    void setNextPage(String nextPage);
    String getNextPage();

    void setOver(boolean over);

    void setActivityForAdapter(BaseActivity a);
    int getAdapterItemCount();

    PhotoAdapter getAdapter();
}
