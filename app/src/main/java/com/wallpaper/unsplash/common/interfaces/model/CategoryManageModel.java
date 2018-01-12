package com.wallpaper.unsplash.common.interfaces.model;


import com.wallpaper.unsplash.UnsplashApplication;

/**
 * Category manage model.
 *
 * Model for {@link com.wallpaper.unsplash.common.interfaces.view.CategoryManageView}.
 *
 * */

public interface CategoryManageModel {

    @UnsplashApplication.CategoryIdRule
    int getCategoryId();
    void setCategoryId(@UnsplashApplication.CategoryIdRule int id);
}