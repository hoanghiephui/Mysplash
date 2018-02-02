package com.wallpapers.unsplash.main.model.widget;

import android.content.Context;

import com.wallpapers.unsplash.R;
import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.data.api.PhotoApi;
import com.wallpapers.unsplash.common.data.service.PhotoService;
import com.wallpapers.unsplash.common.interfaces.model.CategoryModel;
import com.wallpapers.unsplash.common.ui.adapter.PhotoAdapter;
import com.wallpapers.unsplash.common.utils.manager.SettingsOptionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Category object.
 */

public class CategoryObject
        implements CategoryModel {

    private PhotoAdapter adapter;
    private PhotoService service;

    @UnsplashApplication.CategoryIdRule
    private int photosCategory;
    private String photosOrder;

    private int photosPage;
    private List<Integer> pageList;

    private boolean refreshing;
    private boolean loading;
    private boolean over;

    private final String RANDOM_TXT;

    public CategoryObject(Context c, PhotoAdapter adapter) {
        this.adapter = adapter;
        this.service = PhotoService.getService();

        RANDOM_TXT = c.getResources().getStringArray(R.array.photo_order_values)[3];
        this.photosCategory = UnsplashApplication.CATEGORY_BUILDINGS_ID;
        this.photosOrder = SettingsOptionManager.getInstance(c)
                .getDefaultPhotoOrder().equals(RANDOM_TXT) ? RANDOM_TXT : PhotoApi.ORDER_BY_LATEST;

        this.photosPage = adapter.getRealItemCount() / UnsplashApplication.DEFAULT_PER_PAGE;
        this.pageList = new ArrayList<>();

        this.refreshing = false;
        this.loading = false;
        this.over = false;
    }

    @Override
    public PhotoAdapter getAdapter() {
        return adapter;
    }

    @Override
    public PhotoService getService() {
        return service;
    }

    @Override
    public int getPhotosCategory() {
        return photosCategory;
    }

    @Override
    public void setPhotosCategory(int category) {
        photosCategory = category;
    }

    @Override
    public String getPhotosOrder() {
        return photosOrder;
    }

    @Override
    public void setPhotosOrder(String order) {
        photosOrder = order;
    }

    @Override
    public boolean isRandomType() {
        return photosOrder.equals(RANDOM_TXT);
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
    public List<Integer> getPageList() {
        return pageList;
    }

    @Override
    public void setPageList(List<Integer> list) {
        pageList.addAll(list);
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