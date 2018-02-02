package com.wallpapers.unsplash.common.interfaces.view;

/**
 * Edit result view.
 * <p>
 * A view which can edit objects.
 */

public interface EditResultView {

    void drawCreateResult(Object newKey);

    void drawUpdateResult(Object newKey);

    void drawDeleteResult(Object oldKey);
}
