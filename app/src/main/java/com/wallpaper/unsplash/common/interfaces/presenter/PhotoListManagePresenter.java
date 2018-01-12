package com.wallpaper.unsplash.common.interfaces.presenter;

import android.support.annotation.Nullable;

import com.wallpaper.unsplash.common.data.entity.unsplash.Photo;

import java.util.List;

/**
 * Photo list manage presenter.
 * */

public interface PhotoListManagePresenter {

    List<Photo> getPhotoList();

    @Nullable
    Photo getPhoto();

    void setCurrentIndex(int index);
    int getCurrentIndex();

    void setHeadIndex(int index);
    int getHeadIndex();
    int getTailIndex();
}
