package com.wallpaper.unsplash.main.presenter.fragment;

import com.wallpaper.unsplash.common.interfaces.model.CategoryManageModel;
import com.wallpaper.unsplash.common.interfaces.presenter.CategoryManagePresenter;
import com.wallpaper.unsplash.common.interfaces.view.CategoryManageView;

/**
 * Category manage implementor.
 *
 * */

public class CategoryManageImplementor
        implements CategoryManagePresenter {

    private CategoryManageModel model;
    private CategoryManageView view;

    public CategoryManageImplementor(CategoryManageModel model, CategoryManageView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public int getCategoryId() {
        return model.getCategoryId();
    }

    @Override
    public void setCategoryId(int id) {
        model.setCategoryId(id);
        view.setCategory(id);
    }
}