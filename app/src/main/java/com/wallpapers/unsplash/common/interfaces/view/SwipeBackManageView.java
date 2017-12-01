package com.wallpapers.unsplash.common.interfaces.view;

import com.wallpapers.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;

/**
 * Swipe back manage view.
 *
 * A view which can control {@link SwipeBackView} to respond the operation from
 * {@link com.wallpapers.unsplash.common.ui.widget.SwipeBackCoordinatorLayout}.
 *
 * */

public interface SwipeBackManageView {

    boolean checkCanSwipeBack(@SwipeBackCoordinatorLayout.DirectionRule int dir);
}
