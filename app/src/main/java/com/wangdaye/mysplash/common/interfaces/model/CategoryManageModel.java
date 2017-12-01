package com.wangdaye.mysplash.common.interfaces.model;


import com.wangdaye.mysplash.Mysplash;

/**
 * Category manage model.
 *
 * Model for {@link com.wangdaye.mysplash.common.interfaces.view.CategoryManageView}.
 *
 * */

public interface CategoryManageModel {

    @Mysplash.CategoryIdRule
    int getCategoryId();
    void setCategoryId(@Mysplash.CategoryIdRule int id);
}