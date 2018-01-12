package com.wallpaper.unsplash.common.interfaces.view;

/**
 * Category view.
 *
 * A view which can request {@link com.wallpaper.unsplash.common.data.entity.unsplash.Photo} in
 * a category and show them.
 *
 * */

public interface CategoryView {

    void setRefreshing(boolean refreshing);
    void setLoading(boolean loading);

    void setPermitRefreshing(boolean permit);
    void setPermitLoading(boolean permit);

    void initRefreshStart();
    void requestPhotosSuccess();
    void requestPhotosFailed(String feedback);
}
