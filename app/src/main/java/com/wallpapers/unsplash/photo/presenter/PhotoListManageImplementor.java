package com.wallpapers.unsplash.photo.presenter;

import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.interfaces.model.PhotoListManageModel;
import com.wallpapers.unsplash.common.interfaces.presenter.PhotoListManagePresenter;

import java.util.List;

/**
 * Photo list controller.
 * */

public class PhotoListManageImplementor implements PhotoListManagePresenter {

    private PhotoListManageModel model;

    public PhotoListManageImplementor(PhotoListManageModel model) {
        this.model = model;
    }

    @Override
    public List<Photo> getPhotoList() {
        return model.getPhotoList();
    }

    @Override
    public Photo getPhoto() {
        return model.getPhoto();
    }

    @Override
    public void setCurrentIndex(int index) {
        model.setCurrentIndex(index);
    }

    @Override
    public int getCurrentIndex() {
        return model.getCurrentIndex();
    }

    @Override
    public void setHeadIndex(int index) {
        model.setHeadIndex(index);
    }

    @Override
    public int getHeadIndex() {
        return model.getHeadIndex();
    }

    @Override
    public int getTailIndex() {
        return model.getTailIndex();
    }
}
