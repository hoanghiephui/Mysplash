package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.common.interfaces.view.PagerView;

/**
 * Pager presenter.
 *
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.PagerView}.
 *
 * */

public interface PagerPresenter {

    /**
     * Check the {@link com.wallpapers.unsplash.common.interfaces.view.PagerView} need to execute the
     * {@link PagerView#refreshPager()} method.
     * */
    boolean checkNeedRefresh();
    void refreshPager();

    int getIndex();

    boolean isSelected();
    void setSelected(boolean selected);
}
