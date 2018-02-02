package com.wallpapers.unsplash.common.interfaces.presenter;

import com.wallpapers.unsplash.common.basic.activity.BaseActivity;
import com.wallpapers.unsplash.common.interfaces.view.PagerView;

/**
 * Pager manage presenter.
 * <p>
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.PagerManageView}.
 */

public interface PagerManagePresenter {

    int getPagerPosition();

    void setPagerPosition(int position);

    int getPagerItemCount();

    PagerView getPagerView(int position);

    /**
     * Get the key word of the {@link PagerView}, like the order of photos, or type of collections.
     *
     * @return Key words.
     */
    String getPagerKey(int position);

    void checkToRefresh(int position);

    /**
     * {@link BaseActivity#backToTop()}
     * {@link com.wallpapers.unsplash.common.utils.BackToTopUtils}
     */
    boolean needPagerBackToTop();

    void pagerScrollToTop();

    /**
     * Check {@link PagerView} can swipe back.
     *
     * @return Can swipe back.
     */
    boolean canPagerSwipeBack(int dir);
}
