package com.wallpapers.unsplash.common.interfaces.view;

/**
 * Message manage view.
 * <p>
 * A view which can handle and respond message by handler.
 */

public interface MessageManageView {

    void sendMessage(int what, Object o);

    void responseMessage(int what, Object o);
}
