package com.wangdaye.mysplash.photo.presenter.widget;

import com.wangdaye.mysplash._common.i.model.LoadModel;
import com.wangdaye.mysplash._common.i.presenter.LoadPresenter;
import com.wangdaye.mysplash._common.i.view.LoadView;
import com.wangdaye.mysplash.photo.model.widget.LoadObject;

/**
 * Load implementor.
 * */

public class LoadImplementor implements LoadPresenter {
    // model & view.
    private LoadModel model;
    private LoadView view;

    /** <br> life cycle. */

    public LoadImplementor(LoadModel model, LoadView view) {
        this.model = model;
        this.view = view;
    }

    /** <br> presenter. */

    @Override
    public void setLoadingState() {
        switch (model.getState()) {
            case LoadObject.FAILED_STATE:
                model.setState(LoadObject.LOADING_STATE);
                view.setLoadingState();
                break;
        }
    }

    @Override
    public void setFailedState() {
        if (model.getState() == LoadObject.LOADING_STATE) {
            model.setState(LoadObject.FAILED_STATE);
            view.setFailedState();
        }
    }

    @Override
    public void setNormalState() {
        if (model.getState() == LoadObject.LOADING_STATE) {
            model.setState(LoadObject.NORMAL_STATE);
            view.setNormalState();
        }
    }

    @Override
    public boolean isNormalState() {
        return model.getState() == LoadObject.NORMAL_STATE;
    }
}

