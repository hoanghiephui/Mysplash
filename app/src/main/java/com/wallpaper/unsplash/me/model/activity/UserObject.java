package com.wallpaper.unsplash.me.model.activity;

import com.wallpaper.unsplash.common.data.entity.unsplash.User;
import com.wallpaper.unsplash.common.data.service.FeedService;
import com.wallpaper.unsplash.common.data.service.UserService;
import com.wallpaper.unsplash.common.interfaces.model.UserModel;

/**
 * User object.
 * */

public class UserObject
        implements UserModel {

    private UserService userService;
    private User user = null;

    public UserObject() {
        userService = UserService.getService();
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

    public FeedService getFeedService() {
        return null;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }
}
