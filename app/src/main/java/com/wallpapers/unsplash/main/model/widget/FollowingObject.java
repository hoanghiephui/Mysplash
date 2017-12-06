package com.wallpapers.unsplash.main.model.widget;

import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.data.service.FeedService;
import com.wallpapers.unsplash.common.interfaces.model.FollowingModel;
import com.wallpapers.unsplash.common.ui.adapter.FollowingAdapter;

/**
 * Following object.
 *
 * */

public class FollowingObject implements FollowingModel {

    private FollowingAdapter adapter;
    private FeedService service;

    private String nextPage;

    private boolean refreshing;
    private boolean loading;
    private boolean over;

    public FollowingObject(FollowingAdapter adapter) {
        this.adapter = adapter;
        this.service = FeedService.getService();

        this.nextPage = getFirstPage();

        this.refreshing = false;
        this.loading = false;
        this.over = false;
    }

    @Override
    public FollowingAdapter getAdapter() {
        return adapter;
    }

    @Override
    public FeedService getService() {
        return service;
    }

    @Override
    public String getFirstPage() {
        return UnsplashApplication.UNSPLASH_URL + UnsplashApplication.UNSPLASH_FOLLOWING_FEED_URL;
    }

    @Override
    public String getNextPage() {
        return nextPage;
    }

    @Override
    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
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
