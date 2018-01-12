package com.wallpaper.unsplash.common.interfaces.presenter;

import com.wallpaper.unsplash.common.basic.activity.BaseActivity;

/**
 * Multi-filter bar presenter.
 *
 * Presenter for {@link com.wallpaper.unsplash.common.interfaces.view.MultiFilterBarView}.
 *
 * */

public interface MultiFilterBarPresenter {

    void touchNavigatorIcon();
    void touchToolbar(BaseActivity a);
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
