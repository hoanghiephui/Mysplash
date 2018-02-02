package com.wallpapers.unsplash.user.model.widget;

import com.wallpapers.unsplash.common.interfaces.model.PagerModel;

/**
 * Pager object.
 */

public class PagerObject
        implements PagerModel {

    private int index;
    private boolean selected;

    public PagerObject(int index, boolean selected) {
        this.index = index;
        this.selected = selected;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
