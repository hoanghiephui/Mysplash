package com.wallpaper.unsplash.photo.model;

import com.wallpaper.unsplash.common.data.entity.unsplash.Photo;
import com.wallpaper.unsplash.common.interfaces.model.DownloadModel;

/**
 * Download object.
 * */

public class DownloadObject
        implements DownloadModel {

    private Photo photo;

    public DownloadObject(Photo p) {
        this.photo = p;
    }

    @Override
    public Object getDownloadKey() {
        return photo;
    }

    @Override
    public void setDownloadKey(Object key) {
        photo = (Photo) key;
    }
}
