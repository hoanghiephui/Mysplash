package com.wallpaper.unsplash.common.interfaces.view;

/**
 * Me manage view.
 *
 * A view can manage {@link com.wallpaper.unsplash.common.data.entity.unsplash.Me} and user's own
 * {@link com.wallpaper.unsplash.common.data.entity.unsplash.User} information.
 *
 * */

public interface MeManageView {

    void drawMeAvatar();
    void drawMeTitle();
    void drawMeSubtitle();
    void drawMeButton();
}
