package com.wallpaper.unsplash.common.interfaces.view;

import com.wallpaper.unsplash.common.data.entity.unsplash.FollowingFeed;

/**
 * Following view.
 *
 * A view which can request
 * {@link FollowingFeed} and show them.
 *
 * */

public interface FollowingView {

    void setRefreshing(boolean refreshing);
    void setLoading(boolean loading);

    void setPermitRefreshing(boolean permit);
    void setPermitLoading(boolean permit);

    void initRefreshStart();
    void requestFollowingFeedSuccess();
    void requestFollowingFeedFailed(String feedback);
}
