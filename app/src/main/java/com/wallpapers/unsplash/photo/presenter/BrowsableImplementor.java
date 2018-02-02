package com.wallpapers.unsplash.photo.presenter;

import android.net.Uri;

import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.data.service.PhotoInfoService;
import com.wallpapers.unsplash.common.data.service.PhotoService;
import com.wallpapers.unsplash.common.interfaces.model.BrowsableModel;
import com.wallpapers.unsplash.common.interfaces.presenter.BrowsablePresenter;
import com.wallpapers.unsplash.common.interfaces.view.BrowsableView;

/**
 * Browsable implementor.
 */

public class BrowsableImplementor
        implements BrowsablePresenter,
        PhotoService.OnRequestMutilPhotoListener {

    private BrowsableModel model;
    private BrowsableView view;

    public BrowsableImplementor(BrowsableModel model, BrowsableView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public Uri getIntentUri() {
        return model.getIntentUri();
    }

    @Override
    public boolean isBrowsable() {
        return model.isBrowsable();
    }

    @Override
    public void requestBrowsableData() {
        view.showRequestDialog();
        ((PhotoInfoService) model.getService())
                .onZipRequestPhotoInfo(model.getBrowsableDataKey().get(0), this);
    }

    @Override
    public void visitPreviousPage() {
        view.visitPreviousPage();
    }

    @Override
    public void cancelRequest() {
        ((PhotoInfoService) model.getService()).cancel();
    }

    // interface.


    @Override
    public void onRequestMutilPhotoSuccess(PhotoService.Groups groups) {
        if (groups.getPhoto() != null) {
            Photo photo = groups.getPhoto();
            if (photo != null) {
                photo.complete = true;
            }
            view.dismissRequestDialog();
            view.drawBrowsableView(photo);
        } else {
            ((PhotoInfoService) model.getService())
                    .onZipRequestPhotoInfo(model.getBrowsableDataKey().get(0), this);
        }
        if (groups.getPhotoRe() != null) {
            view.drawBrowsableViewList(groups.getPhotoRe());
        }
    }

    @Override
    public void onRequestMutilPhotoFailed(Throwable t) {

        ((PhotoInfoService) model.getService())
                .onZipRequestPhotoInfo(model.getBrowsableDataKey().get(0), this);
    }
}
