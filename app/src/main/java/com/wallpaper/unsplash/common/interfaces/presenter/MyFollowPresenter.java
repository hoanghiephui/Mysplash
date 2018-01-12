package com.wallpaper.unsplash.common.interfaces.presenter;

import android.content.Context;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.interfaces.model.MyFollowModel;
import com.wallpaper.unsplash.common.ui.adapter.MyFollowAdapter;

/**
 * My follow implementor.
 *
 * Presenter for {@link com.wallpaper.unsplash.common.interfaces.view.MyFollowView}.
 *
 * */

public interface MyFollowPresenter {

    // HTTP request.

    void requestMyFollow(Context c, @UnsplashApplication.PageRule int page, boolean refresh);
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

    void setActivityForAdapter(BaseActivity a);
    MyFollowAdapter getAdapter();
}
