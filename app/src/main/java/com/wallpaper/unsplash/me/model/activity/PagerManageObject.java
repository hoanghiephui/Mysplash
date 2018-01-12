package com.wallpaper.unsplash.me.model.activity;

import com.wallpaper.unsplash.common.interfaces.model.PagerManageModel;

/**
 * Pager manage object.
 * */

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
