package com.wangdaye.mysplash.common.interfaces.presenter;

/**
 * Edit result presenter.
 *
 * Presenter for {@link com.wangdaye.mysplash.common.interfaces.view.EditResultView}.
 *
 * */

public interface EditResultPresenter {

    void createSomething(Object newKey);
    void updateSomething(Object newKey);
    void deleteSomething(Object oldKey);

    Object getEditKey();
    void setEditKey(Object key);
}
