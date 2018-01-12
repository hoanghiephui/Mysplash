package com.wallpaper.unsplash.user.presenter.widget;

import com.wallpaper.unsplash.common.interfaces.presenter.SwipeBackPresenter;
import com.wallpaper.unsplash.common.interfaces.view.SwipeBackView;

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

