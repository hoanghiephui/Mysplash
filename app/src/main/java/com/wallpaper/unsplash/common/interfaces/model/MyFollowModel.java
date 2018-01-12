package com.wallpaper.unsplash.common.interfaces.model;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.common.data.service.UserService;
import com.wallpaper.unsplash.common.ui.adapter.MyFollowAdapter;

/**
 * My follow model.
 *
 * Model for {@link com.wallpaper.unsplash.common.interfaces.view.MyFollowView}.
 *
 * */

public interface MyFollowModel {

    MyFollowAdapter getAdapter();
    UserService getService();

    // manage HTTP request parameters.

    int getFollowType();

    int getUsersPage();
    void setUsersPage(@UnsplashApplication.PageRule int page);

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
