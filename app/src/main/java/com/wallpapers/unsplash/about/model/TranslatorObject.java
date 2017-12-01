package com.wallpapers.unsplash.about.model;

import com.wallpapers.unsplash.common.interfaces.model.AboutModel;

/**
 * Translator object.
 *
 * translator information in {@link com.wallpapers.unsplash.common.ui.adapter.AboutAdapter}.
 *
 * */

public class TranslatorObject
        implements AboutModel {

    public int type = AboutModel.TYPE_TRANSLATOR;
    public String avatarUrl;
    public String title;
    public int flagId;
    public String subtitle;

    public TranslatorObject(String avatarUrl, String title, int flagId, String subtitle) {
        this.avatarUrl = avatarUrl;
        this.title = title;
        this.flagId = flagId;
        this.subtitle = subtitle;
    }

    @Override
    public int getType() {
        return type;
    }
}
