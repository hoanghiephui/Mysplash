package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.UnsplashApplication;

/**
 * Category manage presenter.
 * <p>
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.CategoryManageView}.
 */

public interface CategoryManagePresenter {

    @UnsplashApplication.CategoryIdRule
    int getCategoryId();

    void setCategoryId(@UnsplashApplication.CategoryIdRule int id);
}
