package com.wallpapers.unsplash.common.interfaces.presenter;

/**
 * Drawer presenter.
 * <p>
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.DrawerView}.
 */

public interface DrawerPresenter {

    void touchNavItem(int id);

    int getCheckedItemId();

    void setCheckedItemId(int id);
}
