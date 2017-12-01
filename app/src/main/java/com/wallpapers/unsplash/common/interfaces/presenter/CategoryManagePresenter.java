package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.Unsplash;

/**
 * Category manage presenter.
 *
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.CategoryManageView}.
 *
 * */

public interface CategoryManagePresenter {

    @Unsplash.CategoryIdRule
    int getCategoryId();
    void setCategoryId(@Unsplash.CategoryIdRule int id);
}
