package com.wallpaper.unsplash.common.basic;

/**
 * Previewable.
 *
 * If an Object needs to be sent to {@link com.wallpaper.unsplash.common.ui.activity.PreviewActivity},
 * it should implement this interface.
 *
 * */

public interface Previewable {

    String getRegularUrl();
    String getFullUrl();
    String getDownloadUrl();

    int getWidth();
    int getHeight();
}
