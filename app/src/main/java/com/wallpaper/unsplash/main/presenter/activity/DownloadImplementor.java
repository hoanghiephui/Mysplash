package com.wallpaper.unsplash.main.presenter.activity;

import android.content.Context;

import com.wallpaper.unsplash.common.data.entity.unsplash.Photo;
import com.wallpaper.unsplash.common.interfaces.model.DownloadModel;
import com.wallpaper.unsplash.common.interfaces.presenter.DownloadPresenter;
import com.wallpaper.unsplash.common.utils.helper.DownloadHelper;

/**
 * Download implementor.
 *
 * */

public class DownloadImplementor implements DownloadPresenter {

    private DownloadModel model;

    public DownloadImplementor(DownloadModel model) {
        this.model = model;
    }

    @Override
    public void download(Context context) {
        Photo p = (Photo) model.getDownloadKey();
        DownloadHelper.getInstance(context).addMission(context, p, DownloadHelper.DOWNLOAD_TYPE);
    }

    @Override
    public void share(Context context) {
        // do nothing.
    }

    @Override
    public void setWallpaper(Context context) {
        // do nothing.
    }

    @Override
    public Object getDownloadKey() {
        return model.getDownloadKey();
    }

    @Override
    public void setDownloadKey(Object key) {
        model.setDownloadKey(key);
    }
}
