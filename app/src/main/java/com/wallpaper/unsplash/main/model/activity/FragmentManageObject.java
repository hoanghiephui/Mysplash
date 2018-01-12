package com.wallpaper.unsplash.main.model.activity;

import android.content.Intent;
import android.text.TextUtils;

import com.wallpaper.unsplash.R;
import com.wallpaper.unsplash.common.interfaces.model.FragmentManageModel;

/**
 * Fragment mange object.
 *
 * */

public class FragmentManageObject
        implements FragmentManageModel {

    private int id;

    public FragmentManageObject(int id, Intent intent) {
        this.id = id;
        if (this.id == 0) {
            this.id = R.id.action_home;
            if (intent != null && !TextUtils.isEmpty(intent.getAction())
                    && intent.getAction().equals("com.wallpaper.unsplash.Search")) {
                this.id = R.id.action_search;
            }
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
