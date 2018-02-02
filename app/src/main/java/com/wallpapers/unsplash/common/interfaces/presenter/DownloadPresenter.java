package com.wallpapers.unsplash.common.interfaces.presenter;

import android.content.Context;

/**
 * Download presenter.
 * <p>
 * Presenter to control the download behavior.
 */

public interface DownloadPresenter {

    void download(Context context);

    void share(Context context);

    void setWallpaper(Context context);

    Object getDownloadKey();

    void setDownloadKey(Object key);
}
