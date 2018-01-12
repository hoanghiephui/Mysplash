package com.wallpaper.unsplash.common.interfaces.presenter;

/**
 * Drawer presenter.
 *
 * Presenter for {@link com.wallpaper.unsplash.common.interfaces.view.DrawerView}.
 *
 * */

public interface DrawerPresenter {

    void touchNavItem(int id);

    int getCheckedItemId();
    void setCheckedItemId(int id);
}
