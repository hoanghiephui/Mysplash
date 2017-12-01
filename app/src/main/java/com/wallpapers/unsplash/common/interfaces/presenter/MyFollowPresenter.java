package com.wallpapers.unsplash.common.interfaces.presenter;

import android.content.Context;

import com.wallpapers.unsplash.Unsplash;
import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;
import com.wallpapers.unsplash.common.interfaces.model.MyFollowModel;
import com.wallpapers.unsplash.common.ui.adapter.MyFollowAdapter;

/**
 * My follow implementor.
 *
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.MyFollowView}.
 *
 * */

public interface MyFollowPresenter {

    // HTTP request.

    void requestMyFollow(Context c, @Unsplash.PageRule int page, boolean refresh);
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

    // load data interface.

    /** {@link MyFollowModel#getDeltaValue()} */
    int getDeltaValue();
    void setDeltaValue(int delta);

    void setActivityForAdapter(MysplashActivity a);
    MyFollowAdapter getAdapter();
}