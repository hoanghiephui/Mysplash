package com.wallpapers.unsplash.common.interfaces.model;

import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.data.service.PhotoInfoService;
import com.wallpapers.unsplash.common.data.service.PhotoService;
import com.wallpapers.unsplash.common.ui.adapter.PhotoInfoAdapter;

/**
 * Photo info model.
 *
 * Model for {@link com.wallpapers.unsplash.common.interfaces.view.PhotoInfoView}.
 *
 * */

public interface PhotoInfoModel {

    PhotoInfoService getPhotoInfoService();
    PhotoService getPhotoService();
    PhotoInfoAdapter getAdapter();

    Photo getPhoto();
    void setPhoto(Photo p, boolean init);

    /** The flag to mark if loading photo details failed. */
    boolean isFailed();
    void setFailed(boolean b);
}
