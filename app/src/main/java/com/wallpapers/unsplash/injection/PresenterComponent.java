package com.wallpapers.unsplash.injection;

import com.wallpapers.unsplash.ui.fragments.ExproreFragment;

import dagger.Subcomponent;

/**
 * Created by hoanghiep on 1/17/18.
 * Subcomponents have access to all upstream objects in the graph but can have their own scope -
 * they don't need to explicitly state their dependencies as they have access anyway
 */

@PresenterScope
@Subcomponent(modules = DataManagerModule.class)
public interface PresenterComponent {
    void inject(ExproreFragment exproreFragment);
}
