package com.wangdaye.mysplash.photo.presenter;

import com.wangdaye.mysplash.common.data.entity.unsplash.Photo;
import com.wangdaye.mysplash.common.interfaces.model.PhotoListManageModel;
import com.wangdaye.mysplash.common.interfaces.presenter.PhotoListManagePresenter;

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
