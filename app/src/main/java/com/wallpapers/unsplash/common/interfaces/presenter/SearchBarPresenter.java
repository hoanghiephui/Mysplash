package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.common.basic.activity.BaseActivity;

/**
 * Search bar presenter.
 * <p>
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.SearchBarView}.
 */

public interface SearchBarPresenter {

    void touchNavigatorIcon(BaseActivity a);

    boolean touchMenuItem(BaseActivity a, int itemId);

    void showKeyboard();

    void hideKeyboard();

    void submitSearchInfo(String text);
}
