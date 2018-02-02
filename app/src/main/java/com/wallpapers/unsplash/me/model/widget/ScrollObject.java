package com.wallpapers.unsplash.me.model.widget;

import com.wallpapers.unsplash.common.interfaces.model.ScrollModel;

/**
 * Scroll object.
 */

public class ScrollObject
        implements ScrollModel {
    // data
    private boolean toTop;

    public ScrollObject() {
        this.toTop = true;
    }

    @Override
    public boolean isToTop() {
        return toTop;
    }

    @Override
    public void setToTop(boolean top) {
        toTop = top;
    }
}
