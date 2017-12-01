package com.wallpapers.unsplash.main.presenter.activity;

import com.wallpapers.unsplash.common.interfaces.presenter.MessageManagePresenter;
import com.wallpapers.unsplash.common.interfaces.view.MessageManageView;

/**
 * Message manage implementor.
 *
 * */

public class MessageManageImplementor
        implements MessageManagePresenter {

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
