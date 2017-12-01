package com.wallpapers.unsplash.search.model.widget;

import com.wallpapers.unsplash.Unsplash;
import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;
import com.wallpapers.unsplash.common.data.service.SearchService;
import com.wallpapers.unsplash.common.interfaces.model.SearchModel;
import com.wallpapers.unsplash.common.ui.adapter.CollectionAdapter;

/**
 * Search collections object.
 *
 * */

public class SearchCollectionsObject
        implements SearchModel {

    private CollectionAdapter adapter;
    private SearchService service;

    private String searchQuery;

    private int photosPage;

    private boolean refreshing;
    private boolean loading;
    private boolean over;

    public SearchCollectionsObject(CollectionAdapter adapter) {
        this.adapter = adapter;
        this.service = SearchService.getService();

        this.searchQuery = "";

        this.photosPage = adapter.getItemCount() / Unsplash.DEFAULT_PER_PAGE;

        this.refreshing = false;
        this.loading = false;
        this.over = false;
    }

    @Override
    public CollectionAdapter getAdapter() {
        return adapter;
    }

    @Override
    public SearchService getService() {
        return service;
    }

    @Override
    public void setActivity(MysplashActivity a) {
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