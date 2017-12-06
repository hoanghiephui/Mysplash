package com.wallpapers.unsplash.collection.presenter.activity;

import com.wallpapers.unsplash.common.interfaces.presenter.SwipeBackManagePresenter;
import com.wallpapers.unsplash.common.interfaces.view.SwipeBackManageView;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;

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
    public void swipeBackFinish(BaseActivity a, int dir) {
        a.finishActivity(dir);
    }
}
