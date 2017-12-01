package com.wallpapers.unsplash.photo.model;

import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.interfaces.model.PhotoListManageModel;

import java.util.List;

/**
 * Photo list manage object.
 * */

public class PhotoListManageObject implements PhotoListManageModel {

    private List<Photo> photoList;
    private int currentIndex;
    private int headIndex;

    public PhotoListManageObject(List<Photo> photoList, int currentIndex, int headIndex) {
        this.photoList = photoList;
        this.currentIndex = currentIndex;
        this.headIndex = headIndex;
    }

    @Override
    public List<Photo> getPhotoList() {
        return photoList;
    }

    @Override
    public Photo getPhoto() {
        if (photoList == null || photoList.size() == 0
                || currentIndex < 0 || headIndex < 0) {
            return null;
        } else {
            return photoList.get(currentIndex - headIndex);
        }
    }

    @Override
    public void setCurrentIndex(int index) {
        this.currentIndex = index;
    }

    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public void setHeadIndex(int index) {
        this.headIndex = index;
    }

    @Override
    public int getHeadIndex() {
        return headIndex;
    }

    @Override
    public int getTailIndex() {
        return headIndex + photoList.size() - 1;
    }
}
