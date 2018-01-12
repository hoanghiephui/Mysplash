package com.wallpaper.unsplash.common.utils

import android.content.Context
import android.text.format.DateFormat
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by hoanghiep on 12/3/17.
 */
object DateUtils {
    const val FORMAT_TIME_ZONE_5 = "GMT-05:00"
    const val FORMAT_TIME_ZONE_Plus2 = "GMT+02:00"
    fun formatSameDayTime(context: Context?, timestamp: Long): String? {
        if (context == null) return null
        return if (android.text.format.DateUtils.isToday(timestamp)) android.text.format.DateUtils.formatDateTime(context, timestamp,
                if (DateFormat.is24HourFormat(context))
                    android.text.format.DateUtils.FORMAT_SHOW_TIME or android.text.format.DateUtils.FORMAT_24HOUR
                else
                    android.text.format.DateUtils.FORMAT_SHOW_TIME or android.text.format.DateUtils.FORMAT_12HOUR) else android.text.format.DateUtils.formatDateTime(context, timestamp, android.text.format.DateUtils.FORMAT_SHOW_DATE)
    }

    @JvmStatic
    fun dateTimeToTimestamp(created_at: String, timeZoneInt: String, tineZoneOut: String): Long {
        val time: Long
        try {
            //2016-09-05T03:09:13-04:00
            var dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
            dateFormat.timeZone = TimeZone.getTimeZone(timeZoneInt)
            val date = dateFormat.parse(created_at)
            dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            dateFormat.timeZone = TimeZone.getTimeZone(tineZoneOut)
            val tm = Timestamp.valueOf(dateFormat.format(date))
            time = tm.time
            return time
        } catch (e: Exception) {
            return 0
        }

    }

}