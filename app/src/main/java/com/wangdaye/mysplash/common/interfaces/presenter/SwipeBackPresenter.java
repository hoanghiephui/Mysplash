package com.wangdaye.mysplash.common.interfaces.presenter;

import com.wangdaye.mysplash.common.ui.widget.SwipeBackCoordinatorLayout;

/**
 * Swipe back presenter.
 *
 * Presenter for {@link com.wangdaye.mysplash.common.interfaces.view.SwipeBackView}.
 *
 * */

public interface SwipeBackPresenter {

    boolean checkCanSwipeBack(@SwipeBackCoordinatorLayout.DirectionRule int dir);
}
