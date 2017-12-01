package com.wallpapers.unsplash.main.presenter.activity;

import com.wallpapers.unsplash.common.interfaces.model.DrawerModel;
import com.wallpapers.unsplash.common.interfaces.presenter.DrawerPresenter;
import com.wallpapers.unsplash.common.interfaces.view.DrawerView;

/**
 * Drawer implementor.
 *
 * */

public class DrawerImplementor
        implements DrawerPresenter {

    private DrawerModel model;
    private DrawerView view;

    public DrawerImplementor(DrawerModel model, DrawerView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void touchNavItem(int id) {
        int oldId = model.getCheckedItemId();
        if (oldId != id) {
            view.touchNavItem(id);
        }
    }

    @Override
    public int getCheckedItemId() {
        return model.getCheckedItemId();
    }

    @Override
    public void setCheckedItemId(int id) {
        model.setCheckedItemId(id);
        view.setCheckedItem(id);
    }
}
