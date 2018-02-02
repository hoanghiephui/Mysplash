package com.wallpapers.unsplash.ui.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.facebook.ads.*
import com.google.android.gms.ads.AdRequest
import com.wallpapers.unsplash.R
import com.wallpapers.unsplash.exprore.ExprorePresenter
import com.wallpapers.unsplash.kotlin.extensions.inflate
import com.wallpapers.unsplash.kotlin.extensions.visible
import com.wallpapers.unsplash.ui.adapters.base.AdapterDelegate
import kotlinx.android.synthetic.main.item_ads.view.*

/**
 * Created by hoanghiep on 1/18/18.
 */
class AdsDelegate<in T>(private val activity: Activity,
                        idAdsFb: String,
                        idAdsGg: String) : AdapterDelegate<T> {
    var nativeAdScrollView: NativeAdScrollView? = null
    private var viewHolder: AdsViewHolder? = null
    var manager: NativeAdsManager? = null
    val adView = com.google.android.gms.ads.AdView(activity)
    init {
        manager = NativeAdsManager(activity, idAdsFb, 5)
        adView.adSize = com.google.android.gms.ads.AdSize.SMART_BANNER
        adView.adUnitId = idAdsGg
    }


    override fun isForViewType(items: List<T>, position: Int) = items[position] is ExprorePresenter.Ads

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = AdsViewHolder(parent.inflate(R.layout.item_ads))

    override fun onBindViewHolder(items: List<T>, position: Int, holder: RecyclerView.ViewHolder, payloads: List<*>) {
        manager?.loadAds(NativeAd.MediaCacheFlag.ALL)
        viewHolder = holder as AdsViewHolder
        viewHolder?.let {
            manager?.setListener(object : NativeAdsManager.Listener {
                override fun onAdsLoaded() {
                    nativeAdScrollView = NativeAdScrollView(activity, manager, NativeAdView.Type.HEIGHT_300)
                    it.adsView.visible()
                    if (manager != null && manager!!.isLoaded) {
                        it.adsView.addView(nativeAdScrollView)
                    }
                    Log.d("ADS", "LOAD")
                }

                override fun onAdError(p0: AdError?) {
                    Log.d("ADS", "ERROR")
                    val adRequest = AdRequest.Builder().build()
                    adView.loadAd(adRequest)
                    it.adsView.visible()
                    if (adView.parent != null)
                        (adView.parent as ViewGroup).removeView(adView)
                    it.adsView.addView(adView)
                }
            })

        }
    }

    override fun onViewDetachedFromWindow() {
    }

    class AdsViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val adsView: LinearLayout = itemView.viewAds
    }

    fun showAds() {

    }
}