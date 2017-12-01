package com.wallpapers.unsplash.common.interfaces.model;

import android.support.annotation.Nullable;

import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;

import java.util.List;

/**
 * Photo list manage model.
 * */

public interface PhotoListManageModel {

    List<Photo> getPhotoList();

    @Nullable
    Photo getPhoto();

    void setCurrentIndex(int index);
    int getCurrentIndex();

    void setHeadIndex(int index);
    int getHeadIndex();
    int getTailIndex();
}
