package com.wallpaper.unsplash.main.presenter.activity;

import android.support.design.widget.NavigationView;
import android.view.View;

import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.utils.helper.IntentHelper;
import com.wallpaper.unsplash.common.utils.manager.AuthManager;
import com.wallpaper.unsplash.common.interfaces.presenter.MeManagePresenter;
import com.wallpaper.unsplash.common.interfaces.view.MeManageView;
import com.wallpaper.unsplash.common.utils.helper.NotificationHelper;
import com.wallpaper.unsplash.user.view.activity.UserActivity;

/**
 * Me manage implementor.
 *
 * */

public class MeManageImplementor
        implements MeManagePresenter {

    private MeManageView view;

    public MeManageImplementor(MeManageView view) {
        this.view = view;
    }

    @Override
    public void touchMeAvatar(BaseActivity a) {
        NavigationView nav = a.findViewById(R.id.activity_main_navView);
        View header = nav.getHeaderView(0);
        IntentHelper.startMeActivity(
                a,
                header.findViewById(R.id.container_nav_header_avatar),
                UserActivity.PAGE_PHOTO);
    }

    @Override
    public void touchMeButton(BaseActivity a) {
        if (!AuthManager.getInstance().isAuthorized()) {
            IntentHelper.startLoginActivity(a);
        } else {
            AuthManager.getInstance().logout();
        }
    }

    @Override
    public void responseWriteAccessToken() {
        NotificationHelper.showSnackbar("Welcome back.");
        view.drawMeAvatar();
        view.drawMeTitle();
        view.drawMeSubtitle();
        view.drawMeButton();
    }

    @Override
    public void responseWriteUserInfo() {
        view.drawMeTitle();
        view.drawMeSubtitle();
    }

    @Override
    public void responseWriteAvatarPath() {
        view.drawMeAvatar();
    }

    @Override
    public void responseLogout() {
        view.drawMeAvatar();
        view.drawMeTitle();
        view.drawMeSubtitle();
        view.drawMeButton();
    }
}
