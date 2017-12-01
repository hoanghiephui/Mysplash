package com.wangdaye.mysplash.common.interfaces.presenter;

import android.content.Context;
import android.view.View;

/**
 * Popup manage presenter.
 *
 * Presenter for {@link com.wangdaye.mysplash.common.interfaces.view.PopupManageView}.
 *
 * */

public interface PopupManagePresenter {

    void showPopup(Context c, View anchor, String value, int position);
}
