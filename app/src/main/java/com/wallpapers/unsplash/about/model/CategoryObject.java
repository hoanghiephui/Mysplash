package com.wallpapers.unsplash.about.model;

import com.wallpapers.unsplash.common.interfaces.model.AboutModel;

/**
 * Category object.
 * <p>
 * category in {@link com.wallpapers.unsplash.common.ui.adapter.AboutAdapter}.
 */

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
