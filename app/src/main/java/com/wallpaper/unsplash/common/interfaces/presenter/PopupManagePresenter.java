package com.wallpaper.unsplash.common.interfaces.presenter;

import android.content.Context;
import android.view.View;

/**
 * Popup manage presenter.
 *
 * Presenter for {@link com.wallpaper.unsplash.common.interfaces.view.PopupManageView}.
 *
 * */

public interface PopupManagePresenter {

    void showPopup(Context c, View anchor, String value, int position);
}
