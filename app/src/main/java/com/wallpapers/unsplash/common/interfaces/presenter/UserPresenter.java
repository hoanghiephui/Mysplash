package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.common.data.entity.unsplash.User;

/**
 * User presenter.
 * <p>
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.UserView}.
 */

public interface UserPresenter {

    void requestUser();

    void followUser();

    void cancelFollowUser();

    void cancelRequest();

    void setUser(User u);

    User getUser();
}
