package com.wallpapers.unsplash.common.interfaces.presenter;

import android.content.Context;

import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.ui.adapter.FollowingAdapter;

/**
 * Following presenter.
 *
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.FollowingView}.
 *
 * */

public interface FollowingPresenter {

    // HTTP request.

    void requestFollowingFeed(Context c, boolean refresh);
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

    FollowingAdapter getAdapter();
}
