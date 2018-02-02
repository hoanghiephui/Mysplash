package com.wallpapers.unsplash.collection.presenter.activity;

import android.content.Context;
import android.view.View;

import com.wallpapers.unsplash.collection.view.activity.CollectionActivity;
import com.wallpapers.unsplash.common.interfaces.presenter.PopupManagePresenter;
import com.wallpapers.unsplash.common.interfaces.view.PopupManageView;
import com.wallpapers.unsplash.common.ui.popup.CollectionMenuPopupWindow;

/**
 * Popup manage implementor.
 */

public class PopupManageImplementor
        implements PopupManagePresenter,
        CollectionMenuPopupWindow.OnSelectItemListener {

    private PopupManageView view;

    public PopupManageImplementor(PopupManageView view) {
        this.view = view;
    }

    @Override
    public void showPopup(Context c, View anchor, String value, final int position) {
        CollectionMenuPopupWindow window = new CollectionMenuPopupWindow(
                c, anchor, ((CollectionActivity) c).getCollection());
        window.setOnSelectItemListener(this);
    }

    // interface.

    // on select item swipeListener.

    @Override
    public void onSelectItem(int id) {
        view.responsePopup(null, id);
    }
}
