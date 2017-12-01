package com.wallpapers.unsplash.common.interfaces.model;

import com.wallpapers.unsplash.common.data.service.FeedService;
import com.wallpapers.unsplash.common.ui.adapter.FollowingAdapter;

/**
 * Following model.
 *
 * Model for {@link com.wallpapers.unsplash.common.interfaces.view.FollowingView}.
 *
 * */

public interface FollowingModel {

    FollowingAdapter getAdapter();
    FeedService getService();

    // manage HTTP request parameters.

    /** {@link com.wallpapers.unsplash.common.data.api.FeedApi#getFollowingFeed(String)} */
    String getFirstPage();
    String getNextPage();
    void setNextPage(String nextPage);

    // control load state.

    boolean isRefreshing();
    void setRefreshing(boolean refreshing);

    boolean isLoading();
    void setLoading(boolean loading);

    /** The flag to mark the photos already load over. */
    boolean isOver();
    void setOver(boolean over);
}
