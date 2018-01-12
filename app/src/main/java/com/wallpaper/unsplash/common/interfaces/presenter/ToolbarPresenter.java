package com.wallpaper.unsplash.common.interfaces.presenter;

import com.wallpaper.unsplash.common.basic.activity.BaseActivity;

/**
 * Toolbar presenter.
 *
 * Presenter to control the behavior of {@link android.support.v7.widget.Toolbar}.
 *
 * */

public interface ToolbarPresenter {

    void touchNavigatorIcon(BaseActivity a);
    void touchToolbar(BaseActivity a);
    boolean touchMenuItem(BaseActivity a, int itemId);
}
