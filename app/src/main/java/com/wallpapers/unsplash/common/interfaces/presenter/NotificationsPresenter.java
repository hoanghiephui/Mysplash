package com.wallpapers.unsplash.common.interfaces.presenter;

import android.content.Context;

import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.ui.adapter.NotificationAdapter;

/**
 * Notification presenter.
 * <p>
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.NotificationsView}.
 */

public interface NotificationsPresenter {

    // HTTP request.

    void requestNotifications(Context c, boolean refresh);

    void cancelRequest();

    // load data interface.

    /**
     * The param notify is used to control the SwipeRefreshLayout. If set true, the
     * SwipeRefreshLayout will show the refresh animation.
     */
    void loadMore(Context c, boolean notify);

    void initRefresh(Context c);

    boolean canLoadMore();

    boolean isRefreshing();

    boolean isLoading();

    // manage HTTP request parameters.

    void setActivityForAdapter(BaseActivity a);

    NotificationAdapter getAdapter();
}