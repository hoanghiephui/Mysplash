package com.wallpapers.unsplash.common.interfaces.presenter;

/**
 * Message mange presenter.
 * <p>
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.MessageManageView}.
 */

public interface MessageManagePresenter {

    void sendMessage(int what, Object o);

    void responseMessage(int what, Object o);
}
