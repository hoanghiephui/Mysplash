package com.wallpapers.unsplash.user.model.widget;

import android.app.Activity;

import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.data.entity.unsplash.Collection;
import com.wallpapers.unsplash.common.data.entity.unsplash.User;
import com.wallpapers.unsplash.common.data.service.CollectionService;
import com.wallpapers.unsplash.common.interfaces.model.CollectionsModel;
import com.wallpapers.unsplash.common.ui.adapter.CollectionAdapter;

import java.util.ArrayList;

/**
 * Collections object.
 * */

public class CollectionsObject
        implements CollectionsModel {

    private CollectionAdapter adapter;
    private CollectionService service;

    private User requestKey;
    private int collectionsPage;

    private boolean refreshing;
    private boolean loading;
    private boolean over;

    public CollectionsObject(Activity a, User u) {
        this.adapter = new CollectionAdapter(a, new ArrayList<Collection>(UnsplashApplication.DEFAULT_PER_PAGE), false);
        this.service = CollectionService.getService();

        this.requestKey = u;
        this.collectionsPage = 0;

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
        return requestKey;
    }

    @Override
    public void setRequestKey(Object key) {
        requestKey = (User) key;
    }

    @Override
    public int getCollectionsType() {
        return UnsplashApplication.COLLECTION_TYPE_ALL;
    }

    @Override
    public void setCollectionsType(int order) {
        // do nothing.
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

