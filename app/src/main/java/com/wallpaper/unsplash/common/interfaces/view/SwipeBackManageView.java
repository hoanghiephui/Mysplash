package com.wallpaper.unsplash.common.interfaces.view;

import com.wallpaper.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;

/**
 * Swipe back manage view.
 *
 * A view which can control {@link SwipeBackView} to respond the operation from
 * {@link com.wallpaper.unsplash.common.ui.widget.SwipeBackCoordinatorLayout}.
 *
 * */

public interface SwipeBackManageView {

    boolean checkCanSwipeBack(@SwipeBackCoordinatorLayout.DirectionRule int dir);
}
