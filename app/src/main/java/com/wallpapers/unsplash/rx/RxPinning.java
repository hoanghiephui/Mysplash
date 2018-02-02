package com.wallpapers.unsplash.rx;

import com.wallpapers.unsplash.ConnectionEvent;

import java.io.IOException;

import javax.net.ssl.SSLPeerUnverifiedException;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by hoanghiep on 1/17/18.
 */

public class RxPinning {
    private RxBus rxBus;

    public RxPinning(RxBus rxBus) {
        this.rxBus = rxBus;
        rxBus.register(ConnectionEvent.class);
    }

    /**
     * Wraps an {@link Observable} and calls it, handling any errors and emitting {@link
     * ConnectionEvent} objects in response if necessary. Specifically, this method handles SSL
     * pinning issues and network I/O problems.
     *
     * Please note that this is not necessary for calls which don't hit the network, as this method
     * interprets {@link IOException} errors as connectivity issues, which will frustrate/confuse
     * the user.
     *
     * @param function An {@link Observable} function
     * @param <T>      The {@link Observable} type
     * @return A wrapped {@link Observable}
     */
    public <T> Observable<T> call(RxLambdas.ObservableRequest<T> function) {
        RxLambdas.ObservableFunction<T> tokenFunction = new RxLambdas.ObservableFunction<T>() {
            @Override
            public Observable<T> apply(Void empty) {
                return function.apply();
            }
        };

        return Observable.defer(() -> tokenFunction.apply(null))
                .doOnError(this::handleError);
    }

    /**
     * Wraps a {@link Completable} and calls it, handling any errors and emitting {@link
     * ConnectionEvent} objects in response if necessary. Specifically, this method handles SSL
     * pinning issues and network I/O problems.
     *
     * Please note that this is not necessary for calls which don't hit the network, as this method
     * interprets {@link IOException} errors as connectivity issues, which will frustrate/confuse
     * the user.
     *
     * @param function A {@link Completable} function
     * @return A wrapped {@link Completable}
     */
    public Completable call(RxLambdas.CompletableRequest function) {
        RxLambdas.CompletableFunction tokenFunction = new RxLambdas.CompletableFunction() {
            @Override
            public Completable apply(Void empty) {
                return function.apply();
            }
        };

        return Completable.defer(() -> tokenFunction.apply(null))
                .doOnError(this::handleError);
    }

    /**
     * Checks the supplied {@link Throwable} and emits a specific {@link ConnectionEvent} as
     * appropriate.
     *
     * @param throwable A {@link} Throwable emitted by a web call of some description
     */
    private void handleError(Throwable throwable) {
        if (throwable instanceof SSLPeerUnverifiedException) {
            rxBus.emitEvent(ConnectionEvent.class, ConnectionEvent.PINNING_FAIL);
        } else if (throwable instanceof IOException) {
            rxBus.emitEvent(ConnectionEvent.class, ConnectionEvent.NO_CONNECTION);
        }
    }
}
