package com.wallpaper.unsplash.collection.presenter.activity;

import com.wallpaper.unsplash.common.interfaces.presenter.SwipeBackManagePresenter;
import com.wallpaper.unsplash.common.interfaces.view.SwipeBackManageView;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;

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
