package com.wallpaper.unsplash.photo.presenter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.wallpaper.unsplash.common.data.entity.unsplash.LikePhotoResult;
import com.wallpaper.unsplash.common.data.entity.unsplash.Photo;
import com.wallpaper.unsplash.common.data.service.PhotoService;
import com.wallpaper.unsplash.common.interfaces.model.PhotoInfoModel;
import com.wallpaper.unsplash.common.interfaces.presenter.PhotoInfoPresenter;
import com.wallpaper.unsplash.common.interfaces.view.PhotoInfoView;
import com.wallpaper.unsplash.common.ui.adapter.PhotoInfoAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Photo info implementor.
 * */

public class PhotoInfoImplementor
        implements PhotoInfoPresenter {

    private PhotoInfoModel model;
    private PhotoInfoView view;

    private OnRequestPhotoDetailsListener requestPhotoListener;

    public PhotoInfoImplementor(PhotoInfoModel model, PhotoInfoView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void requestPhoto(Context context) {
        requestPhotoListener = new OnRequestPhotoDetailsListener();
        // model.getPhotoInfoService().requestAPhoto(model.getPhoto().id, requestPhotoListener);
        //model.getPhotoService().requestAPhoto(model.getPhoto().id, requestPhotoListener);
        model.getPhotoService().onZipRequestPhotoInfo(model.getPhoto().id, requestPhotoListener);
    }

    @Override
    public void setLikeForAPhoto(Context context) {
        Photo photo = model.getPhoto();
        photo.settingLike = true;
        model.setPhoto(photo, false);
        model.getPhotoService().setLikeForAPhoto(
                model.getPhoto().id,
                !model.getPhoto().liked_by_user,
                new OnSetLikeForAPhotoListener(photo.id));
    }

    @Override
    public void cancelRequest() {
        if (requestPhotoListener != null) {
            requestPhotoListener.cancel();
        }
        // model.getPhotoInfoService().cancel();
        model.getPhotoService().cancel();
    }

    @Override
    public void touchMenuItem(int itemId) {
        view.touchMenuItem(itemId);
    }

    @Override
    public Photo getPhoto() {
        return model.getPhoto();
    }

    @Nullable
    @Override
    public List<Photo> getPhotoList() {
        return model.getListPhotoRe();
    }

    @Override
    public void setPhoto(Photo photo, boolean init) {
        model.setPhoto(photo, init);
    }

    @Override
    public PhotoInfoAdapter getAdapter() {
        return model.getAdapter();
    }

    @Override
    public boolean isFailed() {
        return model.isFailed();
    }

    // interface.

    // on request single photo requestPhotoListener.

    private class OnRequestPhotoDetailsListener
            implements PhotoService.OnRequestMutilPhotoListener {

        private boolean canceled;

        OnRequestPhotoDetailsListener() {
            this.canceled = false;
        }

        public void cancel() {
            this.canceled = true;
        }

        @Override
        public void onRequestMutilPhotoSuccess(PhotoService.Groups groups) {
            if (canceled) {
                return;
            }

            if (groups.getPhotoRe() != null && groups.getPhotoRe().size() > 0) {
                model.setPhoto(groups.getPhotoRe(), false);
            }

            Photo photo = groups.getPhoto();
            if (photo != null) {
                photo.complete = true;
                model.setPhoto(photo, false);
                model.setFailed(false);
                view.requestPhotoSuccess(photo);
            } else {
                model.setFailed(true);
                view.requestPhotoFailed();
            }
        }

        @Override
        public void onRequestMutilPhotoFailed(Throwable t) {
            if (canceled) {
                return;
            }
            model.setFailed(true);
            view.requestPhotoFailed();
        }
    }

    // on set like requestPhotoListener.

    private class OnSetLikeForAPhotoListener implements PhotoService.OnSetLikeListener {

        private String id;

        OnSetLikeForAPhotoListener(String id) {
            this.id = id;
        }

        @Override
        public void onSetLikeSuccess(Call<LikePhotoResult> call, Response<LikePhotoResult> response) {
            if (!model.getPhoto().id.equals(id)) {
                return;
            }
            Photo photo = model.getPhoto();
            photo.settingLike = false;
            if (response.isSuccessful() && response.body() != null) {
                photo.liked_by_user = response.body().photo.liked_by_user;
            }
            model.setPhoto(photo, false);
            view.setLikeForAPhotoCompleted(photo, true);
        }

        @Override
        public void onSetLikeFailed(Call<LikePhotoResult> call, Throwable t) {
            if (!model.getPhoto().id.equals(id)) {
                return;
            }
            Photo photo = model.getPhoto();
            photo.settingLike = false;
            model.setPhoto(photo, false);
            view.setLikeForAPhotoCompleted(photo, false);
        }
    }
}
