package com.wallpapers.unsplash.injection;

import com.wallpapers.unsplash.exprore.ExproreApi;
import com.wallpapers.unsplash.exprore.ExproreDataManager;
import com.wallpapers.unsplash.rx.RxBus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hoanghiep on 1/17/18.
 */
@Module
public class DataManagerModule {
    @Provides
    @PresenterScope
    protected ExproreDataManager provideExproreDataManager(RxBus rxBus) {
        return new ExproreDataManager(new ExproreApi(), rxBus);
    }
}
