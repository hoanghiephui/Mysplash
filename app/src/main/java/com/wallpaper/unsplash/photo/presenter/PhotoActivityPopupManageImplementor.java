package com.wallpaper.unsplash.photo.presenter;

import android.content.Context;
import android.view.View;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.data.entity.unsplash.Photo;
import com.wallpaper.unsplash.common.interfaces.presenter.PopupManagePresenter;
import com.wallpaper.unsplash.common.interfaces.view.PopupManageView;
import com.wallpaper.unsplash.common.ui.popup.PhotoMenuPopupWindow;
import com.wallpaper.unsplash.photo.view.activity.PhotoActivity;

/**
 * Photo activity popup manage implementor.
 * */

public class PhotoActivityPopupManageImplementor
        implements PopupManagePresenter,
        PhotoMenuPopupWindow.OnSelectItemListener {

    private PopupManageView view;

    public PhotoActivityPopupManageImplementor(PopupManageView view) {
        this.view = view;
    }

    @Override
    public void showPopup(Context c, View anchor, String value, int position) {
        BaseActivity activity = UnsplashApplication.getInstance().getTopActivity();
        if (activity != null && activity instanceof PhotoActivity) {
            Photo photo = ((PhotoActivity) activity).getPhoto();
            if (photo != null) {
                PhotoMenuPopupWindow popup = new PhotoMenuPopupWindow(c, anchor);
                popup.setOnSelectItemListener(this);
            }
        }
    }

    @Override
    public void onSelectItem(int id) {
        view.responsePopup(null, id);
    }
}
