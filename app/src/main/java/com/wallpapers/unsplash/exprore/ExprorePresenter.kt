package com.wallpapers.unsplash.exprore

import android.content.Context
import com.wallpapers.unsplash.common.data.entity.unsplash.Photos
import com.wallpapers.unsplash.common.utils.DisplayUtils
import com.wallpapers.unsplash.rx.RxBus
import com.wallpapers.unsplash.ui.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.functions.Function8
import javax.inject.Inject

/**
 * Created by hoanghiep on 1/17/18.
 */
class ExprorePresenter @Inject constructor(private val exproreDataManager: ExproreDataManager,
                                           private val rxBus: RxBus,
                                           private val context: Context) : BasePresenter<ExproreView>() {
    private val displayList = mutableListOf<Any>(ExproreDisplayable())
    override fun onViewReady() {
        displayList.add(ExproreDisplayable())
        displayList.add(Ads())
        displayList.add(ExproreDisplayable())
        displayList.add(ExproreDisplayable())
        displayList.add(ExproreDisplayable())
        displayList.add(Ads())
        displayList.add(ExproreDisplayable())
        displayList.add(ExproreDisplayable())
        displayList.add(ExproreDisplayable())

        view?.notifyItemAdded(displayList, 0)
        view?.notifyItemAdded(displayList, 1)
        view?.notifyItemAdded(displayList, 2)
        view?.notifyItemAdded(displayList, 3)
        view?.notifyItemAdded(displayList, 4)
        view?.notifyItemAdded(displayList, 5)
        view?.notifyItemAdded(displayList, 6)
        view?.notifyItemAdded(displayList, 7)
        view?.notifyItemAdded(displayList, 8)
        view?.notifyItemAdded(displayList, 9)
        if (!DisplayUtils.isLandscape(context) && DisplayUtils.getNavigationBarHeight(context.getResources()) != 0) {
            displayList.add(Footer())
            view?.notifyItemAdded(displayList, 10)
        }
    }

    fun loadData() {
        Observable.zip(exproreDataManager.getPhotoByTag("Business", 3, 1),
                exproreDataManager.getPhotoByTag("Girl", 3, 1),
                exproreDataManager.getPhotoByTag("Nature", 3, 1),
                exproreDataManager.getPhotoByTag("Technology", 3, 1),
                exproreDataManager.getPhotoByTag("Food", 3, 1),
                exproreDataManager.getPhotoByTag("Travel", 3, 1),
                exproreDataManager.getPhotoByTag("Happy", 3, 1),
                exproreDataManager.getPhotoByTag("Cool", 3, 1),
                Function8 { bus: Photos, girl: Photos, nature: Photos, tech: Photos, food: Photos, travel: Photos, happe: Photos, cool: Photos
                    ->
                    ExproreData(bus, girl, nature, tech, food, travel, happe, cool)
                })
                .subscribe({
                    view?.updatePhoto(it)
                }, {
                    it.printStackTrace()
                    view?.onError(it?.message)
                }, {
                    view?.onComplete()
                })

    }

    class ExproreData constructor(val exBusiness: Photos,
                                  val exGirl: Photos,
                                  val exNature: Photos,
                                  val exTechnology: Photos,
                                  val exFood: Photos,
                                  val exTravel: Photos,
                                  val exHappy: Photos,
                                  val exCool: Photos)
    class ExproreDisplayable
    class Ads
    class Footer
}