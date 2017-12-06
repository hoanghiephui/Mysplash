package com.wallpapers.unsplash.common.interfaces.presenter;

import android.content.Context;

import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.ui.adapter.CollectionAdapter;

/**
 * Collections presenter.
 *
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.CollectionsView}.
 *
 * */

public interface CollectionsPresenter {

    // HTTP request.

    void requestCollections(Context c, @UnsplashApplication.PageRule int page, boolean refresh, String query);
    void cancelRequest();

    // load data interface.

    /**
     * The param notify is used to control the SwipeRefreshLayout. If set true, the
     * SwipeRefreshLayout will show the refresh animation.
     * */
    void refreshNew(Context c, boolean notify, String query);
    void loadMore(Context c, boolean notify, String query);
    void initRefresh(Context c, String query);

    boolean canLoadMore();
    boolean isRefreshing();
    boolean isLoading();

    // manage HTTP request parameters.

    Object getRequestKey();
    void setRequestKey(Object k);

    void setType(int key);
    int getType();

    void setPage(@UnsplashApplication.PageRule int page);
    void setOver(boolean over);

    void setActivityForAdapter(BaseActivity a);
    CollectionAdapter getAdapter();
}
