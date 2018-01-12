package com.wallpaper.unsplash.common.interfaces.presenter;

import com.wallpaper.unsplash.common.basic.activity.BaseActivity;

/**
 * Search bar presenter.
 *
 * Presenter for {@link com.wallpaper.unsplash.common.interfaces.view.SearchBarView}.
 *
 * */

public interface SearchBarPresenter {

    void touchNavigatorIcon(BaseActivity a);
    boolean touchMenuItem(BaseActivity a, int itemId);

    void showKeyboard();
    void hideKeyboard();

    void submitSearchInfo(String text);
}
