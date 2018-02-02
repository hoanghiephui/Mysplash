package com.wallpapers.unsplash.common.interfaces.model;

/**
 * Pager model.
 * <p>
 * Model for {@link com.wallpapers.unsplash.common.interfaces.view.PagerView}.
 */

public interface PagerModel {

    int getIndex();

    boolean isSelected();

    void setSelected(boolean selected);
}
