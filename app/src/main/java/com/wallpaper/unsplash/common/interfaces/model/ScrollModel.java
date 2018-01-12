package com.wallpaper.unsplash.common.interfaces.model;

/**
 * Scroll model.
 *
 * Model for {@link com.wallpaper.unsplash.common.interfaces.view.ScrollView}.
 *
 * */

public interface ScrollModel {

    /** The flag to mark the list view has been already scrolled to the top. */
    boolean isToTop();
    void setToTop(boolean top);
}
