package com.wallpapers.unsplash.me.presenter.activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.interfaces.presenter.PopupManagePresenter;
import com.wallpapers.unsplash.common.interfaces.view.PopupManageView;
import com.wallpapers.unsplash.common.ui.popup.MeMenuPopupWindow;
import com.wallpapers.unsplash.common.ui.popup.PhotoOrderPopupWindow;
import com.wallpapers.unsplash.common.utils.helper.NotificationHelper;
import com.wallpapers.unsplash.common.utils.ShareUtils;
import com.wallpapers.unsplash.common.utils.helper.IntentHelper;
import com.wallpapers.unsplash.common.utils.manager.AuthManager;

/**
 * Popup manage implementor.
 * */

public class PopupManageImplementor
        implements PopupManagePresenter,
        MeMenuPopupWindow.OnSelectItemListener {

    private PopupManageView view;

    public PopupManageImplementor(PopupManageView view) {
        this.view = view;
    }

    @Override
    public void showPopup(Context c, View anchor, String value, final int position) {
        if (position < 0) {
            MeMenuPopupWindow window = new MeMenuPopupWindow(c, anchor);
            window.setOnSelectItemListener(this);
        } else if (position != 2) {
            PhotoOrderPopupWindow window = new PhotoOrderPopupWindow(
                    c,
                    anchor,
                    value,
                    PhotoOrderPopupWindow.NO_RANDOM_TYPE);
            window.setOnPhotoOrderChangedListener(new PhotoOrderPopupWindow.OnPhotoOrderChangedListener() {
                @Override
                public void onPhotoOrderChange(String orderValue) {
                    view.responsePopup(orderValue, position);
                }
            });
        } else {
            NotificationHelper.showSnackbar(c.getString(R.string.feedback_no_filter));
        }
    }

    // interface.

    // on select item swipeListener.

    @Override
    public void onSelectItem(int id) {
        BaseActivity a = UnsplashApplication.getInstance().getTopActivity();
        if (a == null) {
            return;
        }
        switch (id) {
            case MeMenuPopupWindow.ITEM_SUBMIT:
                IntentHelper.startWebActivity(a, UnsplashApplication.UNSPLASH_SUBMIT_URL);
                break;

            case MeMenuPopupWindow.ITEM_PORTFOLIO:
                if (AuthManager.getInstance().isAuthorized()
                        && AuthManager.getInstance().getMe() != null) {
                    String url = AuthManager.getInstance().getMe().portfolio_url;
                    if (!TextUtils.isEmpty(url)) {
                        IntentHelper.startWebActivity(a, url);
                    } else {
                        NotificationHelper.showSnackbar(a.getString(R.string.feedback_portfolio_is_null));
                    }
                }
                break;

            case MeMenuPopupWindow.ITEM_SHARE:
                if (AuthManager.getInstance().isAuthorized()
                        && AuthManager.getInstance().getUser() != null) {
                    ShareUtils.shareUser(AuthManager.getInstance().getUser());
                }
                break;
        }
    }
}
