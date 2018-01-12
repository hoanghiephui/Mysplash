package com.wallpaper.unsplash.common.interfaces.presenter;

/**
 * Scroll presenter.
 *
 * Presenter for {@link com.wallpaper.unsplash.common.interfaces.view.ScrollView}.
 *
 * */

public interface ScrollPresenter {

    boolean isToTop();
    void setToTop(boolean top);

    boolean needBackToTop();

    /**
     * This method can make the list view scroll to the top.
     * */
    void scrollToTop();

    /**
     * This method is used to control the list view to automatically load more data when scrolling.
     * */
    void autoLoad(int dy);
}
