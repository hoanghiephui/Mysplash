package com.wangdaye.mysplash.me.presenter.activity;

import com.wangdaye.mysplash.common.interfaces.presenter.SwipeBackManagePresenter;
import com.wangdaye.mysplash.common.interfaces.view.SwipeBackManageView;
import com.wangdaye.mysplash.common._basic.activity.MysplashActivity;

/**
 * Swipe back manage implementor.
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

