package com.wallpaper.unsplash.common.ui.widget

import android.content.Context
import android.os.SystemClock
import android.support.v7.widget.AppCompatTextView
import android.text.format.DateUtils.getRelativeTimeSpanString
import android.util.AttributeSet
import com.wallpaper.unsplash.R
import com.wallpaper.unsplash.common.utils.DateUtils.formatSameDayTime
import java.lang.ref.WeakReference

/**
 * Created by hoanghiep on 12/3/17.
 */
class ShortTimeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = android.R.attr.textViewStyle) : AppCompatTextView(context, attrs, defStyle) {

    private val mTicker: Runnable
    private var mShowAbsoluteTime: Boolean = false
    private var mTime: Long = 0

    init {
        mTicker = TickerRunnable(this)
    }

    fun setShowAbsoluteTime(showAbsoluteTime: Boolean) {
        mShowAbsoluteTime = showAbsoluteTime
        invalidateTime()
    }

    fun setTime(time: Long) {
        mTime = time
        invalidateTime()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        post(mTicker)
    }

    override fun onDetachedFromWindow() {
        removeCallbacks(mTicker)
        super.onDetachedFromWindow()
    }

    private fun invalidateTime() {
        if (mShowAbsoluteTime) {
            setText(formatSameDayTime(context, mTime))
        } else {
            val current = System.currentTimeMillis()
            if (Math.abs(current - mTime) > 60 * 1000) {
                setText(getRelativeTimeSpanString(mTime, System.currentTimeMillis(),
                        android.text.format.DateUtils.MINUTE_IN_MILLIS, android.text.format.DateUtils.FORMAT_ABBREV_ALL))
            } else {
                setText(R.string.just_now)
            }
        }
    }

    private class TickerRunnable (view: ShortTimeView) : Runnable {

        private val mViewRef: WeakReference<ShortTimeView>

        init {
            mViewRef = WeakReference(view)
        }

        override fun run() {
            val view = mViewRef.get() ?: return
            val handler = view.handler ?: return
            view.invalidateTime()
            val now = SystemClock.uptimeMillis()
            val next = now + TICKER_DURATION - now % TICKER_DURATION
            handler.postAtTime(this, next)
        }
    }

    companion object {
        private val TICKER_DURATION = 5000L
    }
}