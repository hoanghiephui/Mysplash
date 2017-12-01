package com.wangdaye.mysplash.common.interfaces.presenter;

/**
 * Drawer presenter.
 *
 * Presenter for {@link com.wangdaye.mysplash.common.interfaces.view.DrawerView}.
 *
 * */

public interface DrawerPresenter {

    void touchNavItem(int id);

    int getCheckedItemId();
    void setCheckedItemId(int id);
}
