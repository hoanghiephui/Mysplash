package com.wallpaper.unsplash.user.presenter.activity;

import android.content.Context;
import android.view.View;

import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.interfaces.presenter.PopupManagePresenter;
import com.wallpaper.unsplash.common.interfaces.view.PopupManageView;
import com.wallpaper.unsplash.common.ui.popup.PhotoOrderPopupWindow;
import com.wallpaper.unsplash.common.utils.helper.NotificationHelper;

/**
 * Popup manage implementor.
 * */

public class PopupManageImplementor
        implements PopupManagePresenter {

    private PopupManageView view;

    public PopupManageImplementor(PopupManageView view) {
        this.view = view;
    }

    @Override
    public void showPopup(Context c, View anchor, String value, final int position) {
        if (position != 2) {
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
}