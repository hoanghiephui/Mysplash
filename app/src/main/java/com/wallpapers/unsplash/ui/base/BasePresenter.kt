package com.wallpapers.unsplash.ui.base

import android.support.annotation.CallSuper
import com.wallpapers.unsplash.injection.Injector
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by hoanghiep on 1/17/18.
 */
abstract class BasePresenter<VIEW : View> protected constructor() : Presenter<VIEW> {

    val compositeDisposable: CompositeDisposable
    var views: VIEW? = null

    override val view: VIEW?
        get() = views

    init {
        compositeDisposable = CompositeDisposable()
    }

    override fun onViewPaused() {
        // No-op
    }

    override fun initView(view: VIEW) {
        this.views = view
    }

    @CallSuper
    override fun onViewDestroyed() {
        /* Clear all subscriptions so that:
         * 1) all processes are cancelled
         * 2) processes don't try to update a null View
         * 3) background processes don't leak memory
         */
        compositeDisposable.clear()

        /*
         * Clear PresenterComponent, thereby releasing all objects with a
         * {@link piuk.blockchain.android.injection.ViewModelScope} annotation for GC
         */
        Injector.getInstance().releaseViewModelScope()
    }

    abstract fun onViewReady()

}