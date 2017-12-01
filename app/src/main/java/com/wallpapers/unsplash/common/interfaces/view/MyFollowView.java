package com.wallpapers.unsplash.common.interfaces.view;

/**
 * My follow view.
 *
 * A view which can request {@link com.wallpapers.unsplash.common.data.entity.unsplash.User} who
 * following user or followed by user and show them.
 *
 * */

public interface MyFollowView {

    void setRefreshing(boolean refreshing);
    void setLoading(boolean loading);

    void setPermitRefreshing(boolean permit);
    void setPermitLoading(boolean permit);

    void initRefreshStart();
    void requestMyFollowSuccess();
    void requestMyFollowFailed(String feedback);
}
