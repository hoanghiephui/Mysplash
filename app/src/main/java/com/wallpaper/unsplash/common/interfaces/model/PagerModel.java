package com.wallpaper.unsplash.common.interfaces.model;

/**
 * Pager model.
 *
 * Model for {@link com.wallpaper.unsplash.common.interfaces.view.PagerView}.
 * */

public interface PagerModel {

    int getIndex();

    boolean isSelected();
    void setSelected(boolean selected);
}
