package com.wallpapers.unsplash.photo.model;

import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.data.service.PhotoInfoService;
import com.wallpapers.unsplash.common.data.service.PhotoService;
import com.wallpapers.unsplash.common.interfaces.model.PhotoInfoModel;
import com.wallpapers.unsplash.common.ui.adapter.PhotoInfoAdapter;
import com.wallpapers.unsplash.photo.view.activity.PhotoActivity;

import java.util.List;

/**
 * Photo info object.
 */

public class PhotoInfoObject
        implements PhotoInfoModel {

    private Photo photo;
    private List<Photo> photoListRelated;
    private boolean failed;
    private PhotoInfoAdapter adapter;

    private PhotoInfoService photoInfoService;
    private PhotoService photoService;

    public PhotoInfoObject(PhotoActivity a, Photo photo, List<Photo> photoListRelated) {
        setPhoto(photo, true);
        this.failed = false;
        this.adapter = new PhotoInfoAdapter(a, photo, photoListRelated);
        this.photoInfoService = PhotoInfoService.getService();
        this.photoService = PhotoService.getService();
    }

    @Override
    public PhotoInfoService getPhotoInfoService() {
        return photoInfoService;
    }

    @Override
    public PhotoService getPhotoService() {
        return photoService;
    }

    @Override
    public PhotoInfoAdapter getAdapter() {
        return adapter;
    }

    @Override
    public Photo getPhoto() {
        return photo;
    }

    @Override
    public List<Photo> getListPhotoRe() {
        return photoListRelated;
    }

    @Override
    public void setPhoto(Photo p, boolean init) {
        if (init && p != null) {
            p.settingLike = false;
        }
        this.photo = p;
    }

    @Override
    public void setPhoto(List<Photo> photoList, boolean init) {
        this.photoListRelated = photoList;
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Override
    public void setFailed(boolean b) {
        this.failed = b;
    }
}
