package com.wallpapers.unsplash.common.interfaces.presenter;

/**
 * Edit result presenter.
 * <p>
 * Presenter for {@link com.wallpapers.unsplash.common.interfaces.view.EditResultView}.
 */

public interface EditResultPresenter {

    void createSomething(Object newKey);

    void updateSomething(Object newKey);

    void deleteSomething(Object oldKey);

    Object getEditKey();

    void setEditKey(Object key);
}
