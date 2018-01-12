package com.wallpaper.unsplash.common.interfaces.presenter;

import com.wallpaper.unsplash.common.data.entity.unsplash.User;

/**
 * User presenter.
 *
 * Presenter for {@link com.wallpaper.unsplash.common.interfaces.view.UserView}.
 *
 * */

public interface UserPresenter {

    void requestUser();
    void followUser();
    void cancelFollowUser();
    void cancelRequest();

    void setUser(User u);
    User getUser();
}
