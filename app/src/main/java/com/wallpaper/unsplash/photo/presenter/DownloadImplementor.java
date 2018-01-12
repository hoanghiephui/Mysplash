package com.wallpaper.unsplash.photo.presenter;

import android.content.Context;

import com.wallpaper.unsplash.common.data.entity.unsplash.Photo;
import com.wallpaper.unsplash.common.utils.helper.DownloadHelper;
import com.wallpaper.unsplash.common.interfaces.model.DownloadModel;
import com.wallpaper.unsplash.common.interfaces.presenter.DownloadPresenter;

/**
 * Download implementor.
 * */

public class DownloadImplementor
        implements DownloadPresenter {

    private DownloadModel model;

    public DownloadImplementor(DownloadModel model) {
        this.model = model;
    }

    @Override
    public void download(Context context) {
        doDownload(context, DownloadHelper.DOWNLOAD_TYPE);
    }

    @Override
    public void share(Context context) {
        doDownload(context, DownloadHelper.SHARE_TYPE);
    }

    @Override
    public void setWallpaper(Context context) {
        doDownload(context, DownloadHelper.WALLPAPER_TYPE);
    }

    @Override
    public Object getDownloadKey() {
        return model.getDownloadKey();
    }

    @Override
    public void setDownloadKey(Object key) {
        model.setDownloadKey(key);
    }

    private void doDownload(Context context, int type) {
        Photo p = (Photo) model.getDownloadKey();
        DownloadHelper.getInstance(context).addMission(context, p, type);
    }
}