package com.wallpapers.unsplash.common.interfaces.model;

import com.wallpapers.unsplash.common.ui.adapter.NotificationAdapter;

/**
 * Notifications model.
 *
 * Model for {@link com.wallpapers.unsplash.common.interfaces.view.NotificationsView}.
 *
 * */

public interface NotificationsModel {

    NotificationAdapter getAdapter();

    // control load state.

    boolean isRefreshing();
    void setRefreshing(boolean refreshing);

    boolean isLoading();
    void setLoading(boolean loading);

    /** The flag to mark the photos already load over. */
    boolean isOver();
}
