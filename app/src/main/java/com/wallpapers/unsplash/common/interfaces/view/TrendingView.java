package com.wallpapers.unsplash.common.interfaces.view;

import com.wallpapers.unsplash.common.data.entity.unsplash.TrendingFeed;

/**
 * Trending view.
 *
 * A view which can request
 * {@link TrendingFeed} and show them.
 *
 * */

public interface TrendingView {

    void setRefreshing(boolean refreshing);
    void setLoading(boolean loading);

    void setPermitRefreshing(boolean permit);
    void setPermitLoading(boolean permit);

    void initRefreshStart();
    void requestTrendingFeedSuccess();
    void requestTrendingFeedFailed(String feedback);
}
