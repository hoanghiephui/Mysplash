package com.wallpaper.unsplash.collection.model.activity;

import com.wallpaper.unsplash.common.interfaces.model.DownloadModel;

/**
 * Download object.
 * */

public class DownloadObject implements DownloadModel {

    private Object key;

    @Override
    public Object getDownloadKey() {
        return key;
    }

    @Override
    public void setDownloadKey(Object key) {
        this.key = key;
    }
}
