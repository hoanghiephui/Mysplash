package com.wallpapers.unsplash.injection;

import android.app.Application;
import android.content.Context;

import com.wallpapers.unsplash.api.EnvironmentSettings;
import com.wallpapers.unsplash.rx.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hoanghiep on 1/17/18.
 */

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    protected Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    protected EnvironmentSettings provideDebugSettings() {
        return new EnvironmentSettings();
    }

    @Provides
    @Singleton
    protected RxBus provideRxBus() {
        return new RxBus();
    }
}
