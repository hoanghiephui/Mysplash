package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;

/**
 * Swipe back presenter.
 * <p>
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.SwipeBackView}.
 */

public interface SwipeBackPresenter {

    boolean checkCanSwipeBack(@SwipeBackCoordinatorLayout.DirectionRule int dir);
}
