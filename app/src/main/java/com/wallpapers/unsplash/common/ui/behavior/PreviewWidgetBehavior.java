package com.wallpapers.unsplash.common.ui.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Preview widget behavior.
 * <p>
 * Behavior class for widget container view in
 * {@link com.wallpapers.unsplash.common.ui.activity.PreviewActivity}, it's used to help the
 * {@link CoordinatorLayout} to layout target child view.
 */

public class PreviewWidgetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    public PreviewWidgetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        child.layout(
                0,
                -child.getMeasuredHeight(),
                child.getMeasuredWidth(),
                0);
        return true;
    }
}