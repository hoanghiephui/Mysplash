package com.wangdaye.mysplash.common.interfaces.presenter;

import com.wangdaye.mysplash.common.interfaces.view.PagerView;

/**
 * Pager presenter.
 *
 * Presenter for {@link com.wangdaye.mysplash.common.interfaces.view.PagerView}.
 *
 * */

public interface PagerPresenter {

    /**
     * Check the {@link com.wangdaye.mysplash.common.interfaces.view.PagerView} need to execute the
     * {@link PagerView#refreshPager()} method.
     * */
    boolean checkNeedRefresh();
    void refreshPager();

    int getIndex();

    boolean isSelected();
    void setSelected(boolean selected);
}
