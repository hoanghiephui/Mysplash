package com.wallpapers.unsplash.common.interfaces.model;


import com.wallpapers.unsplash.UnsplashApplication;

/**
 * Category manage model.
 * <p>
 * Model for {@link com.wallpapers.unsplash.common.interfaces.view.CategoryManageView}.
 */

public interface CategoryManageModel {

    @UnsplashApplication.CategoryIdRule
    int getCategoryId();

    void setCategoryId(@UnsplashApplication.CategoryIdRule int id);
}