package com.wallpaper.unsplash.common.interfaces.model;

import com.wallpaper.unsplash.common.data.service.FeedService;
import com.wallpaper.unsplash.common.ui.adapter.PhotoAdapter;

/**
 * Trending model.
 *
 * Model for {@link com.wallpaper.unsplash.common.interfaces.view.TrendingView}.
 *
 * */

public interface TrendingModel {

    PhotoAdapter getAdapter();
    FeedService getService();

    // manage HTTP request parameters.

    /** {@link com.wallpaper.unsplash.common.data.api.FeedApi#getTrendingFeed(String)} */
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
