package com.wallpapers.unsplash.common.interfaces.view;

import com.wallpapers.unsplash.UnsplashApplication;

/**
 * Category manage view.
 * <p>
 * A view which can manage category data.
 */

public interface CategoryManageView {

    void setCategory(@UnsplashApplication.CategoryIdRule int categoryId);
}
