package com.wallpaper.unsplash.common.interfaces.presenter;

import com.wallpaper.unsplash.UnsplashApplication;

/**
 * Category manage presenter.
 *
 * Presenter for {@link com.wallpaper.unsplash.common.interfaces.view.CategoryManageView}.
 *
 * */

public interface CategoryManagePresenter {

    @UnsplashApplication.CategoryIdRule
    int getCategoryId();
    void setCategoryId(@UnsplashApplication.CategoryIdRule int id);
}
