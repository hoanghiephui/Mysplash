package com.wallpapers.unsplash.common.interfaces.view;

/**
 * Photos view.
 *
 * A view which can request {@link com.wallpapers.unsplash.common.data.entity.unsplash.Photo} by HTTP
 * request and show them.
 *
 * */

public interface PhotosView {

    void setRefreshing(boolean refreshing);
    void setLoading(boolean loading);

    void setPermitRefreshing(boolean permit);
    void setPermitLoading(boolean permit);

    void initRefreshStart();
    void requestPhotosSuccess();
    void requestPhotosFailed(String feedback);
}
