package com.wallpapers.unsplash.ui.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.view.ViewPager
import com.wallpapers.unsplash.common.basic.fragment.BaseFragment

/**
 * Created by hoanghiep on 1/17/18.
 */
abstract class BaseFragment<VIEW : View, PRESENTER : BasePresenter<VIEW>> : BaseFragment() {

    protected var presenter: PRESENTER? = null
        private set

    protected abstract val mvpView: VIEW

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = createPresenter()
        presenter?.initView(mvpView)
    }

    @CallSuper
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        if (view != null
                && view!!.parent != null
                && view!!.parent is ViewPager) {
            /* In ViewPager, don't log here as Fragment might not be visible. Use setUserVisibleHint
             * to log in these situations. */
        }
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        presenter?.onViewPaused()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        presenter?.onViewDestroyed()
    }

    protected fun onViewReady() {
        presenter?.onViewReady()
    }

    protected abstract fun createPresenter(): PRESENTER

}