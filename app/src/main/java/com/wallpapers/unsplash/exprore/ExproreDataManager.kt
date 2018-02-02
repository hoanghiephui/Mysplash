package com.wallpapers.unsplash.exprore

import com.wallpapers.unsplash.common.data.entity.unsplash.Photos
import com.wallpapers.unsplash.rx.RxBus
import com.wallpapers.unsplash.rx.RxPinning
import com.wallpapers.unsplash.rx.RxUtil
import io.reactivex.Observable

/**
 * Created by hoanghiep on 1/17/18.
 */
class ExproreDataManager(private val exproreApi: ExproreApi, rxBus: RxBus) {
    private val rxPinning = RxPinning(rxBus)

    fun getPhotoByTag(query: String, perPage: Int, page: Int): Observable<Photos> {
        return rxPinning.call<Photos> {
            exproreApi.getPhotoByTag(query, perPage, page).compose(RxUtil.applySchedulersToObservable())
        }
    }
}