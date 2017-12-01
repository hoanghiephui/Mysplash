package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.common._basic.activity.MysplashActivity;

/**
 * Search bar presenter.
 *
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.SearchBarView}.
 *
 * */

public interface SearchBarPresenter {

    void touchNavigatorIcon(MysplashActivity a);
    boolean touchMenuItem(MysplashActivity a, int itemId);

    void showKeyboard();
    void hideKeyboard();

    void submitSearchInfo(String text);
}
