package com.wallpaper.unsplash.common.basic;

/**
 * Tag.
 *
 * If an Object need to be displayed in a RecyclerView with
 * {@link com.wallpaper.unsplash.common.ui.adapter.TagAdapter}, it should implement this interface.
 *
 * */

public interface Tag {

    String getTitle();
    String getRegularUrl();
    String getThumbnailUrl();
}
