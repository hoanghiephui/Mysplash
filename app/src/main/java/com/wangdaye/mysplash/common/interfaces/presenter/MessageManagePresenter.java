package com.wangdaye.mysplash.common.interfaces.presenter;

/**
 * Message mange presenter.
 *
 * Presenter for {@link com.wangdaye.mysplash.common.interfaces.view.MessageManageView}.
 *
 * */

public interface MessageManagePresenter {

    void sendMessage(int what, Object o);
    void responseMessage(int what, Object o);
}
