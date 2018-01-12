package com.wallpaper.unsplash.about.model;

import com.wallpaper.unsplash.common.interfaces.model.AboutModel;

/**
 * Header object.
 *
 * Header in {@link com.wallpaper.unsplash.common.ui.adapter.AboutAdapter}.
 *
 * */

public class HeaderObject
        implements AboutModel {

    public int type = AboutModel.TYPE_HEADER;

    @Override
    public int getType() {
        return type;
    }
}
