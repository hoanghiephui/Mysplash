package com.wallpapers.unsplash.common.interfaces.presenter;

import android.content.Context;
import android.view.View;

/**
 * Popup manage presenter.
 * <p>
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.PopupManageView}.
 */

public interface PopupManagePresenter {

    void showPopup(Context c, View anchor, String value, int position);
}
