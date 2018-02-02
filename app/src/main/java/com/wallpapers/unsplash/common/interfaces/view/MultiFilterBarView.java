package com.wallpapers.unsplash.common.interfaces.view;

/**
 * Multi-filter bar view.
 * <p>
 * App bar for the {@link MultiFilterView}.
 */

public interface MultiFilterBarView {

    void touchNavigationIcon();

    void touchSearchButton();

    void touchMenuContainer(int position);

    void showKeyboard();

    void hideKeyboard();

    void submitSearchInfo();
}
