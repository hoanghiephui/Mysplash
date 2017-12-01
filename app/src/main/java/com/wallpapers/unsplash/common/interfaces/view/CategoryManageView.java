package com.wallpapers.unsplash.common.interfaces.view;

import com.wallpapers.unsplash.Unsplash;

/**
 * Category manage view.
 *
 * A view which can manage category data.
 *
 * */

public interface CategoryManageView {

    void setCategory(@Unsplash.CategoryIdRule int categoryId);
}
