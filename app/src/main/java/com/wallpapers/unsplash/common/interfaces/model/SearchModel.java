package com.wallpapers.unsplash.common.interfaces.model;

import android.support.v7.widget.RecyclerView;

import com.wallpapers.unsplash.Unsplash;
import com.wallpapers.unsplash.common.data.service.SearchService;
import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;

/**
 * Search model.
 *
 * Model for {@link com.wallpapers.unsplash.common.interfaces.view.SearchView}.
 *
 * */

public interface SearchModel {

    RecyclerView.Adapter getAdapter();
    SearchService getService();
    void setActivity(MysplashActivity a);

    // manage HTTP request parameters.

    String getSearchQuery();
    void setSearchQuery(String query);

    int getPhotosPage();
    void setPhotosPage(@Unsplash.PageRule int page);

    // control load state.

    boolean isRefreshing();
    void setRefreshing(boolean refreshing);

    boolean isLoading();
    void setLoading(boolean loading);

    /** The flag to mark the photos already load over. */
    boolean isOver();
    void setOver(boolean over);
}
