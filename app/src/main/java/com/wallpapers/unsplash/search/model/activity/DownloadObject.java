package com.wallpapers.unsplash.search.model.activity;

import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.interfaces.model.DownloadModel;

/**
 * Download object.
 */

public class DownloadObject implements DownloadModel {

    private Photo photo;

    @Override
    public Object getDownloadKey() {
        return photo;
    }

    @Override
    public void setDownloadKey(Object key) {
        this.photo = (Photo) key;
    }
}
