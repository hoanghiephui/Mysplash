package com.wallpapers.unsplash.common.interfaces.model;


import com.wallpapers.unsplash.Unsplash;

/**
 * Category manage model.
 *
 * Model for {@link com.wallpapers.unsplash.common.interfaces.view.CategoryManageView}.
 *
 * */

public interface CategoryManageModel {

    @Unsplash.CategoryIdRule
    int getCategoryId();
    void setCategoryId(@Unsplash.CategoryIdRule int id);
}