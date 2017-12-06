package com.wallpapers.unsplash.main.model.widget;

import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.data.service.CollectionService;
import com.wallpapers.unsplash.common.interfaces.model.CollectionsModel;
import com.wallpapers.unsplash.common.ui.adapter.CollectionAdapter;

/**
 * Collections object.
 *
 * */

public class CollectionsObject
        implements CollectionsModel {

    private CollectionAdapter adapter;
    private CollectionService service;

    private int collectionsType;

    private int collectionsPage;

    private boolean refreshing;
    private boolean loading;
    private boolean over;

    public CollectionsObject(CollectionAdapter adapter, @UnsplashApplication.CategoryIdRule int type) {
        this.adapter = adapter;
        this.service = CollectionService.getService();

        this.collectionsType = type;

        this.collectionsPage = adapter.getItemCount() / UnsplashApplication.DEFAULT_PER_PAGE;

        this.refreshing = false;
        this.loading = false;
        this.over = false;
    }

    @Override
    public CollectionAdapter getAdapter() {
        return adapter;
    }

    @Override
    public CollectionService getService() {
        return service;
    }

    @Override
    public Object getRequestKey() {
        return null;
    }

    @Override
    public void setRequestKey(Object key) {
        // do nothing.
    }

    @Override
    public int getCollectionsType() {
        return collectionsType;
    }

    @Override
    public void setCollectionsType(int type) {
        collectionsType = type;
    }

    @Override
    public int getCollectionsPage() {
        return collectionsPage;
    }

    @Override
    public void setCollectionsPage(int page) {
        collectionsPage = page;
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
