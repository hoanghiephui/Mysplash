package com.wallpapers.unsplash.common.interfaces.model;

import com.wallpapers.unsplash.common.data.entity.unsplash.User;
import com.wallpapers.unsplash.common.data.service.FeedService;
import com.wallpapers.unsplash.common.data.service.UserService;

/**
 * User model.
 *
 * Model for {@link com.wallpapers.unsplash.common.interfaces.view.UserView}.
 *
 * */

public interface UserModel {

    UserService getUserService();
    FeedService getFeedService();

    User getUser();
    void setUser(User user);
}
