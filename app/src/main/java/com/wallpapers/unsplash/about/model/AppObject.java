package com.wallpapers.unsplash.about.model;

import com.wallpapers.unsplash.common.interfaces.model.AboutModel;

/**
 * App object.
 * <p>
 * app information in {@link com.wallpapers.unsplash.common.ui.adapter.AboutAdapter}.
 */

public class AppObject
        implements AboutModel {

    public int type = AboutModel.TYPE_APP;
    public int id;
    public int iconId;
    public String text;

    public AppObject(int id, int iconId, String text) {
        this.id = id;
        this.iconId = iconId;
        this.text = text;
    }

    @Override
    public int getType() {
        return type;
    }
}
