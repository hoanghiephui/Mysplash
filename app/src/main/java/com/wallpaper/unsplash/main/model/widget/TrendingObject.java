package com.wallpaper.unsplash.main.model.widget;

import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.common.data.service.FeedService;
import com.wallpaper.unsplash.common.interfaces.model.TrendingModel;
import com.wallpaper.unsplash.common.ui.adapter.PhotoAdapter;

/**
 * Trending object.
 * */

public class TrendingObject implements TrendingModel {

    private PhotoAdapter adapter;
    private FeedService service;

    private String nextPage;

    private boolean refreshing;
    private boolean loading;
    private boolean over;

    public TrendingObject(PhotoAdapter adapter) {
        this.adapter = adapter;
        this.service = FeedService.getService();

        this.nextPage = getFirstPage();

        this.refreshing = false;
        this.loading = false;
        this.over = false;
    }

    @Override
    public PhotoAdapter getAdapter() {
        return adapter;
    }

    @Override
    public FeedService getService() {
        return service;
    }

    @Override
    public String getFirstPage() {
        return UnsplashApplication.UNSPLASH_URL + UnsplashApplication.UNSPLASH_TREND_FEEDING_URL;
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
