package com.wallpaper.unsplash.collection.model.activity;

import com.wallpaper.unsplash.common.data.entity.unsplash.Collection;
import com.wallpaper.unsplash.common.interfaces.model.EditResultModel;

/**
 * Edit result object.
 * */

public class EditResultObject
        implements EditResultModel {

    private Object key;

    public EditResultObject(Collection c) {
        this.key = c;
    }

    @Override
    public Object getEditKey() {
        return key;
    }

    @Override
    public void setEditKey(Object k) {
        key = k;
    }
}
