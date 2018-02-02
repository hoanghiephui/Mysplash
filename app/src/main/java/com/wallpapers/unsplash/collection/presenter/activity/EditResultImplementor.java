package com.wallpapers.unsplash.collection.presenter.activity;

import com.wallpapers.unsplash.common.interfaces.model.EditResultModel;
import com.wallpapers.unsplash.common.interfaces.presenter.EditResultPresenter;
import com.wallpapers.unsplash.common.interfaces.view.EditResultView;

/**
 * Edit result implementor.
 */

public class EditResultImplementor
        implements EditResultPresenter {

    private EditResultModel model;
    private EditResultView view;

    public EditResultImplementor(EditResultModel model, EditResultView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void createSomething(Object newKey) {
        // do nothing.
    }

    @Override
    public void updateSomething(Object newKey) {
        model.setEditKey(newKey);
        view.drawUpdateResult(newKey);
    }

    @Override
    public void deleteSomething(Object oldKey) {
        view.drawDeleteResult(oldKey);
    }

    @Override
    public Object getEditKey() {
        return model.getEditKey();
    }

    @Override
    public void setEditKey(Object k) {
        model.setEditKey(k);
    }
}
