package com.wangdaye.mysplash.common.interfaces.model;

/**
 * Scroll model.
 *
 * Model for {@link com.wangdaye.mysplash.common.interfaces.view.ScrollView}.
 *
 * */

public interface ScrollModel {

    /** The flag to mark the list view has been already scrolled to the top. */
    boolean isToTop();
    void setToTop(boolean top);
}
