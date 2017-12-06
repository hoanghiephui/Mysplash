package com.wallpapers.unsplash.common.basic;

/**
 * Tag.
 *
 * If an Object need to be displayed in a RecyclerView with
 * {@link com.wallpapers.unsplash.common.ui.adapter.TagAdapter}, it should implement this interface.
 *
 * */

public interface Tag {

    String getTitle();
    String getRegularUrl();
    String getThumbnailUrl();
}
