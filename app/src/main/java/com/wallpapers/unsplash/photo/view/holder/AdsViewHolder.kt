package com.wallpapers.unsplash.photo.view.holder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.facebook.ads.*
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.wallpapers.unsplash.Constants
import com.wallpapers.unsplash.R
import com.wallpapers.unsplash.common.basic.activity.BaseActivity
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo
import com.wallpapers.unsplash.common.ui.adapter.PhotoInfoAdapter
import com.wallpapers.unsplash.common.utils.PrefsUtils
import com.wallpapers.unsplash.photo.view.activity.PhotoActivity

/**
 * Created by hoanghiep on 12/4/17.
 */
class AdsViewHolder(itemView: View, activity: BaseActivity) : PhotoInfoAdapter.ViewHolder(itemView) {
    companion object {
        const val TYPE_ADS = 13
    }

    private var adRequest: AdRequest? = null
    var adsView: RelativeLayout? = null
    var adViewFacebook: com.facebook.ads.AdView? = null
    var managerMini: NativeAdsManager? = null
    var nativeAdScrollViewMini: NativeAdScrollView? = null

    init {
        adRequest = AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build()
        adsView = itemView.findViewById(R.id.adViewContainer)


        managerMini = NativeAdsManager(activity,
                activity.getString(R.string.native_small_facebook),
                5)
        managerMini?.loadAds(NativeAd.MediaCacheFlag.ALL)
    }

    override fun onBindView(activity: PhotoActivity?, photo: Photo?) {
        initAds(activity)
    }

    override fun onRecycled() {
        adViewFacebook?.destroy()
    }

    /**
     * init ads facebook and google
     */
    private fun initAds(context: Context?) {
        if (Constants.SHOW_ADS && !PrefsUtils.isPro(context) && PrefsUtils.isShowAds(context)) {
            adViewFacebook = com.facebook.ads.AdView(context, context?.getString(R.string.banner_photo_info_facebook), AdSize.BANNER_HEIGHT_50)
            if (adViewFacebook?.parent != null) {
                ((adViewFacebook?.parent) as ViewGroup).removeView(adViewFacebook)
            }
            if (adsView?.parent != null) {
                adsView?.removeAllViews()
            }

            adViewFacebook?.loadAd()
            adViewFacebook?.setAdListener(object : com.facebook.ads.AdListener {
                override fun onAdClicked(p0: Ad?) {
                }

                override fun onError(p0: Ad?, p1: AdError?) {
                    initAdsGoogle(context)
                }

                override fun onAdLoaded(p0: Ad?) {
                    adsView?.visibility = View.VISIBLE
                }

                override fun onLoggingImpression(p0: Ad?) {
                }

            })

            //initAdsScrollFacebook()
            adsView?.addView(adViewFacebook)

        } else {
            adsView?.visibility = View.GONE
            //adsViewNative
        }
    }

    /*private fun initAdsScrollFacebook() {
        managerMini?.setListener(object : NativeAdsManager.Listener {
            override fun onAdsLoaded() {
                AnimationUtils.animateView(holder.adsViewNative, AnimationUtils.Type.SLIDE_AND_ALPHA, true, 600)
                nativeAdScrollViewMini = NativeAdScrollView(holder.itemView.context, managerMini,
                        NativeAdView.Type.HEIGHT_120)
                holder.adsViewNative.addView(nativeAdScrollViewMini)
                mAdapterCallback.onScrolllList()
            }

            override fun onAdError(adError: AdError) {
                AnimationUtils.animateView(holder.adsViewNative, AnimationUtils.Type.SLIDE_AND_ALPHA, false, 600)
                mAdapterCallback.onScrolllList()
            }
        })
    }*/

    private fun initAdsGoogle(context: Context?) {
        val adsViewG = AdView(context!!)
        adsViewG.adSize = com.google.android.gms.ads.AdSize.SMART_BANNER
        adsViewG.adUnitId = Constants.ADMOB_BANNER_PHOTO

        adsViewG.loadAd(adRequest)
        adsViewG.setAdListener(object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                adsView?.visibility = View.VISIBLE
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                //adsView?.visibility = View.GONE
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                //adsView?.visibility = View.GONE
            }
        })
        adsView?.addView(adsViewG)
    }
}