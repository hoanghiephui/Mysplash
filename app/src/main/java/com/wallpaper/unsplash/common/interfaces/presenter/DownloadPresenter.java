package com.wallpaper.unsplash.common.interfaces.presenter;

import android.content.Context;

/**
 * Download presenter.
 *
 * Presenter to control the download behavior.
 *
 * */

public interface DownloadPresenter {

    void download(Context context);
    void share(Context context);
    void setWallpaper(Context context);

    Object getDownloadKey();
    void setDownloadKey(Object key);
}
