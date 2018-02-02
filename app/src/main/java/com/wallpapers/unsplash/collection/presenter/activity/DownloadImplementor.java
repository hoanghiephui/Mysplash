package com.wallpapers.unsplash.collection.presenter.activity;

import android.content.Context;

import com.wallpapers.unsplash.common.data.entity.unsplash.Collection;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.interfaces.model.DownloadModel;
import com.wallpapers.unsplash.common.interfaces.presenter.DownloadPresenter;
import com.wallpapers.unsplash.common.utils.helper.DownloadHelper;

/**
 * Download implementor.
 */

public class DownloadImplementor
        implements DownloadPresenter {

    private DownloadModel model;

    public DownloadImplementor(DownloadModel model) {
        this.model = model;
    }

    @Override
    public void download(Context context) {
        Object key = getDownloadKey();
        if (key instanceof Collection) {
            DownloadHelper.getInstance(context).addMission(context, ((Collection) key));
        } else {
            DownloadHelper.getInstance(context).addMission(context, (Photo) key, DownloadHelper.DOWNLOAD_TYPE);
        }
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
