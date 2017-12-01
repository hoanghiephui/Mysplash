package com.wangdaye.mysplash.user.model.activity;

import com.wangdaye.mysplash.common.data.entity.unsplash.Photo;
import com.wangdaye.mysplash.common.interfaces.model.DownloadModel;

/**
 * Download object.
 * */

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
