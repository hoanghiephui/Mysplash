package com.wallpaper.unsplash.about.model;

import com.wallpaper.unsplash.common.interfaces.model.AboutModel;

/**
 * Category object.
 *
 * category in {@link com.wallpaper.unsplash.common.ui.adapter.AboutAdapter}.
 *
 * */

public class CategoryObject
        implements AboutModel {

    public int type = AboutModel.TYPE_CATEGORY;
    public String category;

    public CategoryObject(String category) {
        this.category = category;
    }

    @Override
    public int getType() {
        return type;
    }
}
