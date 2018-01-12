package com.wallpaper.unsplash.common.utils

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by hoanghiep on 12/5/17.
 */
class PrefsUtils {
    companion object {
        val PRO = "pro"
        val SHOW_ADS = "ads"
        val AUTHEN_BEARER = "authen_bea"
        val AUTHEN_ID = "authen_id"

        fun setPro(context: Context, isBuy: Boolean) {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            sp.edit().putBoolean(PRO, isBuy).apply()
        }

        fun isPro(context: Context?): Boolean {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getBoolean(PRO, false)
        }

        fun setShowAds(context: Context, isShowAds: Boolean) {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            sp.edit().putBoolean(SHOW_ADS, isShowAds).apply()
        }

        fun isShowAds(context: Context?): Boolean {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getBoolean(SHOW_ADS, false)
        }

        fun setAuthenBearer(context: Context, id: String) {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            sp.edit().putString(AUTHEN_BEARER, id).apply()
        }

        fun setAuthenId(context: Context, id: String) {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            sp.edit().putString(AUTHEN_ID, id).apply()
        }

        fun getAuthenBearer(context: Context?) : String {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getString(AUTHEN_BEARER, "")
        }

        fun getAuthenId(context: Context?) : String {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getString(AUTHEN_ID, "")
        }
    }
}