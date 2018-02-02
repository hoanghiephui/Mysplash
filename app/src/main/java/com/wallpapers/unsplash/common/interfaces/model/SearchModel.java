package com.wallpapers.unsplash.common.interfaces.model;

import android.support.v7.widget.RecyclerView;

import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.data.service.SearchService;

/**
 * Search model.
 * <p>
 * Model for {@link com.wallpapers.unsplash.common.interfaces.view.SearchView}.
 */

public interface SearchModel {

    RecyclerView.Adapter getAdapter();

    SearchService getService();

    void setActivity(BaseActivity a);

    // manage HTTP request parameters.

    String getSearchQuery();

    void setSearchQuery(String query);

    int getPhotosPage();

    void setPhotosPage(@UnsplashApplication.PageRule int page);

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
