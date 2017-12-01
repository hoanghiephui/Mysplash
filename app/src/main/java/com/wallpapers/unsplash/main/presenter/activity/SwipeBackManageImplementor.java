package com.wallpapers.unsplash.main.presenter.activity;

import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;
import com.wallpapers.unsplash.common.interfaces.presenter.SwipeBackManagePresenter;
import com.wallpapers.unsplash.common.interfaces.view.SwipeBackManageView;

/**
 * Swipe back manage implementor.
 *
 * */

public class SwipeBackManageImplementor
        implements SwipeBackManagePresenter {

    private SwipeBackManageView view;

    public SwipeBackManageImplementor(SwipeBackManageView view) {
        this.view = view;
    }

    @Override
    public boolean checkCanSwipeBack(int dir) {
        return view.checkCanSwipeBack(dir);
    }

    @Override
    public void swipeBackFinish(MysplashActivity a, int dir) {
        a.finishActivity(dir);
    }
}
