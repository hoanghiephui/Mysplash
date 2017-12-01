package com.wallpapers.unsplash.me.presenter.widget;

import com.wallpapers.unsplash.common.interfaces.presenter.SwipeBackPresenter;
import com.wallpapers.unsplash.common.interfaces.view.SwipeBackView;

/**
 * Swipe back implementor.
 * */

public class SwipeBackImplementor
        implements SwipeBackPresenter {

    private SwipeBackView view;

    public SwipeBackImplementor(SwipeBackView view) {
        this.view = view;
    }

    @Override
    public boolean checkCanSwipeBack(int dir) {
        return view.checkCanSwipeBack(dir);
    }
}
