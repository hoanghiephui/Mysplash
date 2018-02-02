package com.wallpapers.unsplash.injection;

import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.basic.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hoanghiep on 1/17/18.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        ApiModule.class
})
public interface ApplicationComponent {
    // Subcomponent with its own scope
    PresenterComponent plus(DataManagerModule userModule);

    void inject(UnsplashApplication application);

    void inject(BaseActivity baseActivity);
}
