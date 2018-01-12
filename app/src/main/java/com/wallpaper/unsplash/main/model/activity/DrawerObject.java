package com.wallpaper.unsplash.main.model.activity;

import com.wallpaper.unsplash.common.interfaces.model.DrawerModel;

/**
 * Drawer object.
 *
 * */

public class DrawerObject
        implements DrawerModel {

    private int checkedId;

    public DrawerObject(int id) {
        checkedId = id;
    }

    @Override
    public int getCheckedItemId() {
        return checkedId;
    }

    @Override
    public void setCheckedItemId(int id) {
        checkedId = id;
    }
}
