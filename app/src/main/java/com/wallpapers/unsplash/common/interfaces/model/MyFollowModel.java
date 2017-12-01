package com.wallpapers.unsplash.common.interfaces.model;

import com.wallpapers.unsplash.Unsplash;
import com.wallpapers.unsplash.common.data.service.UserService;
import com.wallpapers.unsplash.common.ui.adapter.MyFollowAdapter;

/**
 * My follow model.
 *
 * Model for {@link com.wallpapers.unsplash.common.interfaces.view.MyFollowView}.
 *
 * */

public interface MyFollowModel {

    MyFollowAdapter getAdapter();
    UserService getService();

    // manage HTTP request parameters.

    int getFollowType();

    int getUsersPage();
    void setUsersPage(@Unsplash.PageRule int page);

    // control load state.

    boolean isRefreshing();
    void setRefreshing(boolean refreshing);

    boolean isLoading();
    void setLoading(boolean loading);

    /** The flag to mark the photos already load over. */
    boolean isOver();
    void setOver(boolean over);

    // record.

    /**
     * +2 --> added 2 followers / followed 2 user.
     * -2 --> reduced 2 followers / canceled follow 2 user.
     * */
    int getDeltaValue();
    void setDeltaValue(int delta);
}
