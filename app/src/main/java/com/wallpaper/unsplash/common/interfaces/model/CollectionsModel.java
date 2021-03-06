package com.wallpaper.unsplash.common.interfaces.model;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.common.data.service.CollectionService;
import com.wallpaper.unsplash.common.ui.adapter.CollectionAdapter;

/**
 * Collections model.
 *
 * Model for {@link com.wallpaper.unsplash.common.interfaces.view.CollectionsView}.
 *
 * */

public interface CollectionsModel {

    CollectionAdapter getAdapter();
    CollectionService getService();

    // manage HTTP request parameters.

    Object getRequestKey();
    void setRequestKey(Object key);

    @UnsplashApplication.CollectionTypeRule
    int getCollectionsType();
    void setCollectionsType(int type);

    @UnsplashApplication.PageRule
    int getCollectionsPage();
    void setCollectionsPage(@UnsplashApplication.PageRule int page);

    // control load state.

    boolean isRefreshing();
    void setRefreshing(boolean refreshing);

    boolean isLoading();
    void setLoading(boolean loading);

    /** The flag to mark the photos already load over. */
    boolean isOver();
    void setOver(boolean over);
}
