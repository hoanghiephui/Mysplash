package com.wallpaper.unsplash.common.interfaces.view;

/**
 * Search view.
 *
 * A view which can request data by {@link com.wallpaper.unsplash.common.data.api.SearchApi} and
 * show them.
 *
 * */

public interface SearchView {

    void setRefreshing(boolean refreshing);
    void setLoading(boolean loading);

    void setPermitRefreshing(boolean permit);
    void setPermitLoading(boolean permit);

    void initRefreshStart();
    void searchSuccess();
    void searchFailed(String feedback);
}
