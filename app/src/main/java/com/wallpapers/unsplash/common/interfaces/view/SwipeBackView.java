package com.wallpapers.unsplash.common.interfaces.view;

import com.wallpapers.unsplash.common.ui.widget.SwipeBackCoordinatorLayout;

/**
 * Swipe back view.
 * <p>
 * A view which can interact with {@link SwipeBackManageView}. By implementing this interface,
 * view can respond the operation from
 * {@link com.wallpapers.unsplash.common.ui.widget.SwipeBackCoordinatorLayout}.
 */

public interface SwipeBackView {

    boolean checkCanSwipeBack(@SwipeBackCoordinatorLayout.DirectionRule int dir);
}
