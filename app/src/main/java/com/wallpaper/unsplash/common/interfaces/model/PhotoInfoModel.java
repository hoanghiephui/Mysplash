package com.wallpaper.unsplash.common.interfaces.model;

import com.wallpaper.unsplash.common.data.entity.unsplash.Photo;
import com.wallpaper.unsplash.common.data.service.PhotoInfoService;
import com.wallpaper.unsplash.common.data.service.PhotoService;
import com.wallpaper.unsplash.common.ui.adapter.PhotoInfoAdapter;

import java.util.List;

/**
 * Photo info model.
 *
 * Model for {@link com.wallpaper.unsplash.common.interfaces.view.PhotoInfoView}.
 *
 * */

public interface PhotoInfoModel {

    PhotoInfoService getPhotoInfoService();
    PhotoService getPhotoService();
    PhotoInfoAdapter getAdapter();

    Photo getPhoto();
    List<Photo> getListPhotoRe();
    void setPhoto(Photo p, boolean init);
    void setPhoto(List<Photo> photoList, boolean init);

    /** The flag to mark if loading photo details failed. */
    boolean isFailed();
    void setFailed(boolean b);
}
