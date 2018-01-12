package com.wallpaper.unsplash.common.interfaces.view;

import com.wallpaper.unsplash.UnsplashApplication;

/**
 * Category manage view.
 *
 * A view which can manage category data.
 *
 * */

public interface CategoryManageView {

    void setCategory(@UnsplashApplication.CategoryIdRule int categoryId);
}
