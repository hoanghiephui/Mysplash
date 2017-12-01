package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;

/**
 * Auth response presenter.
 *
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.MeManageView}.
 *
 * */

public interface MeManagePresenter {

    void touchMeAvatar(MysplashActivity a);
    void touchMeButton(MysplashActivity a);

    /** {@link com.wallpapers.unsplash.common.utils.manager.AuthManager#listenerList} */
    void responseWriteAccessToken();
    void responseWriteUserInfo();
    void responseWriteAvatarPath();
    void responseLogout();
}
