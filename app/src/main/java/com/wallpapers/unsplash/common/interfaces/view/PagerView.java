package com.wallpapers.unsplash.common.interfaces.view;

import android.os.Bundle;

/**
 * Pager view.
 * <p>
 * A view which as an abstract concept for "page".
 */

public interface PagerView {

    void onSaveInstanceState(Bundle bundle);

    void onRestoreInstanceState(Bundle bundle);

    void checkToRefresh();

    boolean checkNeedRefresh();

    boolean checkNeedBackToTop();

    void refreshPager();

    void setSelected(boolean selected);

    void scrollToPageTop();

    void cancelRequest();

    String getKey();

    void setKey(String key);

    int getItemCount();

    boolean canSwipeBack(int dir);

    boolean isNormalState();
}