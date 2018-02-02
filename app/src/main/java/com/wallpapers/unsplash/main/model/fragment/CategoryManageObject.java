package com.wallpapers.unsplash.main.model.fragment;

import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.interfaces.model.CategoryManageModel;

/**
 * Category manage object.
 */

public class CategoryManageObject
        implements CategoryManageModel {

    @UnsplashApplication.CategoryIdRule
    private int categoryId;

    public CategoryManageObject(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int getCategoryId() {
        return categoryId;
    }

    @Override
    public void setCategoryId(int id) {
        categoryId = id;
    }
}