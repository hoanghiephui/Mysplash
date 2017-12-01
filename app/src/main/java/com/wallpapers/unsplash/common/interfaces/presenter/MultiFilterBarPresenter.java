package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;

/**
 * Multi-filter bar presenter.
 *
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.MultiFilterBarView}.
 *
 * */

public interface MultiFilterBarPresenter {

    void touchNavigatorIcon();
    void touchToolbar(MysplashActivity a);
    void touchSearchButton();
    void touchMenuContainer(int position);

    void showKeyboard();
    void hideKeyboard();

    void submitSearchInfo();

    String getQuery();
    void setQuery(String query);

    String getUsername();
    void setUsername(String username);

    int getCategory();
    void setCategory(int c);

    String getOrientation();
    void setOrientation(String o);

    boolean isFeatured();
    void setFeatured(boolean f);
}