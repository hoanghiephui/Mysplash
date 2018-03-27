package com.wallpapers.unsplash.common.interfaces.model;

/**
 * Download model.
 * <p>
 * Model for {@link com.wallpapers.unsplash.common.interfaces.presenter.DownloadPresenter}.
 */

public interface DownloadModel {

    Object getDownloadKey();

    void setDownloadKey(Object key);
}