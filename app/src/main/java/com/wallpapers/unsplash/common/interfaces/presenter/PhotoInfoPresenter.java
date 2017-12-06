package com.wallpapers.unsplash.common.interfaces.presenter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.ui.adapter.PhotoInfoAdapter;

import java.util.List;

/**
 * Photo info presenter.
 *
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.PhotoInfoView}.
 *
 * */

public interface PhotoInfoPresenter {

    void requestPhoto(Context context);
    void setLikeForAPhoto(Context context);
    void cancelRequest();

    void touchMenuItem(int itemId);

    @Nullable
    Photo getPhoto();
    @Nullable
    List<Photo> getPhotoList();
    void setPhoto(Photo photo, boolean init);

    PhotoInfoAdapter getAdapter();

    boolean isFailed();
}
