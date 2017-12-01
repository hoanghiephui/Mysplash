package com.wallpapers.unsplash.main.model.widget;

import com.wallpapers.unsplash.common.interfaces.model.ScrollModel;

/**
 * Scroll object.
 *
 * */

public class ScrollObject
        implements ScrollModel {

    private boolean toTop;

    public ScrollObject(boolean top) {
        this.toTop = top;
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
