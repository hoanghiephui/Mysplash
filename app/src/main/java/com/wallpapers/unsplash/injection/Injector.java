package com.wallpapers.unsplash.injection;

import android.app.Application;
import android.content.Context;

/**
 * Created by hoanghiep on 1/17/18.
 */

public enum Injector {
    INSTANCE;

    private ApplicationComponent applicationComponent;
    private PresenterComponent presenterComponent;

    public static Injector getInstance() {
        return INSTANCE;
    }

    public void init(Context applicationContext) {

        ApplicationModule applicationModule = new ApplicationModule((Application) applicationContext);
        ApiModule apiModule = new ApiModule();
        DataManagerModule managerModule = new DataManagerModule();

        initAppComponent(applicationModule, apiModule, managerModule);
    }

    protected void initAppComponent(ApplicationModule applicationModule, ApiModule apiModule, DataManagerModule managerModule) {

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(applicationModule)
                .apiModule(apiModule)
                .build();

        presenterComponent = applicationComponent.plus(managerModule);
    }

    public ApplicationComponent getAppComponent() {
        return applicationComponent;
    }

    public PresenterComponent getPresenterComponent() {
        if (presenterComponent == null) {
            presenterComponent = applicationComponent.plus(new DataManagerModule());
        }
        return presenterComponent;
    }

    public void releaseViewModelScope() {
        presenterComponent = null;
    }
}
