package com.wallpaper.unsplash.common.interfaces.view;

/**
 * Search bar view.
 *
 * App bar view for {@link SearchView}.
 *
 * */

public interface SearchBarView {

    void clearSearchBarText();
    void submitSearchInfo(String text);

    void showKeyboard();
    void hideKeyboard();
}
