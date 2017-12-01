package com.wangdaye.mysplash.photo.presenter;

import com.wangdaye.mysplash.common.interfaces.presenter.MessageManagePresenter;
import com.wangdaye.mysplash.common.interfaces.view.MessageManageView;

/**
 * Message manage implementor.
 * */

public class MessageManageImplementor implements MessageManagePresenter {

    private MessageManageView view;

    public MessageManageImplementor(MessageManageView view) {
        this.view = view;
    }

    @Override
    public void sendMessage(int what, Object o) {
        view.sendMessage(what, o);
    }

    @Override
    public void responseMessage(int what, Object o) {
        view.responseMessage(what, o);
    }
}
