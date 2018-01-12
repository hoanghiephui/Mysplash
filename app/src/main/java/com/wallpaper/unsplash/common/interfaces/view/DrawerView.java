package com.wallpaper.unsplash.common.interfaces.view;

/**
 * Drawer view.
 *
 * A view which has {@link android.support.v4.widget.DrawerLayout}
 * and {@link android.support.design.widget.NavigationView}.
 *
 * */

public interface DrawerView {

    void touchNavItem(int id);
    void setCheckedItem(int id);
}
