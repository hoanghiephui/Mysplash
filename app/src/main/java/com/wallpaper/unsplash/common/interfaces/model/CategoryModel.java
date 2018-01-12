package com.wallpaper.unsplash.common.interfaces.model;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.common.data.service.PhotoService;
import com.wallpaper.unsplash.common.ui.adapter.PhotoAdapter;

import java.util.List;

/**
 * Category model.
 *
 * Model for {@link com.wallpaper.unsplash.common.interfaces.view.CategoryView}.
 *
 * */

public interface CategoryModel {

    PhotoAdapter getAdapter();
    PhotoService getService();

    // manage HTTP request parameters.

    int getPhotosCategory();
    void setPhotosCategory(@UnsplashApplication.CategoryIdRule int category);

    String getPhotosOrder();
    void setPhotosOrder(String order);
    boolean isRandomType();

    @UnsplashApplication.PageRule
    int getPhotosPage();
    void setPhotosPage(@UnsplashApplication.PageRule int page);

    /** {@link com.wallpaper.unsplash.common.utils.ValueUtils#getPageListByCategory(int)} */
    List<Integer> getPageList();
    void setPageList(List<Integer> list);

    // control load state.

    boolean isRefreshing();
    void setRefreshing(boolean refreshing);

    boolean isLoading();
    void setLoading(boolean loading);

    /** The flag to mark the photos already load over. */
    boolean isOver();
    void setOver(boolean over);
}
