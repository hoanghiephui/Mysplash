package com.wallpapers.unsplash.main.presenter.fragment;

import android.content.Context;
import android.view.View;

import com.wallpapers.unsplash.common.interfaces.presenter.PopupManagePresenter;
import com.wallpapers.unsplash.common.interfaces.view.PopupManageView;
import com.wallpapers.unsplash.common.ui.popup.PhotoOrderPopupWindow;

/**
 * Category fragment popup manage implementor.
 */

public class CategoryFragmentPopupManageImplementor
        implements PopupManagePresenter,
        PhotoOrderPopupWindow.OnPhotoOrderChangedListener {

    private PopupManageView view;

    public CategoryFragmentPopupManageImplementor(PopupManageView view) {
        this.view = view;
    }

    @Override
    public void showPopup(Context c, View anchor, String value, int position) {
        PhotoOrderPopupWindow window = new PhotoOrderPopupWindow(
                c,
                anchor,
                value,
                PhotoOrderPopupWindow.CATEGORY_TYPE);
        window.setOnPhotoOrderChangedListener(this);
    }

    @Override
    public void onPhotoOrderChange(String orderValue) {
        view.responsePopup(orderValue, 0);
    }
}