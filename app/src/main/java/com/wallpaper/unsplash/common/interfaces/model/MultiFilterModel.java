package com.wallpaper.unsplash.common.interfaces.model;

import com.wallpaper.unsplash.common.data.service.PhotoService;
import com.wallpaper.unsplash.common.ui.adapter.PhotoAdapter;

/**
 * Multi-filter model.
 *
 * Model for {@link com.wallpaper.unsplash.common.interfaces.view.MultiFilterView}.
 *
 * */

public interface MultiFilterModel {
    /** {@link com.wallpaper.unsplash.common.data.api.PhotoApi#getRandomPhotos(Integer, Boolean, String, String, String, int)} */

    PhotoAdapter getAdapter();
    PhotoService getService();

    // manage HTTP request parameters.

    String getQuery();
    void setQuery(String query);

    String getUsername();
    void setUsername(String username);

    int getCategory();
    void setCategory(int c);

    String getOrientation();
    void setOrientation(String o);

    boolean isFeatured();
    void setFeatured(boolean f);

    // control load state.

    boolean isRefreshing();
    void setRefreshing(boolean refreshing);

    boolean isLoading();
    void setLoading(boolean loading);

    /** The flag to mark the photos already load over. */
    boolean isOver();
    void setOver(boolean over);
}
