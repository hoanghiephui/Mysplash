package com.wallpapers.unsplash.common.interfaces.model;

/**
 * Scroll model.
 *
 * Model for {@link com.wallpapers.unsplash.common.interfaces.view.ScrollView}.
 *
 * */

public interface ScrollModel {

    /** The flag to mark the list view has been already scrolled to the top. */
    boolean isToTop();
    void setToTop(boolean top);
}
