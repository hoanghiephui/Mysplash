package com.wallpapers.unsplash.about.model;

import com.wallpapers.unsplash.common.interfaces.model.AboutModel;

/**
 * Header object.
 *
 * Header in {@link com.wallpapers.unsplash.common.ui.adapter.AboutAdapter}.
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
