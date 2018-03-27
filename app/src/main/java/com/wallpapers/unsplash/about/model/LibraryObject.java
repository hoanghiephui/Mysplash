package com.wallpapers.unsplash.about.model;

import com.wallpapers.unsplash.common.interfaces.model.AboutModel;

/**
 * Library object.
 * <p>
 * library information in {@link com.wallpapers.unsplash.common.ui.adapter.AboutAdapter}.
 */

public class LibraryObject
        implements AboutModel {

    public int type = AboutModel.TYPE_LIBRARY;
    public String title;
    public String subtitle;
    public String uri;

    public LibraryObject(String title, String subtitle, String uri) {
        this.title = title;
        this.subtitle = subtitle;
        this.uri = uri;
    }

    @Override
    public int getType() {
        return type;
    }
}