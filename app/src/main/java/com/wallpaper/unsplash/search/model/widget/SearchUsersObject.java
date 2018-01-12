package com.wallpaper.unsplash.search.model.widget;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.common.basic.activity.BaseActivity;
import com.wallpaper.unsplash.common.data.service.SearchService;
import com.wallpaper.unsplash.common.interfaces.model.SearchModel;
import com.wallpaper.unsplash.common.ui.adapter.UserAdapter;

/**
 * Search users object.
 *
 * */

public class SearchUsersObject
        implements SearchModel {

    private UserAdapter adapter;
    private SearchService service;

    private String searchQuery;

    private int photosPage;

    private boolean refreshing;
    private boolean loading;
    private boolean over;

    public SearchUsersObject(UserAdapter adapter) {
        this.adapter = adapter;
        this.service = SearchService.getService();

        this.searchQuery = "";

        this.photosPage = adapter.getItemCount() / UnsplashApplication.DEFAULT_PER_PAGE;

        this.refreshing = false;
        this.loading = false;
        this.over = false;
    }

    @Override
    public UserAdapter getAdapter() {
        return adapter;
    }

    @Override
    public SearchService getService() {
        return service;
    }

    @Override
    public void setActivity(BaseActivity a) {
        adapter.setActivity(a);
    }

    @Override
    public String getSearchQuery() {
        return searchQuery;
    }

    @Override
    public void setSearchQuery(String query) {
        searchQuery = query;
    }

    @Override
    public int getPhotosPage() {
        return photosPage;
    }

    @Override
    public void setPhotosPage(int page) {
        photosPage = page;
    }

    @Override
    public boolean isRefreshing() {
        return refreshing;
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        this.refreshing = refreshing;
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    @Override
    public boolean isOver() {
        return over;
    }

    @Override
    public void setOver(boolean over) {
        this.over = over;
    }
}
