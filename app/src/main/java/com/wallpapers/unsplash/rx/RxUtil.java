package com.wallpapers.unsplash.rx;

import com.wallpapers.unsplash.ui.base.BasePresenter;

import io.reactivex.CompletableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hoanghiep on 1/17/18.
 */

public class RxUtil {
    /**
     * Applies standard Schedulers to an {@link Observable}, ie IO for subscription, Main Thread for
     * onNext/onComplete/onError
     */
    public static <T> ObservableTransformer<T, T> applySchedulersToObservable() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    /**
     * Applies standard Schedulers to a {@link Single}, ie IO for subscription, Main Thread for
     * onNext/onComplete/onError
     */
    public static <T> SingleTransformer<T, T> applySchedulersToSingle() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    /**
     * Applies standard Schedulers to a {@link io.reactivex.Completable}, ie IO for subscription,
     * Main Thread for onNext/onComplete/onError
     */
    public static CompletableTransformer applySchedulersToCompletable() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    /**
     * Allows you to call two different {@link Observable} objects based on result of a predicate.
     */
    public static <T, R> Function<? super T, ? extends Observable<? extends R>> ternary(
            Function<T, Boolean> predicate,
            Function<? super T, ? extends Observable<? extends R>> ifTrue,
            Function<? super T, ? extends Observable<? extends R>> ifFalse) {
        return (item) -> predicate.apply(item)
                ? ifTrue.apply(item)
                : ifFalse.apply(item);
    }

    /**
     * Adds the subscription to the upstream {@link Observable} to the {@link CompositeDisposable}
     * supplied by a class extending {@link BasePresenter}. This allows the subscription to be
     * cancelled automatically by the Presenter on Android lifecycle events.
     *
     * @param presenter A class extending {@link BasePresenter}
     * @param <T>       The type of the upstream {@link Observable}
     */
    public static <T> ObservableTransformer<T, T> addObservableToCompositeDisposable(BasePresenter presenter) {
        return upstream -> upstream.doOnSubscribe(disposable ->
                presenter.getCompositeDisposable().add(disposable));
    }

    /**
     * Adds the subscription to the upstream {@link io.reactivex.Completable} to the {@link
     * CompositeDisposable} supplied by a class extending {@link BasePresenter}. This allows the
     * subscription to be cancelled automatically by the Presenter on Android lifecycle events.
     *
     * @param presenter A class extending {@link BasePresenter}
     */
    public static CompletableTransformer addCompletableToCompositeDisposable(BasePresenter presenter) {
        return upstream -> upstream.doOnSubscribe(disposable ->
                presenter.getCompositeDisposable().add(disposable));
    }

    /**
     * Adds the subscription to the upstream {@link Single} to the {@link CompositeDisposable}
     * supplied by a class extending {@link BasePresenter}. This allows the subscription to be
     * cancelled automatically by the Presenter on Android lifecycle events.
     *
     * @param presenter A class extending {@link BasePresenter}
     * @param <T>       The type of the upstream {@link Single}
     */
    public static <T> SingleTransformer<T, T> addSingleToCompositeDisposable(BasePresenter presenter) {
        return upstream -> upstream.doOnSubscribe(disposable ->
                presenter.getCompositeDisposable().add(disposable));
    }
}
