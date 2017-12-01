package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;
import com.wallpapers.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;

/**
 * Swipe back manage presenter.
 *
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.SwipeBackManageView}.
 *
 * */

public interface SwipeBackManagePresenter {

    boolean checkCanSwipeBack(@SwipeBackCoordinatorLayout.DirectionRule int dir);
    void swipeBackFinish(MysplashActivity a, @SwipeBackCoordinatorLayout.DirectionRule int dir);
}
