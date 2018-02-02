package com.wallpapers.unsplash.common.interfaces.model;

import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.data.service.PhotoInfoService;
import com.wallpapers.unsplash.common.data.service.PhotoService;
import com.wallpapers.unsplash.common.ui.adapter.PhotoInfoAdapter;

import java.util.List;

/**
 * Photo info model.
 * <p>
 * Model for {@link com.wallpapers.unsplash.common.interfaces.view.PhotoInfoView}.
 */

public interface PhotoInfoModel {

    PhotoInfoService getPhotoInfoService();

    PhotoService getPhotoService();

    PhotoInfoAdapter getAdapter();

    Photo getPhoto();

    List<Photo> getListPhotoRe();

    void setPhoto(Photo p, boolean init);

    void setPhoto(List<Photo> photoList, boolean init);

    /**
     * The flag to mark if loading photo details failed.
     */
    boolean isFailed();

    void setFailed(boolean b);
}
