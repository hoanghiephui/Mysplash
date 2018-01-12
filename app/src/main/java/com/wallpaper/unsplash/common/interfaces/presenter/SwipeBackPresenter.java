package com.wallpaper.unsplash.common.interfaces.presenter;

import com.wallpaper.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;

/**
 * Swipe back presenter.
 *
 * Presenter for {@link com.wallpaper.unsplash.common.interfaces.view.SwipeBackView}.
 *
 * */

public interface SwipeBackPresenter {

    boolean checkCanSwipeBack(@SwipeBackCoordinatorLayout.DirectionRule int dir);
}
