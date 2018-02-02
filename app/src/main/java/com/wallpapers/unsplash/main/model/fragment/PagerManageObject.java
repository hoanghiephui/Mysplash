package com.wallpapers.unsplash.main.model.fragment;

import com.wallpapers.unsplash.common.interfaces.model.PagerManageModel;

/**
 * Pager manage object.
 */

public class PagerManageObject
        implements PagerManageModel {

    private int pagePosition;

    public PagerManageObject(int initPosition) {
        this.pagePosition = initPosition;
    }

    @Override
    public int getPagerPosition() {
        return pagePosition;
    }

    @Override
    public void setPagerPosition(int position) {
        pagePosition = position;
    }
}
