package com.wallpaper.unsplash.common.interfaces.model;

import com.wallpaper.unsplash.common.data.entity.unsplash.User;
import com.wallpaper.unsplash.common.data.service.FeedService;
import com.wallpaper.unsplash.common.data.service.UserService;

/**
 * User model.
 *
 * Model for {@link com.wallpaper.unsplash.common.interfaces.view.UserView}.
 *
 * */

public interface UserModel {

    UserService getUserService();
    FeedService getFeedService();

    User getUser();
    void setUser(User user);
}
