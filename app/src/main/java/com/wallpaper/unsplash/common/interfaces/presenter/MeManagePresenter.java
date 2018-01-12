package com.wallpaper.unsplash.common.interfaces.presenter;

import com.wallpaper.unsplash.common.basic.activity.BaseActivity;

/**
 * Auth response presenter.
 *
 * Presenter for {@link com.wallpaper.unsplash.common.interfaces.view.MeManageView}.
 *
 * */

public interface MeManagePresenter {

    void touchMeAvatar(BaseActivity a);
    void touchMeButton(BaseActivity a);

    /** {@link com.wallpaper.unsplash.common.utils.manager.AuthManager#listenerList} */
    void responseWriteAccessToken();
    void responseWriteUserInfo();
    void responseWriteAvatarPath();
    void responseLogout();
}
