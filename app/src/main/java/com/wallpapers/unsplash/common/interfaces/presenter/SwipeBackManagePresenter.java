package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;

/**
 * Swipe back manage presenter.
 * <p>
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.SwipeBackManageView}.
 */

public interface SwipeBackManagePresenter {

    boolean checkCanSwipeBack(@SwipeBackCoordinatorLayout.DirectionRule int dir);

    void swipeBackFinish(BaseActivity a, @SwipeBackCoordinatorLayout.DirectionRule int dir);
}
