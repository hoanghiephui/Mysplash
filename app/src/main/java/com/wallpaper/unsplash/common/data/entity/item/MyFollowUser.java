package com.wallpaper.unsplash.common.data.entity.item;

import com.wallpaper.unsplash.common.data.entity.unsplash.User;

/**
 * My follow user.
 *
 * The item model for {@link com.wallpaper.unsplash.common.ui.adapter.MyFollowAdapter}.
 *
 * */

public class MyFollowUser {

    public boolean requesting;
    public boolean switchTo;
    public User user;

    public MyFollowUser(User u) {
        this.requesting = false;
        this.switchTo = false;
        this.user = u;
    }
}
