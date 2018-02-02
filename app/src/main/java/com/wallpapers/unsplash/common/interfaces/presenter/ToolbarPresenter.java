package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.common.basic.activity.BaseActivity;

/**
 * Toolbar presenter.
 * <p>
 * Presenter to control the behavior of {@link android.support.v7.widget.Toolbar}.
 */

public interface ToolbarPresenter {

    void touchNavigatorIcon(BaseActivity a);

    void touchToolbar(BaseActivity a);

    boolean touchMenuItem(BaseActivity a, int itemId);
}
