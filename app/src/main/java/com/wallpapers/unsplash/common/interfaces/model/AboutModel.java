package com.wallpapers.unsplash.common.interfaces.model;

import android.support.annotation.IntDef;

/**
 * About model.
 * <p>
 * Item model for {@link com.wallpapers.unsplash.common.ui.adapter.AboutAdapter}.
 */

public interface AboutModel {

    @AboutTypeRule
    int getType();

    int TYPE_HEADER = 1;
    int TYPE_CATEGORY = 2;
    int TYPE_APP = 3;
    int TYPE_TRANSLATOR = 4;
    int TYPE_LIBRARY = 5;

    @IntDef({TYPE_HEADER, TYPE_CATEGORY, TYPE_APP, TYPE_TRANSLATOR, TYPE_LIBRARY})
    @interface AboutTypeRule {
    }
}
