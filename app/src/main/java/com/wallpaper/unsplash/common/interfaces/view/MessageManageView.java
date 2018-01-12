package com.wallpaper.unsplash.common.interfaces.view;

/**
 * Message manage view.
 *
 * A view which can handle and respond message by handler.
 *
 * */

public interface MessageManageView {

    void sendMessage(int what, Object o);
    void responseMessage(int what, Object o);
}
