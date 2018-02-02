package com.wallpapers.unsplash.common.interfaces.model;

import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.data.service.PhotoService;
import com.wallpapers.unsplash.common.ui.adapter.PhotoAdapter;

import java.util.List;

/**
 * Photos model.
 * <p>
 * Model for {@link com.wallpapers.unsplash.common.interfaces.view.PhotosView}.
 */

public interface PhotosModel {

    PhotoAdapter getAdapter();

    PhotoService getService();

    // manage HTTP request parameters.

    Object getRequestKey();

    void setRequestKey(Object key);

    int getPhotosType();

    String getPhotosOrder();

    void setPhotosOrder(String order);

    boolean isRandomType();

    int getPhotosPage();

    void setPhotosPage(@UnsplashApplication.PageRule int page);

    /**
     * {@link com.wallpapers.unsplash.common.utils.ValueUtils#getPageListByCategory(int)}
     */
    List<Integer> getPageList();

    void setPageList(List<Integer> list);

    // control load state.

    boolean isRefreshing();

    void setRefreshing(boolean refreshing);

    boolean isLoading();

    void setLoading(boolean loading);

    /**
     * The flag to mark the photos already load over.
     */
    boolean isOver();

    void setOver(boolean over);
}
