package com.wallpaper.unsplash.common.interfaces.presenter;

import android.content.Context;

import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.ui.adapter.NotificationAdapter;

/**
 * Notification presenter.
 *
 * Presenter for {@link com.wallpaper.unsplash.common.interfaces.view.NotificationsView}.
 *
 * */

public interface NotificationsPresenter {

    // HTTP request.

    void requestNotifications(Context c, boolean refresh);
    void cancelRequest();

    // load data interface.

    /**
     * The param notify is used to control the SwipeRefreshLayout. If set true, the
     * SwipeRefreshLayout will show the refresh animation.
     * */
    void loadMore(Context c, boolean notify);
    void initRefresh(Context c);

    boolean canLoadMore();
    boolean isRefreshing();
    boolean isLoading();

    // manage HTTP request parameters.

    void setActivityForAdapter(BaseActivity a);
    NotificationAdapter getAdapter();
}
