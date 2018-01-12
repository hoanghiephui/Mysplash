package com.wallpaper.unsplash.common.interfaces.presenter;

import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;

/**
 * Swipe back manage presenter.
 *
 * Presenter for {@link com.wallpaper.unsplash.common.interfaces.view.SwipeBackManageView}.
 *
 * */

public interface SwipeBackManagePresenter {

    boolean checkCanSwipeBack(@SwipeBackCoordinatorLayout.DirectionRule int dir);
    void swipeBackFinish(BaseActivity a, @SwipeBackCoordinatorLayout.DirectionRule int dir);
}
